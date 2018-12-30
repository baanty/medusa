package com.travix.medusa.busyflights.serializers;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class InstantDeserializer extends StdDeserializer<Instant> {

    private static final long serialVersionUID = 1L;

    protected InstantDeserializer() {
        super(Instant.class);
    }


    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
    	String value = jp.getText();
    	System.out.println("value - "+value);
    	LocalDateTime localDateTime = LocalDateTime.parse(value);
    	Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
		return instant;
    }

}