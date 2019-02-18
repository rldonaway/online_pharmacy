#!/bin/bash
#
# Use this shell script to compile (if necessary) your code and then execute it. 
# Below is an example of what might be found in this file if your program was written in Python
#
#python ./src/pharmacy_counting.py ./input/itcont.txt ./output/top_cost_drug.txt

find ./src -name "*.java" > sources.txt
find ./test -name "*.java" > tests.txt

javac -d .classes @sources.txt
javac -classpath ./.classes -d .classes @tests.txt

java -cp ./.classes com.insightdataengineering.donaway.inputoutput.FileInputTest
echo FileInputTest finished

java -cp ./.classes com.insightdataengineering.donaway.inputoutput.FileOutputTest
echo FileOutputTest finished

java -cp ./.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryDataExtractorTest
echo DrugPrescriberQueryDataExtractorTest finished

java -cp ./.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryTest
echo DrugPrescriberQueryTest finished

java -cp ./.classes com.insightdataengineering.donaway.query.DrugPrescriberQueryBigTest
echo DrugPrescriberQueryBigTest finished

java -cp ./.classes com.insightdataengineering.donaway.TopCostDrugQuery ./insight_testsuite/tests/test_1/input/itcont.txt ./insight_testsuite/tests/test_1/output/top_cost_drug.txt
echo TopCostDrugQuery (small) is finished

java -cp ./.classes com.insightdataengineering.donaway.TopCostDrugQuery ./insight_testsuite/tests/test_2/input/de_cc_data.txt ./insight_testsuite/tests/test_2/output/top_cost_drug.txt
echo TopCostDrugQuery (big) is finished

java -cp ./.classes com.insightdataengineering.donaway.sanitycheck.CheckGeneratedOutput
echo CheckGeneratedOutput finished
