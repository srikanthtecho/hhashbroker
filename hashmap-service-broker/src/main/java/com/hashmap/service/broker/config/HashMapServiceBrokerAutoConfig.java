/**
 * 
 */
package com.hashmap.service.broker.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
public class HashMapServiceBrokerAutoConfig {

	@Bean
	public Cloud createCloud() {
		return new CloudFactory().getCloud();
	}

}
