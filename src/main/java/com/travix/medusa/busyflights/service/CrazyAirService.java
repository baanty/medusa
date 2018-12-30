package com.travix.medusa.busyflights.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

@RestController
public class CrazyAirService {
	
	@ResponseBody
	@RequestMapping( value = { "/crazy/*" }, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE )
	public List<CrazyAirResponse> getCrazyAirResponse(@RequestBody CrazyAirRequest request) {
		
		CrazyAirResponse firstResponse = new CrazyAirResponse();
		firstResponse.setAirline("Transavia");
		firstResponse.setArrivalDate(request.getReturnDate());
		firstResponse.setCabinclass("ECONOMY");
		firstResponse.setDepartureAirportCode(request.getOrigin());
		firstResponse.setDepartureDate(request.getDepartureDate());
		firstResponse.setDestinationAirportCode(request.getDestination());
		firstResponse.setPrice(500);
		
		CrazyAirResponse secondResponse = new CrazyAirResponse();
		secondResponse.setAirline("Lufthansa");
		secondResponse.setArrivalDate(request.getReturnDate());
		secondResponse.setCabinclass("ECONOMY");
		secondResponse.setDepartureAirportCode(request.getOrigin());
		secondResponse.setDepartureDate(request.getDepartureDate());
		secondResponse.setDestinationAirportCode(request.getDestination());
		secondResponse.setPrice(600);
		
		List<CrazyAirResponse> response = new ArrayList<CrazyAirResponse>();
		response.add(firstResponse);
		response.add(secondResponse);
		
		return response;
	}

}
