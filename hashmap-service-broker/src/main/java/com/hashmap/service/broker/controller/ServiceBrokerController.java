package com.hashmap.service.broker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hashmap.service.broker.model.Credentials;
import com.hashmap.service.broker.model.Service;
import com.hashmap.service.broker.model.ServiceBinding;
import com.hashmap.service.broker.model.ServiceInstance;
import com.hashmap.service.broker.repository.ServiceBindingRepository;
import com.hashmap.service.broker.repository.ServiceInstanceRepository;
import com.hashmap.service.broker.repository.ServiceRepository;
import com.hashmap.service.broker.service.HashMapBrokerService;

@RestController
public class ServiceBrokerController {


    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceInstanceRepository serviceInstanceRepository;

    @Autowired
    private ServiceBindingRepository serviceBindingRepository;

    @Autowired
    private HashMapBrokerService hashMapBrokerService;

    @Autowired
    private Cloud cloud;

    @RequestMapping("/v2/catalog")
    public Map<String, Iterable<Service>> catalog() {
        Map<String, Iterable<Service>> hashMap = new HashMap<>();
        hashMap.put("services", serviceRepository.findAll());
        return hashMap;
    }

    @RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> create(@PathVariable("id") String id, @RequestBody ServiceInstance serviceInstance) {
        serviceInstance.setId(id);

        boolean exists = serviceInstanceRepository.exists(id);

        if (exists) {
            ServiceInstance existing = serviceInstanceRepository.findOne(id);
            if (existing.equals(serviceInstance)) {
                return new ResponseEntity<>("{}", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("{}", HttpStatus.CONFLICT);
            }
        } else {
            serviceInstanceRepository.save(serviceInstance);
            hashMapBrokerService.create(id);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/v2/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> createBinding(@PathVariable("instanceId") String instanceId,
                                                @PathVariable("id") String id,
                                                @RequestBody ServiceBinding serviceBinding) {
        if (!serviceInstanceRepository.exists(instanceId)) {
            return new ResponseEntity<Object>("{\"description\":\"Service instance " + instanceId + " does not exist!\"", HttpStatus.BAD_REQUEST);
        }

        serviceBinding.setId(id);
        serviceBinding.setInstanceId(instanceId);

        boolean exists = serviceBindingRepository.exists(id);

        if (exists) {
            ServiceBinding existing = serviceBindingRepository.findOne(id);
            if (existing.equals(serviceBinding)) {
                return new ResponseEntity<Object>(wrapCredentials(existing.getCredentials()), HttpStatus.OK);
            } else {
                return new ResponseEntity<Object>("{}", HttpStatus.CONFLICT);
            }
        } else {
            Credentials credentials = new Credentials();
            credentials.setId(UUID.randomUUID().toString());
            credentials.setUri("http://" + myUri() + "/hash/" + instanceId);
            credentials.setUsername("username");
            credentials.setPassword("password");
            serviceBinding.setCredentials(credentials);
            serviceBindingRepository.save(serviceBinding);
            return new ResponseEntity<Object>(wrapCredentials(credentials), HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/v2/service_instances/{instanceId}/service_bindings/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBinding(@PathVariable("instanceId") String instanceId,
                                                @PathVariable("id") String id,
                                                @RequestParam("service_id") String serviceId,
                                                @RequestParam("plan_id") String planId) {
        boolean exists = serviceBindingRepository.exists(id);

        if (exists) {
            serviceBindingRepository.delete(id);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{}", HttpStatus.GONE);
        }
    }

    @RequestMapping(value = "/v2/service_instances/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("id") String id,
                                         @RequestParam("service_id") String serviceId,
                                         @RequestParam("plan_id") String planId) {
        boolean exists = serviceRepository.exists(id);

        if (exists) {
            serviceRepository.delete(id);
            hashMapBrokerService.delete(id);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{}", HttpStatus.GONE);
        }
    }

    @SuppressWarnings("unchecked")
	private String myUri() {
        ApplicationInstanceInfo applicationInstanceInfo = cloud.getApplicationInstanceInfo();
        List<Object> uris = (List<Object>) applicationInstanceInfo.getProperties().get("uris");
        return uris.get(0).toString();
    }

    private Map<String, Object> wrapCredentials(Credentials credentials) {
        Map<String, Object> wrapper = new HashMap<>();
        wrapper.put("credentials", credentials);
        return wrapper;
    }
}
