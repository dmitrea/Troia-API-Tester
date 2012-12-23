package test.java.com.apitests.helpers;

import junit.framework.Assert;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class TestHelpers {
	
	public static String generateRandomJobId(){
		return System.currentTimeMillis() + "job_id";
	}
	
	public static String generateRandomWorkerId(){
		return System.currentTimeMillis() + "worker_id";
	}
	
	public static String createNewJob(){
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "");
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		BaseServerResponse baseServerResponse = new BaseServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		return baseServerResponse.getResult().split(":")[1].trim();
	}
	
}
