package cat.nbtc.step.sms.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.nbtc.step.sms.dto.Sms;
import cat.nbtc.step.sms.dto.SmsRequest;
import cat.nbtc.step.sms.dto.SmsResponse;
import cat.nbtc.step.sms.service.SmscService;

@RestController
@RequestMapping("/rest/api")
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private SmscService smscService;

	public void setSmscService(SmscService smscService) {
		this.smscService = smscService;
	}
	
	@PostMapping("/sendSms")
	public SmsResponse sendSms(@RequestBody SmsRequest smsRequest){
		
		logger.debug("Incoming Message [{}]",smsRequest);
		
		SmsResponse response = new SmsResponse();
		response.setResponseCode("0");
		response.setDescription("Success");
		
		Sms sms = new Sms();
		sms.setSource(smsRequest.getSource());
		sms.setTarget(smsRequest.getTarget());
		sms.setMessage(smsRequest.getMessage());
		
		
		try {
			smscService.sendSms(sms);
			
			logger.debug("Delivery Message Id [{}]",sms.getSmscMessageId());
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error UnsupportedEncodingException target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("10");
			response.setDescription("UnsupportedEncodingException");
		} catch (PDUException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error PDUException target [{}]",smsRequest.getTarget(),e);
			
			response.setResponseCode("11");
			response.setDescription("PDUException");
		} catch (ResponseTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error ResponseTimeoutException target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("12");
			response.setDescription("ResponseTimeoutException");
			
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error InvalidResponseException target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("13");
			response.setDescription("InvalidResponseException");
		} catch (NegativeResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error NegativeResponseException target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("14");
			response.setDescription("NegativeResponseException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error IOException target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("15");
			response.setDescription("IOException");
		}
		
		logger.debug("Response correlation [{}] : [{}]",sms.getSmscMessageId(),response);
		
		return response;
	}
	
}
