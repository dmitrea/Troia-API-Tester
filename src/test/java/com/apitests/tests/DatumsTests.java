package test.java.com.apitests.tests;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.*;

public class DatumsTests {
	
static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup() throws JSONException{
		jobId = TestHelpers.createNewJob();	
	}
	

	@Test
	public void test_Get_Objects(){
		String resourcePath = "/jobs/" + jobId + "/datums";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
        RedirectServerResponse redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", redirectServerResponse.getStatus());	
		
		//check that the redirect shows the correct information
		BaseServerResponse baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		Assert.assertEquals("Costs set", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
	}
	
	
	
	
}