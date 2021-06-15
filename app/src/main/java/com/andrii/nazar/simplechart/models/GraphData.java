package com.andrii.nazar.simplechart.models;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Andrii on 02.01.2016.
 */
public class GraphData {
    private ArrayList<Long> mXData;
    private ArrayList<Float> mYData;
    private int mLineColor;
    private boolean mDrawPoint;
    private boolean mSplice;
    private int mPointRadius;
    private int mPintColor;
    private int mPointShadingColor;
    private int mLineStroke;

    public GraphData(){
        mXData = new ArrayList<>();
        mYData = new ArrayList<>();
        mLineColor = Color.BLACK;
        mDrawPoint = false;
        mSplice = false;
        mPointRadius = 10;
        mPintColor = Color.BLACK;
        mPointShadingColor = Color.BLACK;
        mLineStroke = 5;
    }


    public void addPoint(Long x, Float y) {
        mXData.add(x);
        mYData.add(y);
    }


    public ArrayList<Long> getXData() {
        return mXData;
    }

    public void setXData(ArrayList<Long> xData) {
        this.mXData = xData;
    }

    public ArrayList<Float> getYData() {
        return mYData;
    }

    public void setYData(ArrayList<Float> yData) {
        this.mYData = yData;
    }


    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public boolean isDrawPoint() {
        return mDrawPoint;
    }

    public void setDrawPoint(boolean mDrawPoint) {
        this.mDrawPoint = mDrawPoint;
    }

    public boolean isSplice() {
        return mSplice;
    }

    public void setSplice(boolean mSplice) {
        this.mSplice = mSplice;
    }

    public int getPointRadius() {
        return mPointRadius;
    }

    public void setPointRadius(int mPointRadius) {
        this.mPointRadius = mPointRadius;
    }

    public int getPintColor() {
        return mPintColor;
    }

    public void setPintColor(int mPintColor) {
        this.mPintColor = mPintColor;
    }

    public int getPointShadingColor() {
        return mPointShadingColor;
    }

    public void setPointShadingColor(int mPointShadingColor) {
        this.mPointShadingColor = mPointShadingColor;
    }

    public int getLineStroke() {
        return mLineStroke;
    }

    public void setLineStroke(int mLineStroke) {
        this.mLineStroke = mLineStroke;
    }
}
