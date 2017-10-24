#!/bin/sh

for OUTPUT in $(head -n 3 statelists)
do
	echo "===== "$OUTPUT" =====" 
	hadoop fs -cat wordcount/output/$OUTPUT/*
done