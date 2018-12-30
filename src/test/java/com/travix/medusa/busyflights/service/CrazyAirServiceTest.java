package com.travix.medusa.busyflights.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrazyAirServiceTest {

	@Autowired
	private CrazyAirService service;
	
	@Test
	public void contextLoads() {
		List<CrazyAirResponse> response = service.getCrazyAirResponse(new CrazyAirRequest()); 
		
		assertNotNull("CrazyAir response can not be null", response);
		assertEquals("CrazyAir rresponse must have exactly two results", 2, response.size());
		
	}

}
