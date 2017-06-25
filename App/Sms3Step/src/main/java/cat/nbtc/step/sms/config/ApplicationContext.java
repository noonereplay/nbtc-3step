package cat.nbtc.step.sms.config;


import org.jsmpp.session.SMPPSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import cat.nbtc.step.sms.service.SmscService;


@Configuration
@PropertySource("classpath:config.${spring.profiles.active}.properties")
@EnableWebMvc
public class ApplicationContext {
	
	@Bean
	public SMPPSession smscSession(){
		return new SMPPSession();
	}

	@Bean
	public SmscService smscService(){
		SmscService service = new SmscService();
		return service;
	}
	
	
}
