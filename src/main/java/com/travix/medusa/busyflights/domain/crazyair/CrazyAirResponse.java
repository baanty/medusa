package com.travix.medusa.busyflights.domain.crazyair;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.IsoLocalDateSerializer;
import com.travix.medusa.busyflights.serializers.LocalDateDeserializer;

import lombok.Data;

@Data
public class CrazyAirResponse {

    private String airline;
    private double price;
    private String cabinclass;
    private String departureAirportCode;
    private String destinationAirportCode;
    @JsonDeserialize( using = LocalDateDeserializer.class )
    @JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate departureDate;
    @JsonDeserialize( using = LocalDateDeserializer.class )
    @JsonSerialize(using = IsoLocalDateSerializer.class)
    private LocalDate arrivalDate;
}
