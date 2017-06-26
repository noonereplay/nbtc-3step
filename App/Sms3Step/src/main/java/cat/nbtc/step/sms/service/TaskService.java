package cat.nbtc.step.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private SmscService smscService;
	
	@Scheduled(cron="0 0 23 * * *")
	public void reconnectSmsc(){
		
		logger.debug("Starting Reconnect SMSC");
		try {
			smscService.disconnect();
			smscService.connect();
		} catch (Exception e) {
			logger.error("Reconnect Smsc Task Fail",e);
		}
	}
}
