package com.travix.medusa.busyflights.domain.toughjet;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.IsoLocalDateSerializer;
import com.travix.medusa.busyflights.validator.AirportCodeValidator;

import lombok.Data;

@Data
public class ToughJetRequest {

	@AirportCodeValidator
    private String from;
    
	@AirportCodeValidator
	private String to;
    
	@JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate outboundDate;
    
	@JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate inboundDate;
    
	private int numberOfAdults;

    
}
