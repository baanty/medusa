package com.travix.medusa.busyflights.domain.busyflights;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.IsoLocalDateSerializer;
import com.travix.medusa.busyflights.serializers.LocalDateDeserializer;
import com.travix.medusa.busyflights.validator.AirportCodeValidator;

import lombok.Data;


@Data /* Removed all the boilerplate getter/setters with lombok */
public class BusyFlightsRequest {

	@AirportCodeValidator
    private String origin;
	
	@AirportCodeValidator
    private String destination;
    
	
	@JsonDeserialize(using = LocalDateDeserializer.class)  
    @JsonSerialize(using = IsoLocalDateSerializer.class) 
    private LocalDate departureDate; /* Changed input date type to LocalDate as this is normal beyond Java 8.0 */
    
	@JsonDeserialize(using = LocalDateDeserializer.class)  
    @JsonSerialize(using = IsoLocalDateSerializer.class) 
    private LocalDate returnDate;
    
    @Min(value = 1, message = "Must allow at leat one passanger")
    @Max(value = 4, message = "Can not allow more than four passangers.")
	private int numberOfPassengers;
}
