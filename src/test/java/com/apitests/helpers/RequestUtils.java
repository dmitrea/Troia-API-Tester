package test.java.com.apitests.helpers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.representation.Form;

public class RequestUtils {

	public static String SERVER_BASE_URL = new TestPropertiesLoader().GetProperty("server.url");
	
	public static ClientResponse InvokeGetRequest(String resourcePath) {
		WebResource webResource = new Client().resource(SERVER_BASE_URL).path(resourcePath);
		return webResource.get(ClientResponse.class);
	}
	
	public static ClientResponse InvokeGetRequest(String uri, String resourcePath) {
		WebResource webResource = new Client().resource(uri).path(resourcePath);
		return webResource.get(ClientResponse.class);
	}
	
	public static ClientResponse InvokeGetRequest(String resourcePath, MultivaluedMap<String, String> queryParams) {
	    WebResource webResource = new Client().resource(SERVER_BASE_URL).path(resourcePath).queryParams(queryParams);
		return webResource.get(ClientResponse.class);
	}

	public static ClientResponse InvokeGetRequest(String uri, String resourcePath, MultivaluedMap<String, String> queryParams) {
	    WebResource webResource = new Client().resource(uri).path(resourcePath).queryParams(queryParams);
		return webResource.get(ClientResponse.class);
	}
		
	public static ClientResponse InvokePostRequest(String resourcePath, Form formData)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
}
