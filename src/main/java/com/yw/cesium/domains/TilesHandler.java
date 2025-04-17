package com.yw.cesium.domains;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yw.cesium.infrastructure.exception.InternalServerException;
import org.geotools.filter.function.StaticGeometry;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TilesHandler {

    private final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private int TILES_MAX_DEPTH = 4;

    private ICTiles tiles;

    public TilesHandler(String tilesJson) {
        init(tilesJson);
    }

    public TilesHandler(String tilesJson, int maxDepth) {
        init(tilesJson);
        this.TILES_MAX_DEPTH = maxDepth;
    }

    private void init(String tilesJson) {
        try {
            JsonNode node = limitDepth(mapper.readTree(tilesJson), 0);
            this.tiles = mapper.convertValue(node, Tiles3D.class);

        } catch (JsonProcessingException jpe) {
            throw new InternalServerException("Tiles Convert Object Fail. ", jpe);
        }
    }

    public String getTilesType() {
        return tiles.getRootBoundingVolumeType().name();
    }

    public Geometry getPolygonConvexHull(int crs) {
        List<Point> pointList = this.tiles.getTilesetPointList();

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
        return this.tiles.getTilesetPointList();
    }

    private JsonNode limitDepth(JsonNode node, int currentDepth) {
        if (currentDepth > TILES_MAX_DEPTH) {
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
