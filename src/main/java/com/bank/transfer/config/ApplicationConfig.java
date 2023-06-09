package com.bank.transfer.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Locale;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Data
@Configuration
@EnableScheduling
@EnableAsync
@EnableCaching
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@ConfigurationProperties("app")
public class ApplicationConfig {

	private String clientExchange;
	private String clientQueue;

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		.setMatchingStrategy(MatchingStrategies.STRICT)
		.setFieldMatchingEnabled(true)
		.setSkipNullEnabled(true)
		.setFieldAccessLevel(PRIVATE);
		return mapper;
	}

	@Bean
	public Locale locale() {
		return new Locale("ru");
	}

}