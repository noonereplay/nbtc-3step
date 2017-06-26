package cat.nbtc.step.sms.listen;



import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cat.nbtc.step.sms.controller.ApiController;
import cat.nbtc.step.sms.dao.SmsDao;
import cat.nbtc.step.sms.dto.Sms;
import cat.nbtc.step.sms.service.SmscService;

public class SmsQueueListener implements MessageListener, BeanNameAware {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	private String beanName;
	
	@Autowired
	@Qualifier("jsonMessageConverter")
	private Jackson2JsonMessageConverter jsonConvertor;
	
	@Resource(name="smscService")
	private SmscService smscService;
	
	@Autowired
	private SmsDao smsDao;

	@Override
	public void setBeanName(String beanName) {
		// TODO Auto-generated method stub
		this.beanName = beanName;
	}
	

	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		
				Sms sms = (Sms)jsonConvertor.fromMessage(msg);

				
				logger.info("{} onMessage {} ", beanName, new String(msg.getBody()) );
				
				
				logger.info("Sending Message to {}, {} ",sms.getTarget(),sms.getMessageId());
				try {
					 String smscMsgId = smscService.sendSms(sms);
					 sms.setSmscStatus("0");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error UnsupportedEncodingException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("10");
					sms.setException(e);
				} catch (PDUException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error PDUException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("11");
					sms.setException(e);
				} catch (ResponseTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error ResponseTimeoutException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("12");
					sms.setException(e);
				} catch (InvalidResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error InvalidResponseException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("13");
					sms.setException(e);
				} catch (NegativeResponseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error NegativeResponseException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("14");
					sms.setException(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("Error IOException target [{}]",sms.getTarget(),e);
					sms.setSmscStatus("15");
					sms.setException(e);
				}
				
				logger.info("updating Message to {}, {} ",sms.getTarget(),sms.getMessageId());
				
				try{
					//Use Thread to accomplish
					/*Sms existing = smsDao.getSmsById(sms.getMessageId());
					if(existing != null){
						smsDao.update(sms);
					}*/
					smsDao.save(sms);
					
				}catch(Exception e){
					logger.error("Error updateTransation SMSC DB : {} ",e);
				}
				
				//System.out.println("result : "+sms.toString());
				if(!sms.getSmscStatus().equals("0")){
					logger.error("Error Result [{}]  ",sms);
						throw new ListenerExecutionFailedException("Error occur while sending message to SMSC",sms.getException());
				}
		
	}

}
