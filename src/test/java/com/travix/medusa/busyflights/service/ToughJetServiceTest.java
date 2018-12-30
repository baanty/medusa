package com.travix.medusa.busyflights.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToughJetServiceTest {

	@Autowired
	private ToughJetService service;
	
	@Test
	public void contextLoads() {
		List<ToughJetResponse> response = service.getToughJetResponse(new ToughJetRequest()); 
		
		assertNotNull("ToughJet response can not be null", response);
		assertEquals("Toughjet rresponse must have exactly two results", 2, response.size());
		
	}

}
