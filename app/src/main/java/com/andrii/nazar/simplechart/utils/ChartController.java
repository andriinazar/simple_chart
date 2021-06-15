package com.andrii.nazar.simplechart.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

import com.andrii.nazar.simplechart.constants.AppConstants;
import com.andrii.nazar.simplechart.models.GraphData;
import com.andrii.nazar.simplechart.models.Point;
import com.andrii.nazar.simplechart.views.GraphChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii on 20.12.2015.
 */
public class ChartController {
    //variables
    private SurfaceHolder mHolder;
    private List<Line> mLines;
    private List<Line> mGreedYLine;
    private List<Line> mGreedXLine;
    private List<SurfaceText> mTextX;
    private List<SurfaceText> mTextY;
    private int mGraphBackgroundColor;
    public int width;
    public int height;
    private boolean mSetYGreed;
    private boolean mSetXGreed;
    private int mXGreedCount;
    private int mYGreedCount;
    private GraphData mGraphData;


    public ChartController(SurfaceHolder holder, int width, int height) {
        this.mHolder = holder;
        mLines = new ArrayList<Line>();
        mTextX = new ArrayList<SurfaceText>();
        mTextY = new ArrayList<SurfaceText>();
        mGreedYLine = new ArrayList<Line>();
        mGreedXLine = new ArrayList<Line>();
        this.width = width;
        this.height = height;
        mGraphBackgroundColor = Color.WHITE;
        mSetYGreed = false;
        mSetXGreed = false;
        mXGreedCount = 5;
        mYGreedCount = 5;

    }

    public List<Line> getLines() {
        return mLines;
    }

    public void setLines(List<Line> lines) {
        this.mLines = lines;
    }

    public List<Line> getGreedYLine() {
        return mGreedYLine;
    }

    public void setGreedYLine(List<Line> greedYLine) {
        this.mGreedYLine = greedYLine;
    }

    public List<Line> getGreedXLine() {
        return mGreedXLine;
    }

    public void setGreedXLine(List<Line> greedXLine) {
        this.mGreedXLine = greedXLine;
    }

    public List<SurfaceText> getTextX() {
        return mTextX;
    }

    public void setTextX(List<SurfaceText> textX) {
        this.mTextX = textX;
    }

    public List<SurfaceText> getTextY() {
        return mTextY;
    }

    public void setTextY(List<SurfaceText> textY) {
        this.mTextY = mTextY;
    }

    public ChartController(){
        mLines = new ArrayList<Line>();
        mTextX = new ArrayList<SurfaceText>();
        mTextY = new ArrayList<SurfaceText>();
        mGreedYLine = new ArrayList<Line>();
        mGreedXLine = new ArrayList<Line>();
        mGraphBackgroundColor = Color.WHITE;
        mSetYGreed = false;
        mSetXGreed = false;
        mXGreedCount = 5;
        mYGreedCount = 5;
    }

    public void copyData(ChartController chartController){

    }


    public void addLine(Line line){
        mLines.add(line);
    }
    public void addTextX(SurfaceText textX){
        mTextX.add(textX);
    }
    public void addTextY(SurfaceText textY) {
        mTextY.add(textY);
    }
    public void addGreedYLine(Line line){
        mGreedYLine.add(line);
    }

    public void addGreedXLine(Line line){
        mGreedXLine.add(line);
    }

    public int getGraphBackground() {
        return mGraphBackgroundColor;
    }

    public void setGraphBackground(String graphBackground) {
        this.mGraphBackgroundColor = Color.parseColor(graphBackground);
    }
    public void setGraphBackground(int graphBackground) {
        this.mGraphBackgroundColor = graphBackground;
    }

    public boolean isSetYGreed() {
        return mSetYGreed;
    }

    public void setYGreed(boolean setYGreed) {
        this.mSetYGreed = setYGreed;
    }

    public boolean isSetXGreed() {
        return mSetXGreed;
    }

    public void setXGreed(boolean setXGreed) {
        this.mSetXGreed = setXGreed;
    }

    public int getXGreedCount() {
        return mXGreedCount;
    }

    public void setXGreedCount(int xGreedCount) {
        this.mXGreedCount = xGreedCount;
    }

    public int getYGreedCount() {
        return mYGreedCount;
    }

    public void setYGreedCount(int yGreedCount) {
        this.mYGreedCount = yGreedCount;
    }

    public GraphData getGraphData() {
        return mGraphData;
    }

