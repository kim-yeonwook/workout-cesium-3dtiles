package com.yw.cesium.domains;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

/**
 * sphere data format [x, y, z, radius] 구 모양 데이터
 * 기본 ecef 데이터로 보임, ecef -> wgs 변횐 히면 될 듯? 다른 케이스를 찾지 못함 찾으면 수정
 * 구를 polygon 변환 하려면 각도 마다 point를 생성해야함... 촘촘하게 할수록 원에 가까워 지지만 point가 너무 많아짐
 * bbox를 사용하는게 맞을듯 함..
 */
@Data
public class BoundingVolumeSphere implements IBoundingVolume {

    private double[] sphere;

    @Override
    public BoundingVolumeType getBoundingVolumeType() {
        return BoundingVolumeType.SPHERE;
    }

    @Override
    public double[][] getLLA(double[][] transform) {
        double[] center = transformCenterPoint(transform);
        BigDecimal x = BigDecimal.valueOf(center[0]);
        BigDecimal y = BigDecimal.valueOf(center[1]);
        BigDecimal z = BigDecimal.valueOf(center[2]);

        BigDecimal radius = BigDecimal.valueOf(sphere[3] * getTransformScale(transform));

        return new double[][] {
                proj4jEcef2Wgs84(x.add(radius).doubleValue(), y.add(radius).doubleValue(), z.add(radius).doubleValue()),
                proj4jEcef2Wgs84(x.add(radius).doubleValue(), y.subtract(radius, MathContext.DECIMAL128).doubleValue(), z.add(radius).doubleValue()),
                proj4jEcef2Wgs84(x.subtract(radius, MathContext.DECIMAL128).doubleValue(), y.subtract(radius, MathContext.DECIMAL128).doubleValue(), z.add(radius).doubleValue()),
                proj4jEcef2Wgs84(x.subtract(radius, MathContext.DECIMAL128).doubleValue(), y.add(radius).doubleValue(), z.add(radius).doubleValue())
        };
    }

    private double getTransformScale(double[][] transform) {
        if (ObjectUtils.isEmpty(transform))
            return 1;

        return CMatrix4.getMaximumScale(transform);
    }

    private double[] transformCenterPoint(double[][] transform) {
        if (ObjectUtils.isEmpty(transform))
            return Arrays.copyOfRange(sphere, 0, 3);

        return CMatrix4.multiplyByPoint(transform, Arrays.copyOfRange(sphere, 0, 3));
    }
}