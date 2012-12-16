package test.java.com.apitests.tests;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import test.java.com.apitests.helpers.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DiagnosticsTests {
	
	static String existingModelID, nonExistingModelID;
	
	@BeforeClass
	public static void TestSuiteSetup(){
		TestPropertiesLoader properties = new TestPropertiesLoader();
		existingModelID = properties.GetProperty("db.existing.model.id");
		nonExistingModelID =  properties.GetProperty("db.non.existing.model.id");
	}
	
	@Test
	public void testPing() {
		String resourcePath = "ping";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request successfully processed", baseServerResponse.getMessage());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testPingDB() {
		String resourcePath = "pingDB";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Object has been inserted to the DB and removed from the DB", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testExists_NonExistingModelID() {
		String resourcePath = "exists";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", nonExistingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath, params);
		Assert.assertEquals(404, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request model with id: " + existingModelID + " does not exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testExists_ExistingModelID() {
		String resourcePath = "exists";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", existingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request model with id: " + existingModelID + " exists", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testReset_NonExistingModelId() {
		String resourcePath = "reset";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", nonExistingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath, params);
		Assert.assertEquals(404, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Job with id " + nonExistingModelID + " doesn't exist", baseServerResponse.getMessage());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testReset_ExistingModelId() {
		String resourcePath = "reset";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", existingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Reset the ds model with id: " + existingModelID, baseServerResponse.getMessage());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}

}
