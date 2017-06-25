package cat.nbtc.step.sms.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.DataCoding;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cat.nbtc.step.sms.dto.Sms;



public class SmscService {
	
	private static final Logger logger = LoggerFactory.getLogger(SmscService.class);
	private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();;

	@Autowired
	private SMPPSession smppSession;
	
	@Value("${smsc.addr}")
	private String ipAddr;
	
	@Value("${smsc.username}")
	private String username;
	
	@Value("${smsc.password}")
	private String password;
	
	@Value("${smsc.port}")
	private Integer port;
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public SMPPSession getSmppSession() {
		return smppSession;
	}

	public void setSmppSession(SMPPSession smppSession) {
		this.smppSession = smppSession;
	}
	
	@PostConstruct
	public void init(){
		
		
		try {
			this.connect();
		} catch (Exception e) {
			logger.debug("Error init bean to smsc");
		}
		
		logger.debug("Connecting to  Ip [{}] is Construct [{}]",ipAddr,smppSession == null);
	}
	
	@PreDestroy
	public void destroySmsc(){
		try {
			this.disconnect();
		} catch (Exception e) {
			logger.debug("Error unbind bean to smsc");
		}
	}

	public String  connect() throws IOException{
		
		BindParameter bP = new BindParameter(
				BindType.BIND_TRX,
				username,
				password,
                "any",
                TypeOfNumber.INTERNATIONAL,
                NumberingPlanIndicator.UNKNOWN,
                null);    
		
		String systemId = null;
		
		if(smppSession.getSessionState().isBound() == false){
			systemId = smppSession.connectAndBind(ipAddr, port, bP);
			smppSession.setTransactionTimer(60000);
		}
		
		logger.debug("Binding Smsc Success with systemId [{}] ",systemId);
		
		return systemId;
	}
	
	public String sendSms(Sms sms) throws UnsupportedEncodingException, PDUException, ResponseTimeoutException, InvalidResponseException, NegativeResponseException, IOException{
		
		ESMClass esmClass = new ESMClass();
		DataCoding dataCoding = new GeneralDataCoding(false, true, MessageClass.CLASS1, Alphabet.ALPHA_UCS2);
		
		String msgId = smppSession.submitShortMessage (
                "ESME",
                TypeOfNumber.ALPHANUMERIC,
                NumberingPlanIndicator.UNKNOWN,
                sms.getSource(),
                TypeOfNumber.NATIONAL,
                NumberingPlanIndicator.ISDN,
                sms.getTarget(),
                esmClass,
                (byte) 0,
                (byte) 1,
                TIME_FORMATTER.format(new Date()),
                null,
                new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS_FAILURE),
                (byte) 0,
                dataCoding,
                (byte) 0,
                sms.getMessage().getBytes("UTF-16BE"));
		
		sms.setSmscMessageId(msgId);
		
		logger.debug("Submit Message from [{}] to [{}] with [{}] message by Id [[]]", sms.getSource(),sms.getTarget(),sms.getMessage(),sms.getSmscMessageId());
		
		
		return msgId;
		
	}
	
	
	public String disconnect()
    {
        try 
        {
          smppSession.unbindAndClose();
          return("Unbound successfully\n");
                  }
        catch (Exception ex) 
        {
        	logger.error("Unbind Error {}",ex);
            return("Error while unbinding : \n" + ex.toString() + "\n");
        }
        
    }  
	
}
