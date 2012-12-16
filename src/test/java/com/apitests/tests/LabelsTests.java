package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import test.java.com.apitests.helpers.*;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class LabelsTests {
	
	static String existingModelId, nonexistingModelId;

	@BeforeClass
	public static void TestSuiteSetup(){
		TestPropertiesLoader properties = new TestPropertiesLoader();
		existingModelId = properties.GetProperty("db.existing.model.id");
		nonexistingModelId =  properties.GetProperty("db.non.existing.model.id");
	}
	
	@Test
	public void testLoadWorkerAssignedLabel_ValidAssignedLabels() {
		String resourcePath = "loadWorkerAssignedLabel";
		Form formData = new Form();
		formData.add("id", existingModelId);
		
		ArrayList<Hashtable<String, String>> labels = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> label = new Hashtable<String, String>();
		label.put("workerName", "url1");
		label.put("objectName", "category1");
		label.put("categoryName", "category1");
		labels.add(label);
		
		formData.add("label", labels);

		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
        
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 
        Assert.assertEquals("Job with id " + existingModelId +" doesn't exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	

	
	@Test
	public void testLoadObjects_EmptyObjectData() {
		String resourcePath = "loadObjects";
		Form formData = new Form();
		formData.add("id", existingModelId);
		formData.add("objects", null);
		
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
        String response = serverResponse.getEntity(String.class);
        Assert.assertEquals(404, serverResponse.getStatus());
	}
	
}