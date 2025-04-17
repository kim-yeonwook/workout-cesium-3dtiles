package com.yw.cesium.domains;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class Tiles3D implements ICTiles {

    private Map<String, Object> asset;

    private Map<String, Object> schema;

    private double geometricError;

    private Root root;

    @Override
    public IBoundingVolume.BoundingVolumeType getRootBoundingVolumeType() {
        return getRoot().getBoundingVolumeType();
    }

    @Override
    public List<Point> getTilesetPointList() {
        List<Point> pointList = new ArrayList<>();
        getRoot().lookupPoint(pointList);

        return pointList;
    }
}
