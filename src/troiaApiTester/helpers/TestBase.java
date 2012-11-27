package troiaApiTester.helpers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.representation.Form;

public abstract class TestBase {

	public static String SERVER_BASE_URL = "http://localhost:8080/GetAnotherLabel/rest";
	
	public ClientResponse InvokeGetRequest(String uri, String resourcePath) {
		WebResource webResource = new Client().resource(uri).path(resourcePath);
		return webResource.get(ClientResponse.class);
	}

	public ClientResponse InvokeGetRequest(String uri, String resourcePath, MultivaluedMap<String, String> queryParams) {
	    WebResource webResource = new Client().resource(uri).path(resourcePath).queryParams(queryParams);
		return webResource.get(ClientResponse.class);
	}
	
	public ClientResponse InvokePostRequest(String Uri, String resourcePath, Form formData)
	{	
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(SERVER_BASE_URL).path(resourcePath);
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
	
	public int GetHttpResponse(ClientResponse response){
		return response.getStatus();
	}
}
