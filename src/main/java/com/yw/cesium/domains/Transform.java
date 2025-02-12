package com.yw.cesium.domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * transform 이 있는 경우 좌표 중심점 이동 필요...
 * [transform]
 * m11, m12, m13 → X축 변환 (회전 및 스케일)
 * m21, m22, m23 → Y축 변환 (회전 및 스케일)
 * m31, m32, m33 → Z축 변환 (회전 및 스케일)
 * m41, m42, m43 → 타일의 중심 좌표 (ECEF 좌표)
 * m14 -> 0
 * m24 -> 0
 * m34 -> 0
 * m44 -> 일반적으로 1 (동차 좌표 시스템)
 * -
 * <a href="https://github.com/CesiumGS/cesium/blob/main/packages/engine/Source/Core/Matrix4.js"/a>
 * Cesium 에서 Transform 데이터 중첩 하는 방법 URL, multiplyTransformation() 함수 명
 * 동차 좌표 시스템은 고정 으로 0, 0, 0, 1 로 넣어 준다.
 */
@JsonDeserialize(using = TransformDeserializer.class)
public class Transform {

    private final double[] transform;

    public Transform(double[] transform) {
        this.transform = transform;
    }

    public double[][] multiply(double[][] parent) {
        double[][] child = getMatrix();
        if (ObjectUtils.isEmpty(parent)) {
            return child;
        }

        return CMatrix4.multiplyTransform(parent, child);
    }

    public double[][] getMatrix() {
        if (ObjectUtils.isEmpty(this.transform))
            return null;

        return new double[][] {
                Arrays.copyOfRange(this.transform, 0, 4),
                Arrays.copyOfRange(this.transform, 4, 8),
                Arrays.copyOfRange(this.transform, 8, 12),
                Arrays.copyOfRange(this.transform, 12, 16)
        };
    }
}
