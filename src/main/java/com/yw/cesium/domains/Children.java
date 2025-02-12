package com.yw.cesium.domains;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Children {

    private IBoundingVolume boundingVolume;

    private List<Children> children;

    private Transform transform;

    public void lookupPoint(List<Point> pointList, double[][] upperTransform) {
        double[][] multiplyTransform = ObjectUtils.isEmpty(getTransform()) ? upperTransform : getTransform().multiply(upperTransform);

        if (isExistSubChildren()) {
            for (Children sub : getChildren()) {
                sub.lookupPoint(pointList, multiplyTransform);
            }
        } else {
            pointList.addAll(
                    Arrays.stream(getBoundingVolume().getLLA(multiplyTransform))
                            .map(b -> new Point(b[0], b[1]))
                            .collect(Collectors.toList())
            );
        }
    }

    public boolean isExistSubChildren() {
        return !ObjectUtils.isEmpty(this.children);
    }
}
