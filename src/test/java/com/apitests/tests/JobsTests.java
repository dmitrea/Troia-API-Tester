package test.java.com.apitests.tests;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import test.java.com.apitests.helpers.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class JobsTests {
	
	@Test
	public void testCreateJob_NoIdProvided() throws JSONException {
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "");
		JSONObject response = RequestUtils.InvokePostRequest(resourcePath, formData, 200);
		Assert.assertTrue(response.get("result").toString().startsWith("New job created with ID: RANDOM__"));
		Assert.assertEquals("OK", response.get("status"));
	}
	
	@Test
	public void testCreateJob_JobIdProvided() throws JSONException {
		String resourcePath = "jobs";
		Form formData = new Form();
		String jobId = TestHelpers.generateRandomJobId();
		formData.add("id", jobId);
		JSONObject response = RequestUtils.InvokePostRequest(resourcePath, formData, 200);

		Assert.assertEquals("New job created with ID: " + jobId, response.get("result"));
		Assert.assertEquals("OK", response.get("status"));
	}
	
	@Test
	public void testCreateJob_AlreadyExistingJobId() throws JSONException {
		String resourcePath = "jobs";
		Form formData = new Form();
		String jobId = TestHelpers.generateRandomJobId();
		formData.add("id", jobId);
		JSONObject response = RequestUtils.InvokePostRequest(resourcePath, formData, 200);
		response = RequestUtils.InvokePostRequest(resourcePath, formData, 405);

		Assert.assertEquals("Job with ID: " + jobId + " already exists", response.get("result"));
		Assert.assertEquals("ERROR", response.get("status"));
	}
	
	@Test
	public void testDeleteExistingJobId() throws JSONException {
		//create a new job
		String resourcePath = "jobs";
		String jobId = TestHelpers.generateRandomJobId();
		Form formData = new Form();
		formData.add("id", jobId);
		JSONObject response = RequestUtils.InvokePostRequest(resourcePath, formData, 200);
		
		RequestUtils.InvokeDeleteRequest(resourcePath, formData, 200);
	}
	
	@Test
	public void testDeleteNonExistingJobId() throws JSONException {
		String randomJobId = TestHelpers.generateRandomJobId();
		String resourcePath = "jobs/" + randomJobId;
		RequestUtils.InvokeDeleteRequest(resourcePath, 200);
	}
	
	@Test
	public void testGet_NonExistingJobId() throws JSONException {
		String jobId = TestHelpers.generateRandomJobId();
		String resourcePath = "jobs/" + jobId;
		JSONObject response = RequestUtils.InvokeGetRequest(resourcePath, 400);
		
		Assert.assertEquals("Job with ID: " + jobId + " does not exist", response.get("result"));
		Assert.assertEquals("ERROR", response.get("status"));
	}
	

}