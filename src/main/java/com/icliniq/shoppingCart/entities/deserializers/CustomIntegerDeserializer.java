package com.icliniq.shoppingCart.entities.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class CustomIntegerDeserializer extends JsonDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!JsonToken.VALUE_NUMBER_INT.equals(jsonParser.getCurrentToken())) {
            String fieldName = jsonParser.currentName();
            throw new IllegalArgumentException(fieldName + " must be an integer");
        }
        return jsonParser.getIntValue();
    }
}

