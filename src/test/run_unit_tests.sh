#!/bin/bash

find ./../main -name "*.java" > sources.txt
find . -name "*.java" > tests.txt

javac -d ./../../.classes @sources.txt
javac -classpath ./../../.classes -d ./../../.classes @tests.txt

java -cp ./../../.classes com.insightdataengineering.donaway.inputoutput.FileInputTest
java -cp ./../../.classes com.insightdataengineering.donaway.inputoutput.FileOutputTest
java -cp ./../../.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryDataExtractorTest
java -cp ./../../.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryTest
java -cp ./../../.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryBigTest
java -cp ./../../.classes com.insightdataengineering.donaway.sanitycheck.CheckGeneratedOutput
