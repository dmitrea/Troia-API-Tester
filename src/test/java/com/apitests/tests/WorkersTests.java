package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import test.java.com.apitests.helpers.*;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class WorkersTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup(){
		jobId = TestHelpers.createNewJob();	
	}
	
	@Test
	public void test_Get_Workers_EmptyData() {
		String resourcePath = "jobs/" + jobId + "/workers";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());
	}
	
	@Test
	public void test_Get_Workers_NonEmptyData(){
		//fill-in some assigned labels
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
		
		//retrieve the workers
		resourcePath = "jobs/" + jobId + "/workers";
		serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());        
		
		ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());
		
	}
	  
	@Test
	public void test_Get_Worker_NotExistingWorker() {
		String resourcePath = "jobs/" + jobId + "/workers/" + TestHelpers.generateRandomWorkerId();

		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());
	}
	
	
}