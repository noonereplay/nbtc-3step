package cat.nbtc.step.sms.dto;

public class Sms {

	private String source;
	private String target;
	private String message;
	private String messageId;
	private String smscMessageId;
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSmscMessageId() {
		return smscMessageId;
	}
	public void setSmscMessageId(String smscMessageId) {
		this.smscMessageId = smscMessageId;
	}
	
	
	
}
