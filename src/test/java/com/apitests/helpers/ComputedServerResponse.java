package test.java.com.apitests.helpers;

import com.google.gson.Gson;

public class ComputedServerResponse implements IServerResponse {
	
	private String timestamp;
	private String status;
	private String redirect;
	
	public ComputedServerResponse(){
		this.timestamp = new String();	
		this.status = new String();
		this.redirect = new String();
	}
	
	public ComputedServerResponse(String timestamp,  String status, String redirect) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.redirect = redirect;
	}
	
	public String getRedirect() {
		return this.redirect;
	}
	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public ComputedServerResponse getResponseFromJson(String stringResponse) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(stringResponse, this.getClass());
	}
	
}
