
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;



public class WordCount  {

    public static void main(String[] args) throws Exception {

        System.out.print("===== trace =====\n");

        
    
        String line = "hello-hello- world\n\n hello_world hello ";
        int i = 0;
        Pattern p = Pattern.compile("\\W|_");
        // Matcher m = p.matcher( line );
        // while (m.find()) {
        //     i++;
        // }
        // System.out.println(i); // Prints 2


        for (String word: p.split(line)) {
            if (word.isEmpty()) {
                continue;
            }
            System.out.println(word);
        }
                




    }
}