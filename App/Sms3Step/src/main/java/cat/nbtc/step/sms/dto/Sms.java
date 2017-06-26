package cat.nbtc.step.sms.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="NBTC_APP3_SMS",schema="THREE_STOREY_APP")
public class Sms implements Serializable {

	
	
	@Id
	@SequenceGenerator(name="NBTC_APP3_SMS_SEQ",sequenceName="NBTC_APP3_SMS_SEQ",schema="THREE_STOREY_APP",allocationSize=1)
	@GeneratedValue(generator = "NBTC_APP3_SMS_SEQ", strategy=GenerationType.SEQUENCE)
	@Column(name="SMS_ID")
	private Integer messageId;
	
	@Column(name="SMSC_ID")
	private String smscMessageId;
	
	@Column(name="SMSC_STATUS")
	private String smscStatus;
	
	@Column(name="SUBMITED_BY")
	private String submitedBy;
	
	@Column(name="SOURCE")
	private String source;
	
	@Column(name="TARGET")
	private String target;
	
	@Column(name="MESSAGE")
	private String message;
	
	@Column(name="CORRELATION_ID")
	private String correlationId;
	
	@Transient
	private Exception exception;
	
	
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

	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getSmscMessageId() {
		return smscMessageId;
	}
	public void setSmscMessageId(String smscMessageId) {
		this.smscMessageId = smscMessageId;
	}
	public String getSmscStatus() {
		return smscStatus;
	}
	public void setSmscStatus(String smscStatus) {
		this.smscStatus = smscStatus;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getSubmitedBy() {
		return submitedBy;
	}
	public void setSubmitedBy(String submitedBy) {
		this.submitedBy = submitedBy;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	
	
}
