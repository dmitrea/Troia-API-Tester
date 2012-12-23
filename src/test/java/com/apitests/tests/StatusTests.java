package test.java.com.apitests.tests;

import junit.framework.Assert;
import org.junit.Test;
import test.java.com.apitests.helpers.*;
import com.sun.jersey.api.client.ClientResponse;

public class StatusTests {
	

	@Test
	public void test_Status_Ping() {
		String resourcePath = "status/ping";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("System is working fine", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Status_PingDB() {
		String resourcePath = "status/pingDB";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Job storage com.datascience.core.storages.CachedJobStorage works fine", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Status_BadRequest() {
		String resourcePath = "status/pingDBB";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(404, serverResponse.getStatus());
	}
}
