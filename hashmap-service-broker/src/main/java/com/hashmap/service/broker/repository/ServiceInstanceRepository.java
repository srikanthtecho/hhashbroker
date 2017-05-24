package com.hashmap.service.broker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hashmap.service.broker.model.ServiceInstance;

@Repository
public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, String> {
}
