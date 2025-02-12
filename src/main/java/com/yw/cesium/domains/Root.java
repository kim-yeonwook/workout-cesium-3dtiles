package com.yw.cesium.domains;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Root {

    private IBoundingVolume boundingVolume;

    private List<Children> children;

    private Transform transform;

    private boolean isExistChildren() {
        return !ObjectUtils.isEmpty(this.children);
    }

    private boolean isExistTransform() {
        return !ObjectUtils.isEmpty(this.transform);
    }

    public IBoundingVolume.BoundingVolumeType getBoundingVolumeType() {
        return getBoundingVolume().getBoundingVolumeType();
    }

    public void lookupPoint(List<Point> pointList) {
        double[][] rootTransformMatrix = isExistTransform() ? getTransform().getMatrix() : null;

        if (isExistChildren()) {
            for (Children children : getChildren()) {
                children.lookupPoint(pointList, rootTransformMatrix);
            }
        } else {
            pointList.addAll(
                    Arrays.stream(getBoundingVolume().getLLA(rootTransformMatrix))
                            .map(b -> new Point(b[0], b[1]))
                            .collect(Collectors.toList())
            );
        }
    }
}
