package com.yw.cesium.domains;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.geotools.filter.function.StaticGeometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Tileset3DHandler {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private int MAX_TILESET_DEPTH = 3;

    private ICesiumTileset tileset;

    public Tileset3DHandler(String tilesetJson) throws JsonProcessingException {
        init(tilesetJson);
    }

    public Tileset3DHandler(String tilesetJson, int maxDepth) throws JsonProcessingException {
        init(tilesetJson);
        this.MAX_TILESET_DEPTH = maxDepth;
    }

    private void init(String tilesetJson) throws JsonProcessingException {
        JsonNode node = limitDepth(mapper.readTree(tilesetJson), 0);
        this.tileset = mapper.readValue(mapper.writeValueAsString(node), Tileset3D.class);
    }

    public String getTilesetType() {
        return tileset.getRootBoundingVolumeType().name();
    }

    public Geometry getPolygonConvexHull(int crs) {

        List<Point> pointList = this.tileset.getTilesetPointList();

        int size = pointList.size();
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] coords = new Coordinate[size + 1];
        for (int i = 0; i < size; i++) {
            Point point = pointList.get(i);
            coords[i] = new Coordinate(point.getX(), point.getY());
        }
        coords[size] = coords[0];
        Geometry geometry = StaticGeometry.convexHull(factory.createPolygon(coords));
        geometry.setSRID(crs);

        return geometry;
    }

    public List<Point> getPointList() {
        return this.tileset.getTilesetPointList();
    }

    private JsonNode limitDepth(JsonNode node, int currentDepth) {
        if (currentDepth > MAX_TILESET_DEPTH) {
            return null;
        }

        if (node.isValueNode()) {
            return node;
        } else if (node.isObject()) {
            ObjectNode limitedNode = new ObjectMapper().createObjectNode();
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if ("children".equals(field.getKey())) {
                    currentDepth++;
                }

                JsonNode limitedChild = limitDepth(field.getValue(), currentDepth);
                if (limitedChild != null) {
                    limitedNode.set(field.getKey(), limitedChild);
                }
            }
            return limitedNode;
        } else if (node.isArray()) {
            ArrayNode arrayNode = new ObjectMapper().createArrayNode();
            int size = node.size();

            for (int index = 0; index < size; index++ ) {
                JsonNode n = node.get(index);
                arrayNode.add(limitDepth(n, currentDepth));
            }
            return arrayNode;
        } else {
            return node;
        }
    }
}
