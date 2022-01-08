package ru.javaops.topjava.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.javaops.topjava.HasId;

import java.io.IOException;

public class ChildAsIdOnlySerializer extends StdSerializer<HasId> {

    // must have empty constructor
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