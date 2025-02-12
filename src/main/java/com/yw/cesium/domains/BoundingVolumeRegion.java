package com.yw.cesium.domains;

import lombok.Data;

/**
 * region data format [west, south, east, north, minimum height, maximum height]
 * radian 데이터 degree로 변환
 */
@Data
public class BoundingVolumeRegion implements IBoundingVolume {

    private double[] region;

    @Override
    public BoundingVolumeType getBoundingVolumeType() {
        return BoundingVolumeType.REGION;
    }

    @Override
    public double[][] getLLA(double[][] transform) {
        double west, south, east, north;

        west = Math.toDegrees(region[0]);
        south = Math.toDegrees(region[1]);
        east = Math.toDegrees(region[2]);
        north = Math.toDegrees(region[3]);

        return new double[][]{
                new double[]{west, south},
                new double[]{east, south},
                new double[]{west, north},
                new double[]{west, south}
        };
    }
}
