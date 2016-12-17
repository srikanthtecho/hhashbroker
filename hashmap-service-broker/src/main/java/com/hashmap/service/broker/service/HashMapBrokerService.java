/**
 * 
 */
package com.hashmap.service.broker.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hashmap.service.broker.util.CustomHashMap;

/**
 * @author shyamjumberu
 *
 */
public class HashMapBrokerService {


	@Autowired
	private CustomHashMap<String, CustomHashMap<Object, Object>> map;

	/**
	 * @return the map
	 */
	public CustomHashMap<String, CustomHashMap<Object, Object>> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(CustomHashMap<String, CustomHashMap<Object, Object>> map) {
		this.map = map;
	}

	/**
	 * 
	 * @param id
	 * @param key
	 * @param value
	 */
	public void put(final String id, final Object key, final Object value) {
		final CustomHashMap<Object, Object> customMap = map.get(id);
		customMap.put(key, value);
	}

	/**
	 * 
	 * @param id
	 * @param key
	 * @return
	 */
	public Object get(final String id, final Object key) {
		final CustomHashMap<Object, Object> customMap = map.get(id);
		return customMap.get(key);
	}

	/**
	 * 
	 * @param id
	 * @param key
	 */
	public void delete(final String id, final Object key) {
		final CustomHashMap<Object, Object> customMap = map.get(id);
		customMap.remove(key);
	}

	/**
	 * 
	 * @param id
	 */
	public void delete(final String id) {
		map.remove(id);
	}

	/**
	 * 
	 * @param id
	 */
	public void create(final String id) {
		map.put(id, new CustomHashMap<Object, Object>());
	}
}
