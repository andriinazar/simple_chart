package com.andrii.nazar.simplechart.models;

/**
 * Created by Andrii on 23.12.2015.
 */
public class Point {
    public float x, y;
    public float dx, dy;

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
