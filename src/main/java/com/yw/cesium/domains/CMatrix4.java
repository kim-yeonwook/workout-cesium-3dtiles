package com.yw.cesium.domains;

public class CMatrix4 {

    public static final int MATRIX4 = 4;

    public static double[][] getMatrix3(double[][] matrix4) {
        double[][] matrix3 = new double[CMatrix3.MATRIX3][CMatrix3.MATRIX3];

        for (int i=0; i<CMatrix3.MATRIX3; i++) {
            matrix3[i][0] = matrix4[i][0];
            matrix3[i][1] = matrix4[i][1];
            matrix3[i][2] = matrix4[i][2];
        }

        return matrix3;
    }

    public static double[] multiplyByPoint(double[][] transform, double[] point) {
        return new double[] {
            transform[0][0] * point[0] + transform[1][0] * point[1] + transform[2][0] * point[2] + transform[3][0],
            transform[0][1] * point[0] + transform[1][1] * point[1] + transform[2][1] * point[2] + transform[3][1],
            transform[0][2] * point[0] + transform[1][2] * point[1] + transform[2][2] * point[2] + transform[3][2]
        };
    }

    public static double[][] multiplyTransform(double[][] lMatrix, double[][] rMatrix) {
        double[][] multiply = new double[MATRIX4][MATRIX4];
        multiply[0] = new double[] {
                lMatrix[0][0] * rMatrix[0][0] + lMatrix[1][0] * rMatrix[0][1] + lMatrix[2][0] * rMatrix[0][2],
                lMatrix[0][1] * rMatrix[0][0] + lMatrix[1][1] * rMatrix[0][1] + lMatrix[2][1] * rMatrix[0][2],
                lMatrix[0][2] * rMatrix[0][0] + lMatrix[1][2] * rMatrix[0][1] + lMatrix[2][2] * rMatrix[0][2],
                0.0
        };
        multiply[1] = new double[] {
                lMatrix[0][0] * rMatrix[1][0] + lMatrix[1][0] * rMatrix[1][1] + lMatrix[2][0] * rMatrix[1][2],
                lMatrix[0][1] * rMatrix[1][0] + lMatrix[1][1] * rMatrix[1][1] + lMatrix[2][1] * rMatrix[1][2],
                lMatrix[0][2] * rMatrix[1][0] + lMatrix[1][2] * rMatrix[1][1] + lMatrix[2][2] * rMatrix[1][2],
                0.0
        };
        multiply[2] = new double[] {
                lMatrix[0][0] * rMatrix[2][0] + lMatrix[1][0] * rMatrix[2][1] + lMatrix[2][0] * rMatrix[2][2],
                lMatrix[0][1] * rMatrix[2][0] + lMatrix[1][1] * rMatrix[2][1] + lMatrix[2][1] * rMatrix[2][2],
                lMatrix[0][2] * rMatrix[2][0] + lMatrix[1][2] * rMatrix[2][1] + lMatrix[2][2] * rMatrix[2][2],
                0.0
        };
        multiply[3] = new double[] {
                lMatrix[0][0] * rMatrix[3][0] + lMatrix[1][0] * rMatrix[3][1] + lMatrix[2][0] * rMatrix[3][2] + lMatrix[3][0],
                lMatrix[0][1] * rMatrix[3][0] + lMatrix[1][1] * rMatrix[3][1] + lMatrix[2][1] * rMatrix[3][2] + lMatrix[3][1],
                lMatrix[0][2] * rMatrix[3][0] + lMatrix[1][2] * rMatrix[3][1] + lMatrix[2][2] * rMatrix[3][2] + lMatrix[3][2],
                1.0
        };

        return multiply;
    }

    public static double[][] multiply(double[][] lMatrix, double[][] rMatrix) {
        double[][] multiply = new double[MATRIX4][MATRIX4];

        for (int i=0; i<MATRIX4; i++) {
            for (int j=0; j<MATRIX4; j++) {
                for (int z=0; z<MATRIX4; z++) {
                    multiply[i][j] += lMatrix[z][j] * rMatrix[i][z];
                }
            }
        }

        return multiply;
    }

    public static double getMaximumScale(double[][] transform) {
        double maxScale = Double.MIN_VALUE;
        for (int i=0; i<CMatrix3.MATRIX3; i++) {
            double r = 0.0;
            for (int j=0; j<CMatrix3.MATRIX3; j++) {
                r += Math.pow(transform[i][j], 2);
            }
            double scale = Math.sqrt(r);
            if (scale>maxScale)
                maxScale = scale;
        }

        return maxScale;
    }
}
