package com.hashmap.service.broker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hashmap.service.broker.model.ServiceBinding;

@Repository
public interface ServiceBindingRepository extends CrudRepository<ServiceBinding,String> {
}
