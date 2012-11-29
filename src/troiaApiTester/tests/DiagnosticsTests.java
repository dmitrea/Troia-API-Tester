package troiaApiTester.tests;

import junit.framework.Assert;

import org.junit.Test;

import troiaApiTester.helpers.BaseServerResponse;
import troiaApiTester.helpers.RequestUtils;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;


public class DiagnosticsTests {
	
	//temporary static IDs for some existing/nonExisting db objects - until the api is updated and the proper setUp/teardown methods are put in place 
	public static String nonExistingModelID = "test12345";
	public static String existingModelID = "12345";
	
	@Test
	public void testPing() {
		String resourcePath = "ping";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request successfully processed", baseServerResponse.getMessage());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testPingDB() {
		String resourcePath = "pingDB";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Object has been inserted to the DB and removed from the DB", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	//@Test
	public void testExists_NonExistingModelID() {
		String resourcePath = "exists";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", nonExistingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request model with id: 12345 does not exist", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testExists_ExistingModelID() {
		String resourcePath = "exists";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", existingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Request model with id: " + existingModelID + " exists", baseServerResponse.getMessage().trim());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}
	
	@Test
	public void testReset_NonExistingModelId() {
		String resourcePath = "reset";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", nonExistingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Job with id " + nonExistingModelID + " doesn't exist", baseServerResponse.getMessage());
		Assert.assertEquals("Failure", baseServerResponse.getStatus());
	}
	
	@Test
	public void testReset_ExistingModelId() {
		String resourcePath = "reset";
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("id", existingModelID);
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(RequestUtils.SERVER_BASE_URL, resourcePath, params);
		Assert.assertEquals(200, serverResponse.getStatus());

		BaseServerResponse baseServerResponse = new BaseServerResponse().GetGsonResponse(serverResponse.getEntity(String.class));
		Assert.assertEquals("Reset the ds model with id: " + existingModelID, baseServerResponse.getMessage());
		Assert.assertEquals("Success", baseServerResponse.getStatus());
	}

}
