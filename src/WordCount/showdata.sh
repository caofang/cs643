#!/bin/sh

for OUTPUT in $(cat statelists)
do
	echo "===== "$OUTPUT" =====" 
	hadoop fs -cat wordcount/output/$OUTPUT/*
done