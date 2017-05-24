package com.hashmap.service.broker.client.connector;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;


public class HashServiceInfoCreator extends CloudFoundryServiceInfoCreator<HashServiceInfo> {
	
    public HashServiceInfoCreator() {
        super(new Tags("HashBroker"));
    }

    @Override
    public HashServiceInfo createServiceInfo(Map<String, Object> serviceData) {
        final Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");

        final String id = (String) serviceData.get("name");
        final String uri = (String) credentials.get("uri");
        final String username = (String) credentials.get("username");
        final String password = (String) credentials.get("password");

        return new HashServiceInfo(id, uri, username, password);
    }

    @Override
    public boolean accept(Map<String, Object> serviceData) {
    	final Map<String, Object> credentials = (Map<String, Object>) serviceData.get("credentials");
    	final String uri = (String) credentials.get("uri");
    	final String username = (String) credentials.get("username");
    	final String password = (String) credentials.get("password");
        return username != null &&
                password != null &&
                uri != null;
    }
}
