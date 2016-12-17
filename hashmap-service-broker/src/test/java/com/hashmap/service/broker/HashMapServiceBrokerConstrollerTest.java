/**
 * 
 */
package com.hashmap.service.broker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.hashmap.service.broker.controller.HashMapServiceBrokerController;

/**
 * @author Shyamjumberu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HashMapServiceBrokerConstrollerTest {
	
	private static final String INSTANCE_ID = "instanceId";
	private static final String KEY = "key";
	private static final String VALUE = "value";
	private static final String URI = "/hash/"+INSTANCE_ID+"/"+KEY;
	
	private MockMvc mockMvc;
	
	@Autowired
	private HashMapServiceBrokerController service;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}
	
	@Test
	public void testPut() {
		try {
			final MvcResult mvcResult = mockMvc.perform(put(URI)).andReturn();
			final String response = mvcResult.getResponse().getContentAsString();
			Assert.assertEquals("{}", response);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testGet() {
		try {
			final MvcResult mvcResult = mockMvc.perform(get(URI)).andReturn();
			final String response = mvcResult.getResponse().getContentAsString();
			Assert.assertEquals(VALUE, response);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	@Test
	public void testDelete() {
		try {
			final MvcResult mvcResult = mockMvc.perform(delete(URI)).andReturn();
			final String response = mvcResult.getResponse().getContentAsString();
			Assert.assertEquals("{}", response);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
