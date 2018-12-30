/**
 * 
 */
package com.travix.medusa.busyflights.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/**
 * @author Pijush
 *
 */
public final class Assembler {

	public static final CrazyAirRequest buildCrazyAirRequestFromBusyFlights(BusyFlightsRequest bRequest) {
		CrazyAirRequest cRequest = new CrazyAirRequest();
		cRequest.setDepartureDate(bRequest.getDepartureDate());
		cRequest.setDestination(bRequest.getDestination());
		cRequest.setOrigin(bRequest.getOrigin());
		cRequest.setPassengerCount(bRequest.getNumberOfPassengers());
		cRequest.setReturnDate(bRequest.getReturnDate());
		return cRequest;
	}
	
	public static final ToughJetRequest buildToughJetRequestFromBusyFlights(BusyFlightsRequest bRequest) {
		ToughJetRequest tRequest = new ToughJetRequest();
		tRequest.setFrom(bRequest.getOrigin());
		tRequest.setInboundDate(bRequest.getReturnDate());
		tRequest.setNumberOfAdults(bRequest.getNumberOfPassengers());
		tRequest.setOutboundDate(bRequest.getDepartureDate());
		tRequest.setTo(bRequest.getDestination());
		
		return tRequest;
	}
	
	
	public static final BusyFlightsResponse buildBusyFlightsResponseFromCrazyAirResponse(CrazyAirResponse cResponse) {
		
		BusyFlightsResponse response = new BusyFlightsResponse();
		response.setAirline(cResponse.getAirline());
		response.setArrivalDate(cResponse.getArrivalDate().atStartOfDay());
		response.setDepartureAirportCode(cResponse.getDepartureAirportCode());
		response.setDepartureDate(cResponse.getDepartureDate().atStartOfDay());
		response.setDestinationAirportCode(cResponse.getDestinationAirportCode());
		response.setFare(cResponse.getPrice());
		response.setSupplier("CrazyAir");
		return response;
	}

	public static final BusyFlightsResponse buildBusyFlightsResponseFromToughJetResponse(ToughJetResponse tResponse) {
		
		BusyFlightsResponse response = new BusyFlightsResponse();
		response.setAirline(tResponse.getCarrier());
		response.setArrivalDate(LocalDateTime.ofInstant(tResponse.getInboundDateTime() == null ? Instant.now() : tResponse.getInboundDateTime(), ZoneOffset.UTC));
		response.setDepartureAirportCode(tResponse.getDepartureAirportName());
		response.setDepartureDate(LocalDateTime.ofInstant(tResponse.getOutboundDateTime() == null ? Instant.now() : tResponse.getOutboundDateTime(), ZoneOffset.UTC));
		response.setDestinationAirportCode(tResponse.getArrivalAirportName());
		response.setFare(tResponse.getBasePrice() + tResponse.getTax() - tResponse.getDiscount());
		response.setSupplier("ToughJet");
		return response;
	}
}
