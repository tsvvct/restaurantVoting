package com.github.tsvvct.restaurantvoting.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.tsvvct.restaurantvoting.HasId;

import java.io.IOException;

public class ChildAsIdOnlySerializer extends StdSerializer<HasId> {

    public ChildAsIdOnlySerializer() {
        this(null);
    }

    public ChildAsIdOnlySerializer(Class<HasId> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(HasId value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        gen.writeNumber(value.getId());
    }
}