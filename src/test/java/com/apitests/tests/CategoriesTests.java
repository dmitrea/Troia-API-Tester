package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.*;

public class CategoriesTests {
	
	static String existingModelId, nonexistingModelId;

	@BeforeClass
	public static void TestSuiteSetup(){
		TestPropertiesLoader properties = new TestPropertiesLoader();
		existingModelId = properties.GetProperty("db.existing.model.id");
		nonexistingModelId =  properties.GetProperty("db.non.existing.model.id");
	}
	
	@Test
	public void testLoadCategories_ValidData() {
		String resourcePath = "loadCategories";
		Form formData = new Form();
		formData.add("id", existingModelId);
			
		ArrayList<Hashtable<String, Object>> categories = new ArrayList<Hashtable<String, Object>>();
		Hashtable<String, Double> missClasificationCost = new Hashtable<String, Double>();
		missClasificationCost.put("category1", 1.0);
		missClasificationCost.put("category2", 0.5);
		
		Hashtable<String, Object> category1 = new Hashtable<String, Object>();
		category1.put("prior", 1.0);
		category1.put("name", "category2");
		category1.put("misclassification_cost", missClasificationCost);
		categories.add(category1);
			
		Hashtable<String, Object> category2 = new Hashtable<String, Object>();
		category2.put("prior", 1.0);
		category2.put("name", "category2");
		category2.put("misclassification_cost", missClasificationCost);
		categories.add(category2);

		formData.add("categories", categories);
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
        BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
			
        Assert.assertEquals("Built a request model with 2 categories", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
}