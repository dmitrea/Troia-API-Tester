package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.*;

public class DatumsTests {
	
static String jobId;
	
	@BeforeClass
	public static void TestSuiteSetup(){
		jobId = TestHelpers.createNewJob();	
	}
	
	@Test
	public void test_Put_goldDatums(){
		String resourcePath = "/jobs/" + jobId + "/goldDatums";
		Form formData = new Form();
		ArrayList<Hashtable<String, String>> goldDatums = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> goldDatum = new Hashtable<String, String>();
		goldDatum.put("correctCategory", "notporn");
		goldDatum.put("objectName", "http://google.com");
		
		goldDatums.add(goldDatum);
		ClientResponse serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
        ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());	
	}
	
	@Test
	public void test_Get_goldDatums(){
		String resourcePath = "/jobs/" + jobId + "/goldDatums";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
        ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());	
	}
	
	@Test
	public void test_Get_Datums(){
		String resourcePath = "/jobs/" + jobId + "/datums";
		ClientResponse serverResponse = RequestUtils.InvokeGetRequest(resourcePath);
        ComputedServerResponse computedServerResponse = new ComputedServerResponse().getResponseFromJson(serverResponse.getEntity(String.class)); 

		Assert.assertEquals("OK", computedServerResponse.getStatus());
		Assert.assertNotNull(computedServerResponse.getRedirect());	
	}
	
	
	
	
}