    public void setGraphData(GraphData graphData) {
        this.mGraphData = graphData;
    }

    public static class LineCoordinates{
        private int mStartX;
        private int mStartY;
        private int mEndX;
        private int mEndY;
        public LineCoordinates(){}
        public LineCoordinates(int startX, int startY, int endX, int endY)
        {
            mStartX = startX;
            mStartY = startY;
            mEndX = endX;
            mEndY = endY;
        }
        public Canvas drawLine(Canvas canvas, Paint linePaint){
            canvas.drawLine(mStartX, mStartY, mEndX, mEndY, linePaint);
            return canvas;
        }
    };

    public static class Line{
        private int mColor;
        private int mStroke;
        private List<LineCoordinates> mCoordinates;
        private int mPointColor;
        private int mShadingColor;
        private int mPointRadius;
        private boolean mDrawPoint;
        private boolean mSplice;
        public Line(){
            mCoordinates = new ArrayList<LineCoordinates>();
            mColor = Color.WHITE;
            mPointColor = Color.WHITE;
            mShadingColor = Color.BLACK;
            mPointRadius = 5;
            mStroke = 2;
            mDrawPoint = false;
            mSplice = false;
        }
        public void addLine(int startX, int startY, int endX, int endY){
            mCoordinates.add(new LineCoordinates(startX, startY, endX, endY));
        }

        public Canvas drawLine(Canvas canvas){
            Paint linePaint = new Paint();
            linePaint.setColor(mColor);
            linePaint.setStrokeWidth(mStroke);

            for(int i = 0; i < mCoordinates.size(); i++ ){
                canvas = mCoordinates.get(i).drawLine(canvas, linePaint);
            }
            return canvas;
        }

        public Canvas drawPoint(Canvas canvas){
                Paint pointPaint = new Paint();
                int tmpStroke = 0;
                tmpStroke = (int) mPointRadius / 2;
                if (tmpStroke == 0)
                    pointPaint.setStrokeWidth(1);
                else
                    pointPaint.setStrokeWidth(tmpStroke);
                pointPaint.setStyle(Paint.Style.STROKE);
                pointPaint.setColor(mPointColor);

                Paint shading = new Paint();
                shading.setStyle(Paint.Style.FILL);
                shading.setColor(mShadingColor);

                Path path = new Path();
                for (int i = 0; i < mCoordinates.size(); i++) {

                    path.addCircle(mCoordinates.get(i).mStartX, mCoordinates.get(i).mStartY, mPointRadius, Path.Direction.CW);
                    canvas.drawPath(path, pointPaint);
                    path.reset();
                    path.addCircle(mCoordinates.get(i).mStartX, mCoordinates.get(i).mStartY, mPointRadius - tmpStroke, Path.Direction.CW);
                    canvas.drawPath(path, shading);
                    path.reset();
                    if (i == mCoordinates.size() - 1) {
                        path.addCircle(mCoordinates.get(i).mEndX, mCoordinates.get(i).mEndY, mPointRadius, Path.Direction.CW);
                        canvas.drawPath(path, pointPaint);
                        path.reset();
                        path.addCircle(mCoordinates.get(i).mEndX, mCoordinates.get(i).mEndY, mPointRadius - tmpStroke, Path.Direction.CW);
                        canvas.drawPath(path, shading);
                        path.reset();
                    }

                }
                return canvas;

        }



        public Canvas drawCurveLine(Canvas canvas){

            Paint paint  = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(mStroke);
            paint.setColor(mColor);

            for (int i = 0; i < mCoordinates.size(); i++){
                Path path = new Path();
                Point current = new Point();
                Point next = new Point();
                current.x = mCoordinates.get(i).mStartX;
                current.y = mCoordinates.get(i).mStartY;
                next.x = mCoordinates.get(i).mEndX;
                next.y = mCoordinates.get(i).mEndY;
                if (current.y <= next.y) {
                    PointF middle = new PointF();
                    middle.set(((current.x + next.x) / 2), ((current.y + next.y) / 2));
                    current.dx = (float) (((current.x + middle.x) / 2) + ((middle.x - current.x) / 1.5));
                    current.dy = (float) (((current.y + middle.y) / 2) - ((middle.y - current.y) / 1.5));

                    next.dx = (float) (((middle.x + next.x) / 2) - ((next.x - middle.x) / 1.5));
                    next.dy = (float) (((middle.y + next.y) / 2) + ((next.y - middle.y) / 1.5));

                    path.moveTo(current.x, current.y);
                    path.cubicTo(current.dx, current.dy, next.dx, next.dy, next.x, next.y);
                    canvas.drawPath(path, paint);
                }
                if (current.y > next.y){
                    PointF middle = new PointF();
                    middle.set(((current.x + next.x) / 2), ((current.y + next.y) / 2));
                    current.dx = (float) (((current.x + middle.x) / 2) - ((current.x - middle.x) / 1.5));
                    current.dy = (float) (((current.y + middle.y) / 2) + ((current.y - middle.y) / 1.5));

                    next.dx = (float) (((middle.x + next.x) / 2) + ((middle.x - next.x) / 1.5));
                    next.dy = (float) (((middle.y + next.y) / 2) - ((middle.y - next.y) / 1.5));

                    path.moveTo(current.x, current.y);
                    path.cubicTo(current.dx, current.dy, next.dx, next.dy, next.x, next.y);
                    canvas.drawPath(path, paint);
                }

            }

            return canvas;
        }



