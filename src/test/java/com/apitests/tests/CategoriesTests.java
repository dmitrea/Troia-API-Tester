package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.*;

public class CategoriesTests {
	
	static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup() throws JSONException{
		jobId = TestHelpers.createNewJob();
	}
	
	@Test
	public void test_POST_GET_Categories() throws JSONException {
		
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
		Assert.assertEquals("OK", response.get("status").toString());
		String redirectID = response.get("redirect").toString();
		
		//check that the redirect shows the correct information
		String getJobStatusCommand = "/jobs/" + jobId + "/status/" + redirectID;
		response = RequestUtils.InvokeGetRequest(getJobStatusCommand, 200);
		Assert.assertEquals("Categories added", response.get("result"));
		Assert.assertEquals("OK", response.get("status"));
		
		//GET categories 
		String getCategoriesCommand = "/jobs/" + jobId + "/categories";
		response = RequestUtils.InvokeGetRequest(getCategoriesCommand, 200);
		Assert.assertEquals("OK", response.get("status").toString());
		redirectID = response.get("redirect").toString();
		
		//check that the redirect contains the correct information
		ArrayList<String> initialCategories = new ArrayList<String>();
		initialCategories.add("category1"); 
		initialCategories.add("category2");
		
		getJobStatusCommand = "/jobs/" + jobId + "/status/" + redirectID;
		response = RequestUtils.InvokeGetRequest(getJobStatusCommand, 200);
		Assert.assertEquals("OK", response.get("status").toString());
		JSONArray jsonCategories = response.getJSONArray("result");
		List<String> jsonCategoriesList = new ArrayList<String>();
		for (int i = 0; i < jsonCategories.length(); i++) {  
			jsonCategoriesList.add( jsonCategories.getString(i) );
		}
		
		Assert.assertEquals(2, jsonCategoriesList.size());
		for (String category: initialCategories){
			Assert.assertTrue(jsonCategoriesList.contains(category));
		}	
	}
	
	
}