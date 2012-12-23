package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import test.java.com.apitests.helpers.*;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class AssignedLabelsTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup(){
		jobId = TestHelpers.createNewJob();	
	}
	
	@Test
	public void test_Put_AssignedLabels() {
		String resourcePath = "jobs/" + jobId + "/assignedLabels";
		Form formData = new Form();
		
		ArrayList<Hashtable<String, String>> labels = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> label = new Hashtable<String, String>();
		label.put("workerName", "worker1");
		label.put("objectName", "object1");
		label.put("categoryName", "category1");
		labels.add(label);
		
		formData.add("labels", labels);

		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
      
	
	@Test
	public void test_Get_AssignedLabels() {
		String resourcePath = "jobs/" + jobId + "/assignedLabels";

		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	
}