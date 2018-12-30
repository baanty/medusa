package com.travix.medusa.busyflights.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

public class BusyFlightsServiceTest {

	@InjectMocks
	private BusyFlightsService service;

	@Mock
	RestTemplateService templateService;
	
	@Mock
	Validator validator;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(validator.validate(Matchers.anyObject())).thenReturn(new HashSet<ConstraintViolation<Object>>());
		when(templateService.callRestTemplate(Matchers.anyString(), Matchers.any(HttpEntity.class))).thenReturn(new ArrayList<LinkedHashMap<Object, Object>>());
	}
	
	@Test
	public void contextLoads() {
		
		BusyFlightsRequest request = new BusyFlightsRequest();
		request.setDepartureDate(LocalDate.now().plusMonths(2));
		request.setDestination("CCU");
		request.setNumberOfPassengers(3);
		request.setOrigin("AMS");
		request.setReturnDate(LocalDate.now().plusMonths(3));
		
		Object response = service.getCrazyAirResponse(request); 
		
		assertNotNull("CrazyAir response can not be null", response);
		assertTrue("Response must be a java.util.List ", response instanceof List);
		
		@SuppressWarnings("unchecked")
		List<BusyFlightsResponse> responseList = (List<BusyFlightsResponse>) response;
		assertEquals("There must be no result. ", 0, responseList.size());
		
	}

}
