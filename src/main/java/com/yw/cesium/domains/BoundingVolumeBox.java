package com.yw.cesium.domains;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * [box]
 * [centerX, centerY, centerZ]: 박스 중심점(ECEF 좌표).
 * [XaxisX, XaxisY, XaxisZ]: X축 방향 벡터.
 * [YaxisX, YaxisY, YaxisZ]: Y축 방향 벡터.
 * [ZaxisX, ZaxisY, ZaxisZ]: Z축 방향 벡터.
 *
 */
@Data
public class BoundingVolumeBox implements IBoundingVolume {

    private double[] box;

    @Override
    public BoundingVolumeType getBoundingVolumeType() {
        return BoundingVolumeType.BOX;
    }

    @Override
    public double[][] getLLA(double[][] transform) {

        double[] center = transformCenterPoints(transform);
        double[][] halfAxis = transformHalfAxis(transform);

        double[][] corners = calculateCorners(center, halfAxis[0], halfAxis[1], halfAxis[2]);

        double[][] result = new double[4][2];

        for (int i = 0; i < corners.length; i++) {
            result[i] = proj4jEcef2Wgs84(corners[i][0], corners[i][1], corners[i][2]);
        }

        return result;
    }

    private double[][] calculateCorners(double[] center, double[] xAxis, double[] yAxis, double[] zAxis) {
        return new double[][]{
                add(center, xAxis, yAxis, zAxis),
                add(center, xAxis, negate(yAxis), zAxis),
                add(center, negate(xAxis), negate(yAxis), zAxis),
                add(center, negate(xAxis), yAxis, zAxis)
        };
    }

    private double[][] calculateCorners(double[] box) {
        double[] center = Arrays.copyOfRange(box, 0, 3);
        double[] xAxis = Arrays.copyOfRange(box, 3, 6);
        double[] yAxis = Arrays.copyOfRange(box, 6, 9);
        double[] zAxis = Arrays.copyOfRange(box, 9, 12);

        return new double[][]{
                add(center, xAxis, yAxis, zAxis),
                add(center, xAxis, negate(yAxis), zAxis),
                add(center, negate(xAxis), negate(yAxis), zAxis),
                add(center, negate(xAxis), yAxis, zAxis)
        };
    }

    private double[] add(double[] a, double[] b, double[] c, double[] d) {
        return new double[]{
                a[0] + b[0] + c[0] + d[0],
                a[1] + b[1] + c[1] + d[1],
                a[2] + b[2] + c[2] + d[2],
        };
    }

    private double[] negate(double[] v) {
        return new double[]{-v[0], -v[1], -v[2]};
    }

    private double[] transformCenterPoints(double[][] transform) {
        if (ObjectUtils.isEmpty(transform))
            return Arrays.copyOfRange(box, 0, 3);

        return CMatrix4.multiplyByPoint(transform, Arrays.copyOfRange(box, 0, 3));
    }

    private double[][] transformHalfAxis(double[][] transform) {
        double[][] halfAxis = {
                Arrays.copyOfRange(box, 3, 6),
                Arrays.copyOfRange(box, 6, 9),
                Arrays.copyOfRange(box, 9, 12)
        };

        return ObjectUtils.isEmpty(transform) ? halfAxis
                : CMatrix3.multiply(CMatrix4.getMatrix3(transform), halfAxis);
    }
}
