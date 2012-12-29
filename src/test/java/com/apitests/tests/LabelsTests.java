package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import test.java.com.apitests.helpers.*;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class LabelsTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup() throws JSONException{
		jobId = TestHelpers.createNewJob();	
	}
	
	@Test
	public void testAddGetAssignedLabels() throws JSONException {
		String getAssignedLabelsCommand = "jobs/" + jobId + "/assignedLabels";
		Form formData = new Form();
		
		ArrayList<Hashtable<String, String>> labels = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> label1 = new Hashtable<String, String>();
		label1.put("workerName", "worker1");
		label1.put("objectName", "object1");
		label1.put("categoryName", "category1");
		labels.add(label1);
		
		Hashtable<String, String> label2 = new Hashtable<String, String>();
		label2.put("workerName", "worker2");
		label2.put("objectName", "object2");
		label2.put("categoryName", "category2");
		labels.add(label2);
		formData.add("labels", labels);

		JSONObject response = RequestUtils.InvokePostRequest(getAssignedLabelsCommand, formData, 200);
		Assert.assertEquals("OK", response.get("status"));
		String redirectID = response.get("redirect").toString();
		
		//check that the redirect shows the correct information
		String getJobStatusCommand = "/jobs/" + jobId + "/status/" + redirectID;
		response = RequestUtils.InvokeGetRequest(getJobStatusCommand, 200);
		Assert.assertEquals("Assigns added", response.get("result"));
		Assert.assertEquals("OK", response.get("status"));
				
		//retrieve the assigned labels 
		response = RequestUtils.InvokeGetRequest(getAssignedLabelsCommand, 200);
		Assert.assertEquals("OK", response.get("status"));
		
		//TBD - check the corectness of the data returned
	}
      
	
	@Test
	public void test_Put_Get_goldData(){
		//POST the gold labels
		String resourcePath = "/jobs/" + jobId + "/goldDatums";
		Form formData = new Form();
		ArrayList<Hashtable<String, String>> goldDatums = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> goldDatum = new Hashtable<String, String>();
		goldDatum.put("correctCategory", "notporn");
		goldDatum.put("objectName", "http://google.com");
		
		goldDatums.add(goldDatum);
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
        RedirectServerResponse redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", redirectServerResponse.getStatus());
		Assert.assertNotNull(redirectServerResponse.getRedirect());	
		
		//check that the redirect shows the correct information
		BaseServerResponse baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		Assert.assertEquals("Correct datums added", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
					
		//GET the gold labels 
		serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", redirectServerResponse.getStatus());
						
		//check that the redirect shows the correct information
		//baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		//System.out.println(baseServerResponse.getResult());
		//Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	
	@Test
	public void test_Put_Get_evaluationData(){
		//PUT the evaluation labels
		String resourcePath = "/jobs/" + jobId + "/evaluationDatums";
		Form formData = new Form();
		ArrayList<Hashtable<String, String>> goldDatums = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> goldDatum = new Hashtable<String, String>();
		goldDatum.put("correctCategory", "notporn");
		goldDatum.put("objectName", "http://google.com");
		
		goldDatums.add(goldDatum);
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
        RedirectServerResponse redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", redirectServerResponse.getStatus());
		
		//check that the redirect shows the correct information
		BaseServerResponse baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		Assert.assertEquals("Correct datums added", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
								
		//GET the evaluation labels 
		serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("OK", redirectServerResponse.getStatus());
										
		//check that the redirect shows the correct information
		//baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		//System.out.println(baseServerResponse.getResult());
		//Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
}