        public int getColor() {
            return mColor;
        }


        public void setColor(String color) {
            this.mColor = Color.parseColor(color);
        }
        public void setColor(int color) {
            this.mColor = color;
        }

        public int getStroke() {
            return mStroke;
        }

        public void setStroke(int stroke) {
            this.mStroke = stroke;
        }

        public int getPointColor() {
            return mPointColor;
        }

        public void setPointColor(String pointColor) {
            this.mPointColor = Color.parseColor(pointColor);
        }
        public void setPointColor(int pointColor) {
            this.mPointColor = pointColor;
        }

        public int getPointRadius() {
            return mPointRadius;
        }

        public void setPointRadius(int pointRadius) {
            this.mPointRadius = pointRadius;
        }

        public int getPointShadingColor() {
            return mShadingColor;
        }

        public void setPointShadingColor(String shadingColor) {
            this.mShadingColor = Color.parseColor(shadingColor);
        }

        public void setPointShadingColor(int shadingColor) {
            this.mShadingColor = shadingColor;
        }

        public boolean isDrawPoint() {
            return mDrawPoint;
        }

        public void setDrawPoint(boolean drawPoint) {
            this.mDrawPoint = drawPoint;
        }

        public boolean isSplice() {
            return mSplice;
        }

        public void setSplice(boolean splice) {
            this.mSplice = splice;
        }
    };

    public static class SurfaceText {
        private int mTextXColor;
        private int mTextYColor;
        private List<GraphChartView.GreedData> mYHeader;
        private List<GraphChartView.GreedData> mXHeader;
        private int mTextXSize;
        private int mTextYSize;
        private Typeface mTextXFont;
        private Typeface mTextYFont;
        private int mWeight;
        private int mHeight;

        public SurfaceText(int weight, int height) {
            mWeight = weight;
            mHeight = height;
            mYHeader = new ArrayList<GraphChartView.GreedData>();
            mXHeader = new ArrayList<GraphChartView.GreedData>();
            mTextYColor = Color.WHITE;
            mTextXColor = Color.WHITE;
            mTextXSize = 20;
            mTextXSize = 20;
            mTextXFont = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
            mTextYFont = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);

        }

        public void addYText(GraphChartView.GreedData textData){
            mYHeader.add(textData);
        }
        public void addXText(GraphChartView.GreedData textData){
            mXHeader.add(textData);
        }

        public Canvas drawYText(Canvas canvas){
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(mTextYColor);
            textPaint.setTextSize(mTextYSize);
            textPaint.setTypeface(mTextYFont);
            textPaint.setTextAlign(Paint.Align.RIGHT);
            if (mYHeader != null) {
                for (int i = 0; i < mYHeader.size(); i++) {
                    if (mYHeader.get(i).getStringHeader() != null) {
                        canvas.drawText(String.valueOf(mYHeader.get(i).getStringHeader()), AppConstants.GRAPH_PADDING - 10, (mYHeader.get(i).getCoordinate() + (int) (mTextYSize / 2)), textPaint);
                    }else {
                        canvas.drawText(String.format("%.0f", mYHeader.get(i).getHeader()), AppConstants.GRAPH_PADDING - 10, (mYHeader.get(i).getCoordinate() + (int) (mTextYSize / 2)), textPaint);
                    }
                }
            }

            return canvas;
        }

