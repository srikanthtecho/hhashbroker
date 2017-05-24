/**
 * 
 */
package com.hashmap.service.broker.client.config;

import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.hashmap.service.broker.client.connector.HashServiceInfo;


@Configuration
@EnableAutoConfiguration
public class ServiceBrokerClientAutoConfig {

	@Bean
	public Cloud createCloud() {
		return new CloudFactory().getCloud();
	}

	@Bean
	public HashServiceInfo haashServiceInfo() {
		final List<ServiceInfo> serviceInfos = createCloud().getServiceInfos();
		for (ServiceInfo serviceInfo : serviceInfos) {
			if (serviceInfo instanceof HashServiceInfo) {
				return (HashServiceInfo) serviceInfo;
			}
		}
		throw new RuntimeException("Unable to find bound HashBroker instance!");
	}

	@Bean
	RestTemplate restTemplate() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(haashServiceInfo().getUsername(), haashServiceInfo().getPassword()));
		httpClient.setCredentialsProvider(credentialsProvider);
		ClientHttpRequestFactory reqFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(reqFactory);
	}
}
