package test.java.com.apitests.helpers;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.representation.Form;

public class TestHelpers {
	
	public static String generateRandomJobId(){
		return System.currentTimeMillis() + "job_id";
	}
	
	public static String generateRandomWorkerId(){
		return System.currentTimeMillis() + "worker_id";
	}
	
	public static String createNewJob() throws JSONException{
		String resourcePath = "jobs";
		Form formData = new Form();
		formData.add("id", "");
		JSONObject response = RequestUtils.InvokePostRequest(resourcePath, formData, 200);
		String result = response.get("result").toString();
		System.out.println(result);
		return result.split(":")[1].trim();
	}
}
