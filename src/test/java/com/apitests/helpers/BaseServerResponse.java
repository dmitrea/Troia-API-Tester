package test.java.com.apitests.helpers;

import com.google.gson.Gson;

public class BaseServerResponse implements IServerResponse {
	
	private String timestamp;
	private String result;
	private String status;
	
	public BaseServerResponse(){
		this.result = new String();
		this.status = new String();
		this.timestamp = new String();	
	}
	
	public BaseServerResponse(String result, String status, String timestamp) {
		super();
		this.result = result;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public String getResult() {
		return this.result;
	}
	public void setResult(String result) {
		this.result = result;
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
	public BaseServerResponse getResponseFromJson(String stringResponse) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(stringResponse, this.getClass());
	}
	
}
