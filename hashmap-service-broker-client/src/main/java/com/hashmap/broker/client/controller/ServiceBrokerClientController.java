/**
 * 
 */
package com.hashmap.broker.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hashmap.service.broker.client.connector.HashServiceInfo;

@RestController
public class ServiceBrokerClientController {
	@Autowired
	private HashServiceInfo hashServiceInfo;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/HashBroker/info")
	public HashServiceInfo info() {
		return hashServiceInfo;
	}

	@RequestMapping(value = "/HashBroker/{key}", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@PathVariable("key") String key, @RequestBody String value) {
		restTemplate.put(hashServiceInfo.getUri() + "/{key}", value, key);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/HashBroker/{key}", method = RequestMethod.GET)
	public ResponseEntity<String> put(@PathVariable("key") String key) {
		String response = restTemplate.getForObject(hashServiceInfo.getUri() + "/{key}", String.class, key);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
