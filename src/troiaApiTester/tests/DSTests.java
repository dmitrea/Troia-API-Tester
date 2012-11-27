package troiaApiTester.tests;

import troiaApiTester.helpers.*;
import java.util.ArrayList;
import java.util.Hashtable;
import junit.framework.Assert;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class DSTests extends TestBase {
	public static String nonExistingModelID = "test12345";
	public static String existingModelID = "12345";
	
	
	@Test
	public void testLoadGoldLabels_NonExistingModelID() {
		String resourcePath = "loadGoldLabels";
		Form formData = new Form();
		formData.add("id", nonExistingModelID);
		
		ArrayList<Hashtable<String, String>> goldLabels = new ArrayList<>();
		Hashtable<String, String> goldLabel1 = new Hashtable<String, String>();
		goldLabel1.put("objectName", "url1");
		goldLabel1.put("correctCategory", "category1");
		goldLabels.add(goldLabel1);
		
		Hashtable<String, String> goldLabel2 = new Hashtable<String, String>();
		goldLabel2.put("objectName", "url2");
		goldLabel2.put("correctCategory", "category2");
		goldLabels.add(goldLabel2);
		
		formData.add("labels", goldLabels);
		
		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id " + nonExistingModelID +" doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	
	@Test
	public void testLoadGoldLabels_ExistingModelID() {
		String resourcePath = "loadGoldLabels";
		Form formData = new Form();
		formData.add("id", existingModelID);
		
		ArrayList<Hashtable<String, String>> goldLabels = new ArrayList<>();
		Hashtable<String, String> goldLabel1 = new Hashtable<String, String>();
		goldLabel1.put("objectName", "url1");
		goldLabel1.put("correctCategory", "category1");
		goldLabels.add(goldLabel1);
		
		Hashtable<String, String> goldLabel2 = new Hashtable<String, String>();
		goldLabel2.put("objectName", "url2");
		goldLabel2.put("correctCategory", "category2");
		goldLabels.add(goldLabel2);
		
		formData.add("labels", goldLabels);
		
		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
        BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Loaded 2 gold labels", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testLoadGoldLabel_NonExistingModelID() {
		String resourcePath = "loadGoldLabel";
		Form formData = new Form();
		formData.add("id", nonExistingModelID);
		
		ArrayList<Hashtable<String, String>> goldLabels = new ArrayList<>();
		Hashtable<String, String> goldLabel = new Hashtable<String, String>();
		goldLabel.put("objectName", "url1");
		goldLabel.put("correctCategory", "category1");
		goldLabels.add(goldLabel);
		
		formData.add("label", goldLabels);

		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id " + nonExistingModelID + " doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testLoadGoldLabel_ExistingModelID() {
		String resourcePath = "loadGoldLabel";
		Form formData = new Form();
		formData.add("id", existingModelID);
		
		ArrayList<Hashtable<String, String>> goldLabels = new ArrayList<>();
		Hashtable<String, String> goldLabel = new Hashtable<String, String>();
		goldLabel.put("objectName", "url1");
		goldLabel.put("correctCategory", "category1");
		goldLabels.add(goldLabel);
		
		formData.add("label", goldLabels);

		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
        BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
        //Assert.assertEquals("Loaded gold label ... ", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	
	@Test
	public void testLoadWorkerAssignedLabel() {
		String resourcePath = "loadWorkerAssignedLabel";
		Form formData = new Form();
		formData.add("id", "12345");
		
		ArrayList<Hashtable<String, String>> labels = new ArrayList<>();
		Hashtable<String, String> label = new Hashtable<String, String>();
		label.put("workerName", "url1");
		label.put("objectName", "category1");
		label.put("categoryName", "category1");
		labels.add(label);
		
		formData.add("label", labels);

		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id 12345 doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testLoadCategories() {
		String resourcePath = "loadCategories";
		Form formData = new Form();
		formData.add("id", "12345");
			
		ArrayList<Hashtable<String, Object>> categories = new ArrayList<>();
		Hashtable<String, Double> missClasificationCost = new Hashtable<String, Double>();
		missClasificationCost.put("notporn", 1.0);
		missClasificationCost.put("porn", 0.5);
		
		Hashtable<String, Object> category1 = new Hashtable<String, Object>();
		category1.put("prior", 1.0);
		category1.put("name", "porn");
		category1.put("misclassification_cost", missClasificationCost);
		categories.add(category1);
			
		Hashtable<String, Object> category2 = new Hashtable<String, Object>();
		category2.put("prior", 1.0);
		category2.put("name", "porn");
		category2.put("misclassification_cost", missClasificationCost);
		categories.add(category2);

		formData.add("categories", categories);
		ClientResponse serverResponse = InvokePostRequest(SERVER_BASE_URL, resourcePath, formData);
        BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class)); 
			
        Assert.assertEquals("Built a request model with 2 categories", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
		
		
		}
	
}