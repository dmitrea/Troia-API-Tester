package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import test.java.com.apitests.helpers.*;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class CostMatrixTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup(){
		jobId = TestHelpers.createNewJob();	
	}
	
	@Test
	public void test_Put_CostMatrix() {
		String resourcePath = "jobs/" + jobId + "/costs";
		Form formData = new Form();
		
		ArrayList<Hashtable<String, Object>> costMatrix = new ArrayList<Hashtable<String, Object>>();
		Hashtable<String, Object> row1 = new Hashtable<String, Object>();
		row1.put("categoryTo", "porn");
		row1.put("cost", 0);
		row1.put("categoryFrom", "porn");
		costMatrix.add(row1);
		
		Hashtable<String, Object> row2 = new Hashtable<String, Object>();
		row2.put("categoryTo", "porn");
		row2.put("cost", 1);
		row2.put("categoryFrom", "notporn");
		costMatrix.add(row2);
		
		Hashtable<String, Object> row3 = new Hashtable<String, Object>();
		row3.put("categoryTo", "notporn");
		row3.put("cost", 1);
		row3.put("categoryFrom", "notporn");
		costMatrix.add(row3);
		
		Hashtable<String, Object> row4 = new Hashtable<String, Object>();
		row4.put("categoryTo", "notporn");
		row4.put("cost", 0);
		row4.put("categoryFrom", "notporn");
		costMatrix.add(row4);
		
		formData.add("costs", costMatrix);

		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());
	}
      
	
	@Test
	public void test_Get_CostMatrix() {
		String resourcePath = "jobs/" + jobId + "/costs";

		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());
	}
	
	
}