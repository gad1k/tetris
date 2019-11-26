package com.javarush.games.tetris;

public class ShapeMatrix {
    public static final int[][] I = new int[][]{
            {12},
            {12},
            {12},
            {12}
    };

    public static final int[][] J = new int[][]{
            {7, 0, 0},
            {7, 7, 7}
    };

    public static final int[][] L = new int[][]{
            {0, 0, 4},
            {4, 4, 4}
    };

    public static final int[][] O = new int[][]{
            {3, 3},
            {3, 3}
    };

    public static final int[][] S = new int[][]{
            {0, 6, 6},
            {6, 6, 0}
    };

    public static final int[][] T = new int[][]{
            {0, 8, 0},
            {8, 8, 8}
    };

    public static final int[][] Z = new int[][]{
            {5, 5, 0},
            {0, 5, 5}
    };
}
