package test.java.com.apitests.tests;

import java.util.ArrayList;
import java.util.Hashtable;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

import test.java.com.apitests.helpers.BaseServerResponse;
import test.java.com.apitests.helpers.RedirectServerResponse;
import test.java.com.apitests.helpers.RequestUtils;
import test.java.com.apitests.helpers.TestHelpers;

public class ComputationTests {
	
	static String jobId;
	enum Label { worker, object, category};
	
	@BeforeClass
	public static void TestSuiteSetup(){
		jobId = TestHelpers.createNewJob();
	}
	
	@Test
	public void test_Compute() {
		int no_iterations= 10;
		
		//add categories
		String resourcePath = "/jobs/" + jobId + "/categories";
		Form formData = new Form();
			
		ArrayList<Hashtable<String, Object>> categories = new ArrayList<Hashtable<String, Object>>();
		Hashtable<String, Double> missClasificationCost = new Hashtable<String, Double>();
		missClasificationCost.put("category1", 0.5);
		missClasificationCost.put("category2", 1.0);
		
		Hashtable<String, Object> category1 = new Hashtable<String, Object>();
		category1.put("prior", 1.0);
		category1.put("name", "category1");
		category1.put("misclassification_cost", missClasificationCost);
		categories.add(category1);
			
		Hashtable<String, Object> category2 = new Hashtable<String, Object>();
		category2.put("prior", 1.0);
		category2.put("name", "category2");
		category2.put("misclassification_cost", missClasificationCost);
		categories.add(category2);

		formData.add("categories", categories);
		ClientResponse serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		
		//add labels
		resourcePath = "jobs/" + jobId + "/assignedLabels";
		formData = new Form();	
		String [][] labelData = { 
				{"worker1", 	"object1",	  	"category1"},
				{"worker1", 	"object2", 		"category1"},
				{"worker1", 	"object3",      "category1"},
				{"worker1", 	"object4",     	"category1"},
				{"worker1", 	"object5",      "category1"},
				{"worker2", 	"object1",    	"category2"},
				{"worker2", 	"object2", 		"category1"},
				{"worker2", 	"object3",      "category2"},
				{"worker2", 	"object2",     	"category1"},
				{"worker2", 	"object5",      "category1"},
				{"worker3", 	"object1",    	"category2"},
				{"worker3", 	"object2", 		"category1"},
				{"worker3", 	"object3",      "category2"},
				{"worker3", 	"object4",     	"category1"},
				{"worker3", 	"object5",      "category2"},
				{"worker4", 	"object1",    	"category2"},
				{"worker4", 	"object2", 		"category1"},
				{"worker4", 	"object3",      "category2"},
				{"worker4", 	"object4",     	"category1"},
				{"worker4", 	"object5",      "category2"},
				{"worker5", 	"object1",    	"category1"},
				{"worker5", 	"object2", 		"category2"},
				{"worker5", 	"object3",      "category1"},
				{"worker5", 	"object4",     	"category2"},	
				{"worker5", 	"object5",      "category1"}
				};
		
		ArrayList<Hashtable<String, String>> labels = new ArrayList<Hashtable<String, String>>();
		for (int i = 0; i < labelData.length; i++ ){	
			Hashtable<String, String> label = new Hashtable<String, String>();
			String[] labelInfo = labelData[i];
			label.put("workerName", labelInfo[0]);	
			label.put("objectName", labelInfo[1]);	
			label.put("categoryName", labelInfo[2]);
			labels.add(label);
		}		
		formData.add("labels", labels);

		serverResponse = RequestUtils.InvokePutRequest(resourcePath, formData);
		Assert.assertEquals(200, serverResponse.getStatus());
		
		//compute
		resourcePath = "jobs/" + jobId + "/compute";
		formData = new Form();	
		formData.add("iterations", no_iterations);
		serverResponse = RequestUtils.InvokePostRequest(resourcePath, formData);
		RedirectServerResponse redirectServerResponse = new RedirectServerResponse().getResponseFromJson(serverResponse.getEntity(String.class));
		Assert.assertEquals("OK", redirectServerResponse.getStatus());
			
		//check that the redirect shows the correct information
		BaseServerResponse baseServerResponse  = TestHelpers.getJobStatus(jobId, redirectServerResponse.getRedirect());
		Assert.assertEquals("Compute started", baseServerResponse.getResult());
		Assert.assertEquals("OK", baseServerResponse.getStatus());
		
		
	}
		

}
