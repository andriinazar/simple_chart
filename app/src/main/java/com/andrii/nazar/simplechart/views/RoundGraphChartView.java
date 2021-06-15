package com.andrii.nazar.simplechart.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.andrii.nazar.simplechart.R;
import com.andrii.nazar.simplechart.constants.AppConstants;
import com.andrii.nazar.simplechart.models.RoundGraphData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Andrii on 05.01.2016.
 */
public class RoundGraphChartView extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mHolder;
    private List<RoundGraphData> mGraphData;
    private float mCenterValue;
    private boolean mSurfaceCreated = false;

    public RoundGraphChartView(Context context) {
        super(context);
        mContext = context;
        mGraphData = new ArrayList<RoundGraphData>();
        mCenterValue = 0;
    }

    public RoundGraphChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGraphData = new ArrayList<RoundGraphData>();
        mCenterValue = 0;
    }

    public RoundGraphChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mGraphData = new ArrayList<RoundGraphData>();
        mCenterValue = 0;
    }

    public void start(){
        mHolder = this.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceCreated = true;
        beginDraw();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void beginDraw(){
        if( mSurfaceCreated ){
            if( mGraphData != null && mGraphData.size() > 0 ){
                drawCircleDiagram(mGraphData, mCenterValue);
            } else {
                drawEmptyData(mCenterValue);
            }
        }
    }

    public void setData(final List<RoundGraphData> data, final float centerValue){
        mGraphData = data;
        mCenterValue = centerValue;
        beginDraw();
    }

    public void drawDiagram(final List<RoundGraphData> data, final float centerValue){

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true){
                        if (data != null) {
                            if (data.size() != 0) {
                                drawCircleDiagram(data, centerValue);
                            } else {
                                drawEmptyData(centerValue);
                            }
                        }
                        break;
                    }
            }
        };
        thread.start();
    }

    private void drawEmptyData(float centerValue) {
        Canvas canvas = mHolder.lockCanvas();
        int mTextSize = 30;
        int mCircleStroke = 30;
        int mTextSize2 = 17;
        RoundGraphData tmpGraphData = new RoundGraphData("", 1000, 0);

        if (canvas != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            int mCenterX = height / 2;
            int mCenterY = width / 2;

            int size = (mCenterX + mCenterY)/4;
            mCircleStroke = size/3;
            mTextSize = mCircleStroke;
            mTextSize2 = mCircleStroke/2;

            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawColor(Color.WHITE);
            //canvas.drawColor(Color.WHITE);

            Paint p = new Paint();
            // smooths
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(mCircleStroke);
            //p.setStrokeWidth(size - (size * 0.2f));
            Path path = new Path();
            //path.addCircle(mCenterX, mCenterY, ((height/2) - 5), Path.Direction.CW);
            //canvas.drawPath(path, p);
//            Typeface mTfRobo = Typeface.createFromAsset(mContext.getAssets(),
//                    "fonts/roboto_regular.ttf");
            RectF rectF = new RectF((width / 2) - (size - (mCircleStroke/2)), (height / 2) - (size - (mCircleStroke/2)), (width / 2) + (size - (mCircleStroke/2)), (height / 2) + (size - (mCircleStroke/2)));
            //canvas.drawRect(rectF, p);
            // demo
                float[] data = new float[1];
                    data[0] = tmpGraphData.getValue();

                // get sum
                float sum = 0.0f;
                for (int i = 0; i < data.length; i++)
                    sum += data[i];

                // draw data
                List<Float> anglesStartList = new ArrayList<Float>();
                List<Float> anglesEndList = new ArrayList<Float>();
                List<Float> procentList = new ArrayList<Float>();
                List<PointF> points = new ArrayList<PointF>();
                float startAngle = 0.0f;
                for (int i = 0; i < data.length; i++) {
                    // get random color
//                    switch (transactionType) {
//                        case AppConstants.CATEGORY_TYPE_OUTGOING:
//                            p.setColor(getResources().getColor(R.color.dashboard_outgoing_value_color));
//                            break;
//                        case AppConstants.CATEGORY_TYPE_INCOME:
//                            p.setColor(getResources().getColor(R.color.dashboard_incoming_value_color));
//                            break;
//                    }
                    // calc
                    float procent = (100 * data[i]) / sum;
                    procentList.add(procent);
                    float angleEnd = (360 * procent) / 100;
                    anglesStartList.add(startAngle);

                    float steep = 0;
                    steep = angleEnd;
                    angleEnd = angleEnd + startAngle;
                    anglesEndList.add(angleEnd);
                    // draw

                    canvas.drawArc(rectF, startAngle, steep, false, p);
                    startAngle = angleEnd;
                }

                float r = (float) ((width + height) / 5.5);
                for (int i = 0; i < anglesStartList.size(); i++){
                    float pointAngle = (anglesStartList.get(i) + ((anglesEndList.get(i) - anglesStartList.get(i))/2));
                    // Calculated from canvas width
                    float x =  (float)((width/2) + r* Math.cos(Math.toRadians(pointAngle ) ));
                    float y =  (float)((height/2) + r* Math.sin(Math.toRadians(pointAngle ) ));
                    points.add(new PointF(x, y));
                }

                int procentTextSize = (int) (mCircleStroke/1.5);
                int headerTextSize = (int)(mCircleStroke/1.5);
                Paint mTextPocentPaint = new Paint();
                mTextPocentPaint.setColor(mContext.getResources().getColor(R.color.dashboard_round_text_color));
                mTextPocentPaint.setTextSize(procentTextSize);
                mTextPocentPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                mTextPocentPaint.setTextAlign(Paint.Align.CENTER);



                /*Paint mTmpPaint1 = new Paint();
                mTmpPaint1.setStrokeWidth(3);
                mTmpPaint1.setColor(Color.WHITE);
                canvas.drawLine(width / 2, 0, width / 2, height, mTmpPaint1);
                canvas.drawLine(0, height/2, width, height/2, mTmpPaint1);*/



                Paint textPaint = new Paint();
//                switch (transactionType) {
//                    case AppConstants.CATEGORY_TYPE_OUTGOING:
//                        textPaint.setColor(getResources().getColor(R.color.dashboard_outgoing_value_color));
//                        break;
//                    case AppConstants.CATEGORY_TYPE_INCOME:
//                        textPaint.setColor(getResources().getColor(R.color.dashboard_incoming_value_color));
//                        break;
//                }

                textPaint.setTextSize(mTextSize);
                textPaint.setTextAlign(Paint.Align.CENTER);
                String tmp = String.valueOf((int)centerValue);
                canvas.drawText(tmp, width / 2, ((height / 2)), textPaint);

                Paint textPaint2 = new Paint();

//                textPaint2.setTypeface(Typeface.create(mTfRobo, Typeface.NORMAL));
                textPaint2.setColor(getResources().getColor(R.color.dashboard_outgoing_header_color));
                textPaint2.setTextSize(mTextSize2);
                textPaint2.setTextAlign(Paint.Align.CENTER);
//                switch (transactionType) {
//                    case AppConstants.CATEGORY_TYPE_OUTGOING:
//                        canvas.drawText(getResources().getString(R.string.dashboard_balance_outgoing_header), width / 2, ((height / 2) + mTextSize2 + (height / 50)), textPaint2);
//                        break;
//                    case AppConstants.CATEGORY_TYPE_INCOME:
//                        canvas.drawText(getResources().getString(R.string.dashboard_balance_incoming_header), width / 2, ((height / 2) + mTextSize2 + (height / 50)), textPaint2);
//                        break;
//                }

                mHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void drawCircleDiagram(List<RoundGraphData> graphData, float centerValue){
        Canvas canvas = mHolder.lockCanvas();
        int mTextSize = 30;
        int mCircleStroke = 30;
        int mTextSize2 = 17;

        if (canvas != null) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            int mCenterX = height / 2;
            int mCenterY = width / 2;

            int size = (mCenterX + mCenterY)/4;
            mCircleStroke = size/3;
            mTextSize = mCircleStroke;
            mTextSize2 = mCircleStroke/2;

            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawColor(Color.WHITE);
            //canvas.drawColor(Color.WHITE);

            Paint p = new Paint();
            // smooths
            p.setAntiAlias(true);
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(mCircleStroke);
            //p.setStrokeWidth(size - (size * 0.2f));
            Path path = new Path();
            //path.addCircle(mCenterX, mCenterY, ((height/2) - 5), Path.Direction.CW);
            //canvas.drawPath(path, p);
//            Typeface mTfRobo = Typeface.createFromAsset(mContext.getAssets(),
//                    "fonts/roboto_regular.ttf");
            RectF rectF = new RectF((width / 2) - (size - (mCircleStroke/2)), (height / 2) - (size - (mCircleStroke/2)), (width / 2) + (size - (mCircleStroke/2)), (height / 2) + (size - (mCircleStroke/2)));
            //canvas.drawRect(rectF, p);
            // demo
            if (graphData != null) {
                float[] data = new float[graphData.size()];
                for (int i = 0; i < graphData.size(); i++) {
                    data[i] = graphData.get(i).getValue();
                }

                // get sum
                float sum = 0.0f;
                for (float datum : data) sum += datum;

                // draw data
                List<Float> anglesStartList = new ArrayList<Float>();
                List<Float> anglesEndList = new ArrayList<Float>();
                List<Float> procentList = new ArrayList<Float>();
                List<PointF> points = new ArrayList<PointF>();
                float startAngle = 0.0f;
                for (int i = 0; i < graphData.size(); i++) {
                    // get random color
                    Random rnd = new Random();
                    //p.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    p.setColor(graphData.get(i).getColor());
                    // calc
                    float procent = (100 * data[i]) / sum;
                    procentList.add(procent);
                    float angleEnd = (360 * procent) / 100;
                    anglesStartList.add(startAngle);

                    float steep = 0;
                    steep = angleEnd;
                    angleEnd = angleEnd + startAngle;
                    anglesEndList.add(angleEnd);
                    // draw

                    canvas.drawArc(rectF, startAngle, steep, false, p);
                    startAngle = angleEnd;
                }

                float r = (float) ((width + height) / 5.5);
                for (int i = 0; i < anglesStartList.size(); i++){
                    float pointAngle = (anglesStartList.get(i) + ((anglesEndList.get(i) - anglesStartList.get(i))/2));
                    float x =  (float)((width/2) + r* Math.cos(Math.toRadians(pointAngle)));
                    float y =  (float)((height/2) + r* Math.sin(Math.toRadians(pointAngle)));
                    points.add(new PointF(x, y));
                }
                int procentTextSize = (int) (mCircleStroke/1.5);
                int headerTextSize = (int)(mCircleStroke/1.5);
                Paint mTextPocentPaint = new Paint();
                mTextPocentPaint.setColor(mContext.getResources().getColor(R.color.dashboard_round_text_color));
                mTextPocentPaint.setTextSize(procentTextSize);
                mTextPocentPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                mTextPocentPaint.setTextAlign(Paint.Align.CENTER);


                for (int i = 0; i < procentList.size(); i++){
                    Paint headerTextPaint = new Paint();
                    headerTextPaint.setColor(graphData.get(i).getColor());
                    headerTextPaint.setTextSize(headerTextSize);
                    headerTextPaint.setTextAlign(Paint.Align.CENTER);
                    //String mPocentStr = new String("%d",String.valueOf(procentList.get(i)) + " %");
                    String mPocentStr = String.format("%.2f", procentList.get(i)) + " %";
                    canvas.drawText(mPocentStr, points.get(i).x, points.get(i).y, mTextPocentPaint);
                    canvas.drawText(graphData.get(i).getName(), points.get(i).x, (float) (points.get(i).y + (headerTextSize * 1.2)), mTextPocentPaint);
//                    Categories categories = Categories.getCategoriesByID(DatabaseHelper.getDbHelper(mContext), graphData.get(i).getCategoryId());
//                    canvas.drawText(categories.getIconPath(), points.get(i).x, points.get(i).y + (procentTextSize + (height/150)), headerTextPaint );
                }

                Paint textPaint = new Paint();
                if (centerValue > 10000){
                    mTextSize = mTextSize - 2;
                }
                if (centerValue > 100000){
                    mTextSize = mTextSize - 2;
                }
                if (centerValue > 1000000){
                    mTextSize = mTextSize - 2;
                }
                if (centerValue > 10000000){
                    mTextSize = mTextSize - 2;
                }
                textPaint.setTextSize(mTextSize);
                textPaint.setTextAlign(Paint.Align.CENTER);
                String tmp = String.valueOf((int) centerValue);
                canvas.drawText(tmp, width / 2, ((height / 2) + (mTextSize / 2)), textPaint);

                Paint textPaint2 = new Paint();

                textPaint2.setColor(getResources().getColor(R.color.dashboard_outgoing_header_color));
                textPaint2.setTextSize(mTextSize2);
                textPaint2.setTextAlign(Paint.Align.CENTER);
//                switch (transactionType) {
//                    case AppConstants.CATEGORY_TYPE_OUTGOING:
//                        canvas.drawText(getResources().getString(R.string.dashboard_balance_outgoing_header), width / 2, ((height / 2) + mTextSize2 + (height / 50)), textPaint2);
//                        break;
//                    case AppConstants.CATEGORY_TYPE_INCOME:
//                        canvas.drawText(getResources().getString(R.string.dashboard_balance_incoming_header), width / 2, ((height / 2) + mTextSize2 + (height / 50)), textPaint2);
//                        break;
//                }

                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}

