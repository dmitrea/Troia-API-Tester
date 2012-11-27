package troiaApiTester.helpers;

import com.google.gson.Gson;

public class BaseServerResponse implements IServerResponse {
	
	private String message;
	private String status;
	private String timestamp;
	
	public BaseServerResponse(){
		this.message = new String();
		this.status = new String();
		this.timestamp = new String();	
	}
	
	public BaseServerResponse(String message, String status, String timestamp) {
		super();
		this.message = message;
		this.status = status;
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public BaseServerResponse GetGsonResponse(String stringResponse) {
		// TODO Auto-generated method stub
		return new Gson().fromJson(stringResponse, this.getClass());
	}
	
}
