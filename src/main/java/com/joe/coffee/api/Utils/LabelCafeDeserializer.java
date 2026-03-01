package com.joe.coffee.api.Utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.joe.coffee.api.Enum.LabelCafe;

import java.io.IOException;

public class LabelCafeDeserializer extends JsonDeserializer<LabelCafe> {

    @Override
    public LabelCafe deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        if (text == null || text.isBlank()) {
            return null; // "" ou null → null
        }
        return LabelCafe.valueOf(text); // sinon, transforme en enum
    }
}