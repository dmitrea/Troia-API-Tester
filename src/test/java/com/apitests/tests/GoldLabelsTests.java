package test.java.com.apitests.tests;

import java.util.ArrayList;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.*;

public class GoldLabelsTests {
	
	static String existingModelId, nonexistingModelId;

	@BeforeClass
	public static void TestSuiteSetup(){
		TestPropertiesLoader properties = new TestPropertiesLoader();
		existingModelId = properties.GetProperty("db.existing.model.id");
		nonexistingModelId =  properties.GetProperty("db.non.existing.model.id");
	}
	
	@Test
	public void testLoadGoldLabels_NonexistingModelId() {
		String resourcePath = "loadGoldLabels";
		
		ArrayList<GoldLabel> goldLabels = new ArrayList<GoldLabel>();
		GoldLabel goldLabel1 = new GoldLabel("url1", "category1");
		GoldLabel goldLabel2 = new GoldLabel("url2", "category2");
		goldLabels.add(goldLabel1);
		goldLabels.add(goldLabel2);
		
		Form formData = new Form();
		formData.add("id", nonexistingModelId);
		formData.add("labels", goldLabels);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(404, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id " + nonexistingModelId +" doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	
	@Test
	public void testLoadGoldLabels_existingModelId() {
		String resourcePath = "loadGoldLabels";
		ArrayList<GoldLabel> goldLabels = new ArrayList<GoldLabel>();
		GoldLabel goldLabel1 = new GoldLabel("url1", "category1");
		GoldLabel goldLabel2 = new GoldLabel("url2", "category2");
		goldLabels.add(goldLabel1);
		goldLabels.add(goldLabel2);
		
		Form formData = new Form();
		formData.add("id", existingModelId);
		formData.add("labels", goldLabels);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
        BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Loaded 2 gold labels", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testLoadGoldLabel_NonexistingModelId() {
		String resourcePath = "loadGoldLabel";
		GoldLabel goldLabel = new GoldLabel("url1", "category1");
		
		Form formData = new Form();
		formData.add("id", nonexistingModelId);
		formData.add("label", goldLabel);
	
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(404, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id " + nonexistingModelId + " doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testLoadGoldLabel_existingModelId() {
		String resourcePath = "loadGoldLabel";
		GoldLabel goldLabel = new GoldLabel("url1", "category1");
		
		Form formData = new Form();
		formData.add("id", existingModelId);
		formData.add("label", goldLabel);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
        BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        //Assert.assertEquals("Loaded gold label: " + goldLabel.toString(), baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testMarkLabelAsGold_ValidModelId_ValidLabel(){
		String resourcePath = "markLabelAsGold";
		GoldLabel goldLabel = new GoldLabel("url1", "category1");
		
		Form formData = new Form();
		formData.add("id", existingModelId);
		formData.add("label", goldLabel);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        //Assert.assertEquals(goldLabel.toString() + " marked as gold", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());		
	}
	
	@Test
	public void testMarkLabelAsGold_InvalidModelId_ValidLabel(){
		String resourcePath = "markLabelAsGold";
		GoldLabel goldLabel = new GoldLabel("url1", "category1");
		
		Form formData = new Form();
		formData.add("id", nonexistingModelId);
		formData.add("label", goldLabel);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(404, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("Failure", baseServerResponse.getStatus());		
	}
	
	
	@Test
	public void testMarkLabelAsGold_ValidModelId_NullLabel(){
		String resourcePath = "markLabelAsGold";
		
		Form formData = new Form();
		formData.add("id", existingModelId);
		formData.add("label", null);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(404, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("Failure", baseServerResponse.getStatus());		
	}
	
	@Test
	public void testMarkLabelAsGold_ValidModelId_MissingLabelParam(){
		String resourcePath = "markLabelAsGold";
		
		Form formData = new Form();
		formData.add("id", existingModelId);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(404, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
		Assert.assertEquals("Failure", baseServerResponse.getStatus());		
	}
	
	
}