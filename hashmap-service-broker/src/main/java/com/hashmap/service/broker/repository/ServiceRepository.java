package com.hashmap.service.broker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hashmap.service.broker.model.Service;

/**
 * 
 * @author Shyamjumberu
 *
 */
@Repository
public interface ServiceRepository extends CrudRepository<Service, String> {
}
