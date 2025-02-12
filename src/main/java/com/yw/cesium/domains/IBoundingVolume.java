package com.yw.cesium.domains;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.locationtech.proj4j.*;

/**
 * <a href="https://github.com/CesiumGS/3d-tiles/tree/main/specification"/a>
 * -
 * ecef -> wgs84 변환시 좌표 정학성 이슈
 * 최종 으로 가장 정확도가 높은 2개 선택
 * 1. proj4j library
 * 2. 반복 계산
 */
@JsonDeserialize(using = BoundingVolumeDeserializer.class)
public interface IBoundingVolume {

    enum BoundingVolumeType {
        BOX, REGION, SPHERE
    }

    BoundingVolumeType getBoundingVolumeType();

    double[][] getLLA(double[][] transform);

    default double[] proj4jEcef2Wgs84(double x, double y, double z) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateTransformFactory factory = new CoordinateTransformFactory();
        CoordinateReferenceSystem ecef = crsFactory.createFromName("EPSG:4978");
        CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326");
        CoordinateTransform transform = factory.createTransform(ecef, wgs84);

        ProjCoordinate input = new ProjCoordinate(x, y, z);
        ProjCoordinate output = new ProjCoordinate();
        transform.transform(input, output);

        return new double[] {output.x, output.y};
    }

    default double[] doWhileEcef2Wgs84(double x, double y, double z) {
        double a = 6378137.0;
        double e2 = 0.00669437999014;

        double lon = Math.atan2(y, x) * 180.0 / Math.PI;
        double p = Math.sqrt(x * x + y * y);
        double lat = Math.atan2(z, p * (1 - e2));
        double latPrev;

        int maxIterations = 10;
        double threshold = 1e-12;
        do {
            latPrev = lat;
            double sinLat = Math.sin(latPrev);
            double N = a / Math.sqrt(1 - e2 * sinLat * sinLat);
            lat = Math.atan2(z + e2 * N * sinLat, p);
        } while (Math.abs(lat - latPrev) > threshold && --maxIterations > 0);

        lat = Math.toDegrees(lat);

        return new double[]{lat, lon};
    }
}