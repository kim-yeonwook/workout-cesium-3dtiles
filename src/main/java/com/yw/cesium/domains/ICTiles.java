package com.yw.cesium.domains;

import java.util.List;

public interface ICTiles {

    List<Point> getTilesetPointList();

    IBoundingVolume.BoundingVolumeType getRootBoundingVolumeType();

}
