package com.scheduling.app;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class JobSchedulerConfiguration {

	@Bean
	@QuartzDataSource
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource quartzDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(120000);
		requestFactory.setReadTimeout(60000);
		return new RestTemplate(requestFactory);
	}
}
