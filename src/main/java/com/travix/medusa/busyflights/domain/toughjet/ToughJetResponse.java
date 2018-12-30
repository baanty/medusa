package com.travix.medusa.busyflights.domain.toughjet;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.travix.medusa.busyflights.serializers.InstantDeserializer;
import com.travix.medusa.busyflights.serializers.IsoInstantSerializer;

import lombok.Data;


@Data
public class ToughJetResponse {

    private String carrier;
    private double basePrice;
    private double tax;
    private double discount;
    private String departureAirportName;
    private String arrivalAirportName;
    @JsonDeserialize( using = InstantDeserializer.class)
    @JsonSerialize(using = IsoInstantSerializer.class)
    private Instant outboundDateTime;
    @JsonDeserialize( using = InstantDeserializer.class)
    @JsonSerialize(using = IsoInstantSerializer.class)
    private Instant inboundDateTime;
}
