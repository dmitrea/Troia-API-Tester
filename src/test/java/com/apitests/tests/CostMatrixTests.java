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
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class CostMatrixTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup() throws JSONException{
			jobId = TestHelpers.createNewJob();
	}
	
	@Test
	public void testPostGetCosts() throws JSONException {
		
		//POST categories
		String postCategoriesCommand = "/jobs/" + jobId + "/categories";
		Form formData = new Form();
			
		ArrayList<Hashtable<String, Object>> categories = new ArrayList<Hashtable<String, Object>>();
		Hashtable<String, Double> missClasificationCost = new Hashtable<String, Double>();
		missClasificationCost.put("category1", 1.0);
		missClasificationCost.put("category2", 0.5);
		
		Hashtable<String, Object> category1 = new Hashtable<String, Object>();
		category1.put("prior", 1.0);
		category1.put("name", "category1");
		category1.put("misclassification_cost", missClasificationCost);
		categories.add(category1);
			
		Hashtable<String, Object> category2 = new Hashtable<String, Object>();
		category2.put("prior", 0.5);
		category2.put("name", "category2");
		category2.put("misclassification_cost", missClasificationCost);
		categories.add(category2);

		formData.add("categories", categories);
		JSONObject response = RequestUtils.InvokePostRequest(postCategoriesCommand, formData, 200);
		
		//POST the costs
		String postCostsCommand = "jobs/" + jobId + "/costs";
		formData = new Form();
		
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

		response = RequestUtils.InvokePostRequest(postCostsCommand, formData, 200);
		Assert.assertEquals("OK", response.get("status"));
		String redirectID = response.get("redirect").toString();
		
		//check that the redirect shows the correct information
		String getJobStatusCommand = "/jobs/" + jobId + "/status/" + redirectID;
		response = RequestUtils.InvokeGetRequest(getJobStatusCommand, 200);
		Assert.assertEquals("Costs set", response.get("result"));
		Assert.assertEquals("OK", response.get("status"));
		
		//GET the costs 
		String getCostsCommand = "/jobs/" + jobId + "/costs";
		response = RequestUtils.InvokeGetRequest(getCostsCommand, 200);
		Assert.assertEquals("OK", response.get("status"));
		redirectID = response.get("redirect").toString();
		
		getJobStatusCommand = "/jobs/" + jobId + "/status/" + redirectID;
		response = RequestUtils.InvokeGetRequest(getJobStatusCommand, 200);
		Assert.assertEquals("OK", response.get("status"));
		System.out.println(response.get("result"));
		
        //check that the redirect shows the correct information
		//baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		//System.out.println(baseServerResponse.getResult());
		//Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
      
	
	@Test
	public void testGetCosts_NonExistingJobId() throws JSONException {
		String randomJobId = TestHelpers.generateRandomJobId();
		String resourcePath = "jobs/" + randomJobId + "/costs";

		JSONObject response = RequestUtils.InvokeGetRequest(resourcePath, 400);
	}
	
	
}