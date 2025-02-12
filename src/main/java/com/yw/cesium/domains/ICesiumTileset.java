package com.yw.cesium.domains;

import java.util.List;

public interface ICesiumTileset {

    List<Point> getTilesetPointList();

    IBoundingVolume.BoundingVolumeType getRootBoundingVolumeType();

}
