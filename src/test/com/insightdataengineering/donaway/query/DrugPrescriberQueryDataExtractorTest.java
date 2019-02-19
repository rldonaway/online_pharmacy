package com.insightdataengineering.donaway.query;

//import org.junit.jupiter.api.Test;

public class DrugPrescriberQueryDataExtractorTest {

    DrugPrescriberQueryDataExtractor dpqde = new DrugPrescriberQueryDataExtractor();
    
//    @Test
    public void testExtractLineItems() {
        String[] items = dpqde.extractLineItems("1000000001,Smith,James,AMBIEN,100");
        assert "1000000001,Smith,James".equals(items[0]);
        assert "AMBIEN".equals(items[1]);
        assert "100".equals(items[2]);
        items = dpqde.extractLineItems("1000000003,\"Johnson, Jr.\",James,CHLORPROMAZINE,1000");
        assert "1000000003,\"Johnson, Jr.\",James".equals(items[0]);
        assert "CHLORPROMAZINE".equals(items[1]);
        assert "1000".equals(items[2]);
        items = dpqde.extractLineItems("1275618837,\"BOYD, JR\",KEITH,IBUPROFEN,58.79");
        assert "1275618837,\"BOYD, JR\",KEITH".equals(items[0]);
        assert "IBUPROFEN".equals(items[1]);
        assert "58.79".equals(items[2]);
    }
    
    public static final void main(String... args) {
    	DrugPrescriberQueryDataExtractorTest test = new DrugPrescriberQueryDataExtractorTest();
    	test.testExtractLineItems();
    	System.out.println("DrugPrescriberQueryDataExtractorTest finished");
    }

}
