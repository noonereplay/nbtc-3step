package cat.nbtc.step.sms.dto;

public class SmsResponse {

	String responseCode;
	String description;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "SmsResponse [responseCode=" + responseCode + ", description=" + description + "]";
	}
	
	
}
