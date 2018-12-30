package com.travix.medusa.busyflights.domain.busyflights;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.IsoDateTimeDeserializer;
import com.travix.medusa.busyflights.serializers.IsoDateTimeSerializer;

import lombok.Data;


@Data
public class BusyFlightsResponse {
	private String airline;
	private String supplier;
	private double fare;
	private String departureAirportCode;
	private String destinationAirportCode;
	@JsonDeserialize(using = IsoDateTimeDeserializer.class )
	@JsonSerialize(using = IsoDateTimeSerializer.class)
	private LocalDateTime  departureDate;
	@JsonDeserialize(using = IsoDateTimeDeserializer.class )
	@JsonSerialize(using = IsoDateTimeSerializer.class)
	private LocalDateTime  arrivalDate;
	
}
