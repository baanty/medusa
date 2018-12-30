package com.travix.medusa.busyflights.serializers;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class IsoInstantSerializer extends StdSerializer<Instant> {

    private static final long serialVersionUID = 1L;

    public IsoInstantSerializer(){
        super(Instant.class);
    }

    @Override
    public void serialize(Instant  value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
        gen.writeString(value.toString());
    }
}