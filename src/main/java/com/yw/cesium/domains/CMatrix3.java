package com.yw.cesium.domains;

public class CMatrix3 {

    public static final int MATRIX3 = 3;

    public static double[][] multiply(double[][] lMatrix, double[][] rMatrix) {
        double[][] multiply = new double[MATRIX3][MATRIX3];

        for (int i=0; i<MATRIX3; i++) {
            for (int j=0; j<MATRIX3; j++) {
                for (int z=0; z<MATRIX3; z++) {
                    multiply[i][j] += lMatrix[z][j] * rMatrix[i][z];
                }
            }
        }

        return multiply;
    }
}
