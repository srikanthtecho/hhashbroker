/**
 * 
 */
package com.hashmap.service.broker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hashmap.service.broker.service.HashMapBrokerService;


public class HashMapServiceBrokerController {

	@Autowired
	private HashMapBrokerService mapBrokerService;

	/**
	 * 
	 * @param instanceId
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/hash/{instanceId}/{key}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable("instanceId") String instanceId, @PathVariable("key") String key) {
		final Object response = mapBrokerService.get(instanceId, key);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("{}", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * 
	 * @param instanceId
	 * @param key
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/hash/{in	stanceId}/{key}", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@PathVariable("instanceId") String instanceId, @PathVariable("key") String key,
			@RequestBody String value) {
		mapBrokerService.put(instanceId, key, value);
		return new ResponseEntity<>("{}", HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param instanceId
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/hash/{instanceId}/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("instanceId") String instanceId,
			@PathVariable("key") String key) {
		final Object response = mapBrokerService.get(instanceId, key);
		if (response != null) {
			mapBrokerService.delete(instanceId, key);
			return new ResponseEntity<>("{}", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("{}", HttpStatus.GONE);
		}
	}

}
