package cat.nbtc.step.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "cat.nbtc.step.sms.controller")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	
	@Bean
	public InternalResourceViewResolver viewResolver() {
	InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	resolver.setPrefix("/WEB-INF/view/");
	resolver.setSuffix(".jsp");
	return resolver;
	}
	 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}
}
