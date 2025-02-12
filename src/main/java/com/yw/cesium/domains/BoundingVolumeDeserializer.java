package com.yw.cesium.domains;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class BoundingVolumeDeserializer extends StdDeserializer<IBoundingVolume> {

    protected BoundingVolumeDeserializer() {
        super(IBoundingVolume.class);
    }

    @Override
    public IBoundingVolume deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = parser.getCodec().readTree(parser);

        if (node.has("box")) {
            BoundingVolumeBox box = new BoundingVolumeBox();
            box.setBox(parser.getCodec().treeToValue(node.get("box"), double[].class));
            return box;
        }

        if (node.has("region")) {
            BoundingVolumeRegion region = new BoundingVolumeRegion();
            region.setRegion(parser.getCodec().treeToValue(node.get("region"), double[].class));
            return region;
        }

        if (node.has("sphere")) {
            BoundingVolumeSphere sphere = new BoundingVolumeSphere();
            sphere.setSphere(parser.getCodec().treeToValue(node.get("sphere"), double[].class));
            return sphere;
        }

        return null;
    }
}
