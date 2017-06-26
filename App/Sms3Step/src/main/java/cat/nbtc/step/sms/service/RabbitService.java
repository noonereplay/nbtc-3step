package cat.nbtc.step.sms.service;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cat.nbtc.step.sms.dto.Sms;

@Service
public class RabbitService {
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(RabbitService.class);
	
	@Autowired
	@Qualifier("smsTemplate")
	private RabbitTemplate smsTemplate;

	public void setSmsTemplate(RabbitTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public void publishSms(Sms sms) throws Exception {

		logger.info("Start Publishing {}",sms);
		smsTemplate.convertAndSend(sms);

	}
	
	
}
