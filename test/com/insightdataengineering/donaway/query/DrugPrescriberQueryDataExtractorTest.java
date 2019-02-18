package com.insightdataengineering.donaway.query;

import org.junit.jupiter.api.Test;

public class DrugPrescriberQueryDataExtractorTest {

    DrugPrescriberQueryDataExtractor dpqde = new DrugPrescriberQueryDataExtractor();
    
    @Test
    public void testParseLineWithQuotes() {
        String toTest = "1000000003,\"Johnson, Jr.\",James,CHLORPROMAZINE,1000";
        String[] strings = dpqde.parseLineWithQuotes(toTest);
        assert "1000000003".equals(strings[0]);
        assert "\"Johnson, Jr.\"".equals(strings[1]);
        assert "James".equals(strings[2]);
        assert "CHLORPROMAZINE".equals(strings[3]);
        assert "1000".equals(strings[4]);
    }
    
    @Test
    public void testSplitOnFirstCommaQuotes() {
        String toTest = "something,\"else,too\"";
        String[] strings = dpqde.splitOnFirstCommaQuotes(toTest);
        assert "something".equals(strings[0]);
        assert "\"else,too\"".equals(strings[1]);
        toTest = "\"something\",\"else,too\"";
        strings = dpqde.splitOnFirstCommaQuotes(toTest);
        assert "\"something\"".equals(strings[0]);
        assert "\"else,too\"".equals(strings[1]);
    }
    
    @Test
    public void testSplitOnFirstComma() {
        String toTest = "something,else,something,really,else";
        String[] strings = dpqde.splitOnFirstComma(toTest);
        assert "something".equals(strings[0]);
        assert "else,something,really,else".equals(strings[1]);
    }
    
    @Test
    public void testExtractLineItems() {
        String[] items = dpqde.extractLineItems("1000000001,Smith,James,AMBIEN,100");
        assert "1000000001".equals(items[0]);
        assert "Smith".equals(items[1]);
        assert "James".equals(items[2]);
        assert "AMBIEN".equals(items[3]);
        assert "100".equals(items[4]);
        items = dpqde.extractLineItems("1000000003,\"Johnson, Jr.\",James,CHLORPROMAZINE,1000");
        assert "1000000003".equals(items[0]);
        assert "\"Johnson, Jr.\"".equals(items[1]);
        assert "James".equals(items[2]);
        assert "CHLORPROMAZINE".equals(items[3]);
        assert "1000".equals(items[4]);
        items = dpqde.extractLineItems("1275618837,\"BOYD, JR\",KEITH,IBUPROFEN,58.79");
        assert "1275618837".equals(items[0]);
        assert "\"BOYD, JR\"".equals(items[1]);
        assert "KEITH".equals(items[2]);
        assert "IBUPROFEN".equals(items[3]);
        assert "58.79".equals(items[4]);
    }
    
}
