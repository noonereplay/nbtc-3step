package cat.nbtc.step.sms.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.nbtc.step.sms.dto.Sms;
import cat.nbtc.step.sms.dto.SmsRequest;
import cat.nbtc.step.sms.dto.SmsResponse;
import cat.nbtc.step.sms.service.RabbitService;
import cat.nbtc.step.sms.service.SmscService;

@RestController
@RequestMapping("/rest/api")
public class ApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private SmscService smscService;
	
	@Autowired
	private RabbitService rabbitService;

	public void setSmscService(SmscService smscService) {
		this.smscService = smscService;
	}
	
	public void setRabbitService(RabbitService rabbitService) {
		this.rabbitService = rabbitService;
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
		sms.setCorrelationId(smsRequest.getCorrelationId());
		sms.setSubmitedBy(smsRequest.getSubmitedBy());
		
		try {
			//smscService.sendSms(sms);
			rabbitService.publishSms(sms);
			logger.debug("Delivery Message Id [{}]",sms.getSmscMessageId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error Exception target [{}]",smsRequest.getTarget(),e);
			response.setResponseCode("20");
			response.setDescription("Internal Server Error");
		}
		
		logger.debug("Response correlation [{}] : [{}]",sms.getSmscMessageId(),response);
		
		return response;
	}
	
	@GetMapping("/smsDisconnect")
	public SmsResponse disconnect(){
		
		SmsResponse response = new SmsResponse();
		response.setResponseCode("0");
		response.setDescription("Success");
		
		try {
			smscService.disconnect();
		} catch (Exception e) {
			logger.error("Error Disconnect API",e);
			response.setResponseCode("20");
			response.setDescription(e.getMessage());
		}
		
		
		return response;
	}
	
}
