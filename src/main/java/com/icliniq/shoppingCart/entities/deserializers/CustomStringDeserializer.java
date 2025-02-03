package com.icliniq.shoppingCart.entities.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CustomStringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (!JsonToken.VALUE_STRING.equals(jsonParser.getCurrentToken())) {
            String fieldName = jsonParser.currentName();
            throw new IllegalArgumentException(fieldName + " must be a string.");
        }
        return jsonParser.getText();
    }
}

