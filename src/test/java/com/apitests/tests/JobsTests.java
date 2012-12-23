package test.java.com.apitests.tests;

import junit.framework.Assert;
import org.junit.Test;
import test.java.com.apitests.helpers.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class JobsTests {
	
	
	@Test
	public void test_Put_JobIdNotProvided() {
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "");
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertTrue(baseServerResponse.getResult().startsWith("New job created with ID: RANDOM__"));
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Put_JobIdProvided() {
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "12");
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("New job created with ID: 12", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Delete_ExistingJobId() {
		//create a new job
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "12");
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		serverResponse = RequestUtils.InvokeDeleteRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Removed job with ID: 12", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Delete_NonExistingJobId() {
		String resourcePath = "jobs";
		Form formData = new Form();
		String randomJobId = TestHelpers.generateRandomJobId();
		formData.add("id", randomJobId);
		
		ClientResponse serverResponse = RequestUtils.InvokeDeleteRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Removed job with ID: " + randomJobId, baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	@Test
	public void test_Get_NonExistingJobId() {
		String randomJobId = TestHelpers.generateRandomJobId();
		String resourcePath = "jobs/" + randomJobId;
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
		Assert.assertEquals(400, serverResponse.getStatus());
		
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("Job with ID: " + randomJobId + " doesn't exists", baseServerResponse.getResult());
		Assert.assertEquals("ERR", baseServerResponse.getStatus());
	}
	

}