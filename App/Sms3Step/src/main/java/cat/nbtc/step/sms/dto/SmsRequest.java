package cat.nbtc.step.sms.dto;

public class SmsRequest {

	private String target;
	private String source;
	private String message;
	private String correlationId;
	private String submitedBy;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	
	public String getSubmitedBy() {
		return submitedBy;
	}
	public void setSubmitedBy(String submitedBy) {
		this.submitedBy = submitedBy;
	}
	@Override
	public String toString() {
		return "SmsRequest [target=" + target + ", source=" + source + ", message=" + message + ", correlationId="
				+ correlationId + ", submitedBy=" + submitedBy + "]";
	}

	
	
}