        public Canvas drawXText(Canvas canvas) {
            Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setColor(mTextXColor);
            textPaint.setTextSize(mTextXSize);
            textPaint.setTypeface(mTextXFont);
            textPaint.setTextAlign(Paint.Align.CENTER);
            if (mXHeader != null){
                for (int i = 0; i < mXHeader.size(); i++){
                    if (mXHeader.get(i).getStringHeader() != null) {
                        canvas.drawText(mXHeader.get(i).getStringHeader(), mXHeader.get(i).getCoordinate(), mHeight - 40, textPaint);
                    }else{
                        canvas.drawText((String.valueOf((int) mXHeader.get(i).getHeader())), mXHeader.get(i).getCoordinate(), mHeight - 40, textPaint);
                    }
                    //canvas.drawCircle(mXHeader.get(i).getCoordinate(), mHeight - AppConstants.GRAPH_PADDING/2, 10, textPaint);
                }
            }
            return canvas;
        }

        public int getTextXColor() {
            return mTextXColor;
        }

        public void setTextXColor(String textXColor) {
            this.mTextXColor = Color.parseColor(textXColor);
        }

        public void setTextXColor(int textXColor) {
            this.mTextXColor = textXColor;
        }

        public int getTextYColor() {
            return mTextYColor;
        }

        public void seTextYColor(String textYColor) {
            this.mTextYColor = Color.parseColor(textYColor);
        }

        public void seTextYColor(int textYColor) {
            this.mTextYColor = textYColor;
        }

        public int getTextXSize() {
            return mTextXSize;
        }

        public void setTextXSize(int textXSize) {
            this.mTextXSize = textXSize;
        }

        public int getTextYSize() {
            return mTextYSize;
        }

        public void setTextYSize(int textYSize) {
            this.mTextYSize = textYSize;
        }

        public Typeface getTextYFont() {
            return mTextYFont;
        }

        public void setTextYFont(Typeface textYFont) {
            this.mTextYFont = textYFont;
        }


        public Typeface getTextXFont() {
            return mTextXFont;
        }

        public void setTextXFont(Typeface mTextXFont) {
            this.mTextXFont = mTextXFont;
        }


    };

    public void render(){
        Canvas canvas = mHolder.lockCanvas();
        if(canvas != null){

            // clean screen
            //canvas.setBitmap(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
            //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawColor(mGraphBackgroundColor);

            // get canvas size
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            //canvas.drawLine(AppConstants.GRAPH_PADDING, height - AppConstants.GRAPH_PADDING, AppConstants.GRAPH_PADDING, 10, paint);
            //canvas.drawLine(AppConstants.GRAPH_PADDING, height - AppConstants.GRAPH_PADDING, width - 10, height - AppConstants.GRAPH_PADDING, paint);
            //canvas.drawLine(0,0,width,height,paint);



            //draw greed
            if (mSetYGreed) {
                if (mGreedYLine != null) {
                    for (int i = 0; i < mGreedYLine.size(); i++) {
                        mGreedYLine.get(i).drawLine(canvas);
                    }
                }
            }
            if (mSetXGreed) {
                if (mGreedXLine != null) {
                    for (int i = 0; i < mGreedXLine.size(); i++) {
                        mGreedXLine.get(i).drawLine(canvas);
                    }
                }
            }


            //draw Y text
            if (mTextY != null) {
                for (int i = 0; i < mTextY.size(); i++) {
                    mTextY.get(i).drawYText(canvas);
                }
            }

            //draw X text
            if (mTextX != null) {
                for (int i = 0; i < mTextX.size(); i++) {
                    mTextX.get(i).drawXText(canvas);
                }
            }

            if (mLines != null) {
                for (int i = 0; i < mLines.size(); i++) {
                    if(mLines.get(i).isSplice()){
                        mLines.get(i).drawCurveLine(canvas);
                    }else {
                        mLines.get(i).drawLine(canvas);
                    }
                    if (mLines.get(i).isDrawPoint()) {
                        mLines.get(i).drawPoint(canvas);
                    }
                }
            }



            // draw
            mHolder.unlockCanvasAndPost(canvas);
            clearCanvas();
        }
    }

    public void clearCanvas(){
        if (mGreedYLine != null)
        mGreedYLine.clear();
        if (mGreedXLine != null)
        mGreedXLine.clear();
        if (mTextY != null)
        mTextY.clear();
        if (mTextX != null)
        mTextX.clear();
        if (mLines != null)
        mLines.clear();

    }

    public void initXYLines(Canvas canvas, Paint paint){
        // get canvas size
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawLine(10, height, 10, 10, paint);
        canvas.drawLine(10, height, width, 10, paint);
    }
}
