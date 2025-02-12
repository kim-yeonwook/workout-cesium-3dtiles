package com.yw.cesium.domains;

import lombok.Getter;

@Getter
public class Point implements Comparable<Point> {
    double x, y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        if (this.y == p.y) {
            return Double.compare(this.x, p.x);
        }
        return Double.compare(this.y, p.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
