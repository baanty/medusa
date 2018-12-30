package com.travix.medusa.busyflights.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

@RestController
public class ToughJetService {
	
	@ResponseBody
	@RequestMapping( value = { "/tough/*" }, consumes = "application/json", produces = "application/json" )
	public List<ToughJetResponse> getToughJetResponse(@RequestBody ToughJetRequest request) {
		
		ToughJetResponse firstResponse = new ToughJetResponse();
		firstResponse.setArrivalAirportName(request.getTo());
		firstResponse.setBasePrice(100);
		firstResponse.setCarrier("RyanAir");
		firstResponse.setDepartureAirportName(request.getFrom());
		firstResponse.setDiscount(4);
		firstResponse.setInboundDateTime(LocalDateTime.of(request.getInboundDate(), LocalTime.now()).toInstant(ZoneOffset.UTC));
		firstResponse.setOutboundDateTime(LocalDateTime.of(request.getOutboundDate(), LocalTime.now()).toInstant(ZoneOffset.UTC));
		firstResponse.setTax(2);
		
		
		ToughJetResponse secondResponse = new ToughJetResponse();
		secondResponse.setArrivalAirportName(request.getTo());
		secondResponse.setBasePrice(1000);
		secondResponse.setCarrier("KLM");
		secondResponse.setDepartureAirportName(request.getFrom());
		secondResponse.setDiscount(4);
		secondResponse.setInboundDateTime(LocalDateTime.of(request.getInboundDate(), LocalTime.now()).toInstant(ZoneOffset.UTC));
		secondResponse.setOutboundDateTime(LocalDateTime.of(request.getOutboundDate(), LocalTime.now()).toInstant(ZoneOffset.UTC));
		secondResponse.setTax(2);
		
		List<ToughJetResponse> ret = new ArrayList<ToughJetResponse>();
		ret.add(firstResponse);
		ret.add(secondResponse);
		
		return ret;

	}

}
