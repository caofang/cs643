#!/bin/sh

echo "clearing output"
hadoop fs -rm -r wordcount/output*


for OUTPUT in $(cat statelists)
do
	echo "===== "$OUTPUT" =====" 
	hadoop jar WordCount.jar org.myorg.WordCount /user/ec2-user/wordcount/input/$OUTPUT /user/ec2-user/wordcount/output/$OUTPUT > /dev/null 2>&1
done