package com.yw.cesium.domains;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TransformDeserializer extends JsonDeserializer<Transform> {

    @Override
    public Transform deserialize(JsonParser p, DeserializationContext context) throws IOException {
        double[] values = p.readValueAs(double[].class);
        return new Transform(values);
    }
}
