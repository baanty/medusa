package com.travix.medusa.busyflights.domain.crazyair;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.IsoLocalDateSerializer;
import com.travix.medusa.busyflights.validator.AirportCodeValidator;

import lombok.Data;

@Data
public class CrazyAirRequest {

	@AirportCodeValidator
    private String origin;
	
	@AirportCodeValidator
    private String destination;
    
	@JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate departureDate;
    
	@JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate returnDate;
    
	private int passengerCount;
}
