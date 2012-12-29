package test.java.com.apitests.helpers;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.representation.Form;

public class RequestUtils {

	public static String SERVER_BASE_URL = new TestPropertiesLoader().GetProperty("server.url");
	
	public static JSONObject InvokeGetRequest(String resourcePath, int expectedHttpResponseCode) {	
		ClientConfig config = new DefaultClientConfig();
	    config.getClasses().add(JacksonJsonProvider.class);
	    WebResource webResource = Client.create(config).resource(SERVER_BASE_URL).path(resourcePath);
	    Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
	    return webResource.get(JSONObject.class);
	}
		
	public static JSONObject InvokePostRequest(String resourcePath, Form formData, int expectedHttpResponseCode)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);   
        Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
        return webResource.type(MediaType.APPLICATION_JSON).post(JSONObject.class, formData);
	}
	
	public static JSONObject InvokePutRequest(String resourcePath, int expectedHttpResponseCode) {
		WebResource webResource = new Client().resource(SERVER_BASE_URL).path(resourcePath);
        Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
		return webResource.put(JSONObject.class);
	}
	
	public static JSONObject InvokePutRequest(String resourcePath, Form formData, int expectedHttpResponseCode)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);
        Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
        return webResource.type(MediaType.APPLICATION_JSON).put(JSONObject.class, formData);
	}
	
	public static JSONObject InvokeDeleteRequest(String resourcePath, int expectedHttpResponseCode)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);
        Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
	    return webResource.type(MediaType.APPLICATION_JSON).delete(JSONObject.class);
	}	
	
	public static JSONObject InvokeDeleteRequest(String resourcePath, Form formData, int expectedHttpResponseCode)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);
        Assert.assertEquals(expectedHttpResponseCode, webResource.get(ClientResponse.class).getStatus());
	    return webResource.type(MediaType.APPLICATION_JSON).delete(JSONObject.class, formData);
	}	
}
