package org.myorg;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.log4j.Logger;

public class Multiwordcnt {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException  {

            Configuration conf = new Configuration();
            Job myJob = new Job(conf, "Multiwordcnt");
            String[] userargs = new GenericOptionsParser(conf, args).getRemainingArgs();

            myJob.setJarByClass(Multiwordcnt.class);
            myJob.setMapperClass(MyMapper.class);
            myJob.setReducerClass(MyReducer.class);     
            myJob.setMapOutputKeyClass(Text.class);
            myJob.setMapOutputValueClass(IntWritable.class);

            myJob.setOutputKeyClass(Text.class);
            myJob.setOutputValueClass(IntWritable.class);

            myJob.setInputFormatClass(TextInputFormat.class);
            myJob.setOutputFormatClass(TextOutputFormat.class);

            FileInputFormat.addInputPath(myJob, new Path(userargs[0]));
            FileOutputFormat.setOutputPath(myJob, new Path(userargs[1]));

            System.exit(myJob.waitForCompletion(true) ? 0 : 1 );
    }

    public static  class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        Text emitkey = new Text();
        IntWritable emitvalue = new IntWritable(1);

        public void map(LongWritable key , Text value, Context context) throws IOException, InterruptedException {

            String filePathString = ((FileSplit) context.getInputSplit()).getPath().toString();                     
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while  (tokenizer.hasMoreTokens()){

                String filepathword = filePathString + "*" + tokenizer.nextToken();
                emitkey.set(filepathword);
                context.write(emitkey, emitvalue);
            }           
        }
    }

    public static  class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        Text emitkey = new Text();
        IntWritable emitvalue = new IntWritable();
        private MultipleOutputs<Text,IntWritable> multipleoutputs;

        public void setup(Context context) throws IOException, InterruptedException {
            multipleoutputs = new MultipleOutputs<Text,IntWritable>(context);
        }           

        public void reduce(Text key , Iterable <IntWritable> values, Context context)   throws IOException, InterruptedException {
            int sum = 0;

            for (IntWritable value : values){
                sum = sum + value.get();
            }
            String pathandword = key.toString();
            String[] splitted = pathandword.split("\\*");
            String path = splitted[0];
            String word = splitted[1];              
            emitkey.set(word);
            emitvalue.set(sum);
            System.out.println("word:" + word + "\t" + "sum:" + sum + "\t" + "path:  " + path);
            multipleoutputs.write(emitkey,emitvalue , path);
        }

        public void cleanup(Context context) throws IOException, InterruptedException {
            multipleoutputs.close();
        }
    }
}