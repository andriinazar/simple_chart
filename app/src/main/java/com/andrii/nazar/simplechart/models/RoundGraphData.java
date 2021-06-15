package com.andrii.nazar.simplechart.models;

import android.graphics.Color;

/**
 * Created by Andrii on 05.01.2016.
 */
public class RoundGraphData {
    private String mName;
    private float mValue;
    private int mColor;

    public RoundGraphData(String name, float value) {
        mName = name;
        mValue = value;
        mColor = Color.BLACK;
    }

    public RoundGraphData(String name, float value, int color) {
        this.mName = name;
        this.mValue = value;
        this.mColor = color;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        this.mValue = value;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

}
