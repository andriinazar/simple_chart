package com.andrii.nazar.simplechart.views;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.core.content.ContextCompat;

import com.andrii.nazar.simplechart.R;
import com.andrii.nazar.simplechart.constants.AppConstants;
import com.andrii.nazar.simplechart.models.GraphData;
import com.andrii.nazar.simplechart.utils.ChartController;
import com.andrii.nazar.simplechart.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class GraphChartView extends SurfaceView implements SurfaceHolder.Callback {
    // constants
    public static final int TYPE_CHART = 1;

    //variables
    private Thread mThread;
    private SurfaceHolder mHolder;
    private Context mContext;
    private ChartController mChartController;
    private int mType = TYPE_CHART;
    private List<GraphData> mGraphData;
    private boolean mSurfaceCreated = false;


    public GraphChartView(Context context) {
        super(context);
        mContext = context;
        mGraphData = new ArrayList<GraphData>();
    }

    public GraphChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mGraphData = new ArrayList<GraphData>();
    }

    public GraphChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mGraphData = new ArrayList<GraphData>();
    }

    public void start(){
        mHolder = this.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
    }

    public void stop(){

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mChartController = new ChartController(holder, width, height);
        mChartController.setGraphBackground(ContextCompat.getColor(mContext, R.color.chart_background));
        mChartController.setYGreed(true);
        mChartController.setXGreedCount(8);
        mChartController.setYGreedCount(6);
        mSurfaceCreated = true;
        beginDraw();
        //mChartController.setGraphBackground(Color.parseColor("#" + Integer.toHexString(mContext.getResources().getColor(R.color.colorPrimaryDark))));
        //setGraphData(null);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void beginDraw(){
        if( mSurfaceCreated ){
            if( mGraphData != null && mGraphData.size() > 0 ){
                setGraphData(mGraphData);
            }else{
                drawEmptyGraph();
            }
        }
    }

    public void setData(final List<GraphData> graphData){
        mGraphData = graphData;
        beginDraw();
    }

    public void setDataInThread(final List<GraphData> graphData){
        mThread = new Thread() {
            @Override
            public void run() {
                while (true){
                    if (mChartController != null){
                        if (graphData != null) {
                            //mChartController.clearCanvas();
                            if (graphData.size() > 0) {
                                setGraphData(graphData);
                            }else{
                                drawEmptyGraph();
                            }
                        }
                        break;
                    }
                }
            }
        };
        mThread.start();
    }

    public void setGraphData(List<GraphData> graphData)
    {
        setData(graphData, 1);
    }

    public void setData(List<GraphData> data, int graphType){

        if (data != null) {
            float maxY = 0;
            float minY = 0;

            long maxX = 0;
            long minX = 0;

            if (data.size() > 1) {
                List<Float> maxTmpY = new ArrayList<>();
                List<Float> minTmpY = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getYData().size() > 1) {
                        maxTmpY.add(Collections.max(data.get(i).getYData()));
                        minTmpY.add(Collections.min(data.get(i).getYData()));
                    } else {
                        maxTmpY.add(data.get(i).getYData().get(0));
                        minTmpY.add((float) 0);
                    }
                }

                List<Long> maxTmpX = new ArrayList<>();
                List<Long> minTmpX = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getXData().size() > 1) {
                        maxTmpX.add(Collections.max(data.get(i).getXData()));
                        minTmpX.add(Collections.min(data.get(i).getXData()));
                    } else {
                        maxTmpX.add(data.get(i).getXData().get(0));
                        minTmpX.add(data.get(i).getXData().get(0));
                    }
                }

                maxY = Collections.max(maxTmpY);
                minY = Collections.min(minTmpY);

                maxX = Collections.max(maxTmpX);
                minX = Collections.min(minTmpX);

            } else {
                maxY = Collections.max(data.get(0).getYData());
                minY = Collections.min(data.get(0).getYData());

                maxX = Collections.max(data.get(0).getXData());
                minX = Collections.min(data.get(0).getXData());
            }

            String mTmpMaxS = Util.getDayFromTimestamp(maxX);
            String mTmpMinS = Util.getDayFromTimestamp(minX);
            /*for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getYData().size() == 1) {
                    data.get(i).getYData().add(data.get(i).getYData().get(0));
                    data.get(i).getXData().add(maxX);
                }
            }*/
            if (mChartController == null)
                return;
            int width = mChartController.width;
            int height = mChartController.height;
            width = width - 20;
            float devH = maxY - minY;
            //float tmp1  = height/devH;
            height = height - AppConstants.GRAPH_PADDING;
            float coefY_1 = (float) (mChartController.getYGreedCount() ) / (float) ((mChartController.getYGreedCount() + 1));
            float coefY_2 = ((float) (mChartController.getYGreedCount() + 1)) / (float) (mChartController.getYGreedCount());
            //float tmp1 = (float) ((height*0.9)/(maxY * 1.1));
            float tmp1 = (float) ((height * coefY_1) / (maxY * coefY_2));

            float devW = maxX - minX;
            //float devW = maxX;

            float coefX_1 = (float) (mChartController.getXGreedCount() * 10) / (float) ((mChartController.getXGreedCount() + 1) * 10);
            float coefX_2 = ((float) (mChartController.getXGreedCount() + 1) * 10) / (float) (mChartController.getXGreedCount() * 10);

            List<ChartController.Line> mLines = new ArrayList<ChartController.Line>();
            for (GraphData graphData : data) {
                ChartController.Line tmpLine = new ChartController.Line();
                tmpLine.setColor(graphData.getLineColor());
                tmpLine.setDrawPoint(graphData.isDrawPoint());
                tmpLine.setSplice(graphData.isSplice());
                tmpLine.setPointRadius(graphData.getPointRadius());
                tmpLine.setPointColor(graphData.getPintColor());
                tmpLine.setPointShadingColor(graphData.getPointShadingColor());
                tmpLine.setStroke(graphData.getLineStroke());
                mLines.add(tmpLine);
//                switch (i) {
//                    case 0:
//                        mTmpLine.setColor(mContext.getResources().getColor(R.color.dashboard_incoming_value_color));
//                        mTmpLine.setDrawPoint(false);
//                        mTmpLine.setSplice(false);
//                        mTmpLine.setPointRadius(5);
//                        mTmpLine.setPointColor(mContext.getResources().getColor(R.color.dashboard_graph_point_color));
//                        mTmpLine.setPointShadingColor(mContext.getResources().getColor(R.color.dashboard_graph_point_shading_color));
//                        mTmpLine.setStroke(2);
//                        mLines.add(tmpLine);
//                        break;
//                    case 1:
//                        mTmpLine.setColor(mContext.getResources().getColor(R.color.dashboard_outgoing_value_color));
//                        mTmpLine.setDrawPoint(false);
//                        mTmpLine.setSplice(false);
//                        mTmpLine.setPointRadius(5);
//                        mTmpLine.setPointColor(mContext.getResources().getColor(R.color.dashboard_graph_point_color));
//                        mTmpLine.setPointShadingColor(mContext.getResources().getColor(R.color.dashboard_graph_point_shading_color));
//                        mTmpLine.setStroke(2);
//                        mLines.add(mTmpLine);
//
//
//                }
            }

            List<Integer> mTmpXForGraph = new ArrayList<>();

            for (int j = 0; j < data.size(); j++) {
                int ySize = data.get(j).getYData().size();
                int[] coordinateY = null;
                if (ySize > 1) {
                    coordinateY = new int[ySize];
                    for (int i = 0; i < coordinateY.length; i++) {
                        coordinateY[i] = (int) ((((height - (AppConstants.GRAPH_PADDING/2 - (AppConstants.GRAPH_PADDING/4))) - ((data.get(j).getYData().get(i))) * tmp1)));
                    }
                }
                if (ySize == 1){
                    coordinateY = new int[2];
                    coordinateY[0] = (int) ((((height - (AppConstants.GRAPH_PADDING/2 - (AppConstants.GRAPH_PADDING/4))) - ((data.get(j).getYData().get(0))) * tmp1)));
                    coordinateY[1] = (int) ((((height - (AppConstants.GRAPH_PADDING/2 - (AppConstants.GRAPH_PADDING/4))) - ((data.get(j).getYData().get(0))) * tmp1)));

                }

                //float tmp2 = (float) (width*0.91/(devW*1.1));
                //float tmp2 = (float) ((float) (width) * coefX_1 / ((float) devW * coefX_2));
                //float tmp2 = (float) ((float) (width) / (float) (devW) );
                //float tmp2 = (float) ((float) (width - AppConstants.GRAPH_PADDING) / (float) (maxX - minX) );
                float tmp2 = (float) ((float) (width - AppConstants.GRAPH_PADDING) / (float) (Collections.max(data.get(j).getXData()) + Collections.min(data.get(j).getXData())));
                int xSize = data.get(j).getXData().size();
                int[] coordinateX = null;
                if (xSize > 1) {
                    coordinateX = new int[xSize];
                    for (int i = 0; i < coordinateX.length; i++) {

                        //coordinateX[i] = (int) (((data.get(j).getXData().get(i) - Collections.min(data.get(j).getXData())) * tmp2 ) + AppConstants.GRAPH_PADDING);
                        coordinateX[i] = (int) (((data.get(j).getXData().get(i) - Collections.min(data.get(j).getXData())) * tmp2) + AppConstants.GRAPH_PADDING);
                        //coordinateX[i] = (int) ((((data.get(j).getXData().get(i) * width) / devW) ) + AppConstants.GRAPH_PADDING);
                        mTmpXForGraph.add(coordinateX[i]);
                    }
                }
                if (xSize == 1){
                    coordinateX = new int[2];
                    coordinateX[0] = (int) (AppConstants.GRAPH_PADDING);
                    coordinateX[1] = (int) (width);
                    for (int x : coordinateX) {
                        mTmpXForGraph.add(x);
                    }

                }

                for (int i = 0; i < Objects.requireNonNull(coordinateY).length - 1; i++) {
                    assert coordinateX != null;
                    mLines.get(j).addLine(coordinateX[i], coordinateY[i], coordinateX[i + 1], coordinateY[i + 1]);
                }
            }
            for (int i = 0; i < mLines.size(); i++) {
                mChartController.addLine(mLines.get(i));
            }

//            Typeface mTfRobo = Typeface.createFromAsset(mContext.getAssets(),
//                    "fonts/roboto_regular.ttf");
            // init greed
            float tmpSteepCoordinateY = 0;
            float tmpSteepDataY = 0;
            tmpSteepCoordinateY = (float) ((height - AppConstants.GRAPH_PADDING ) / (float) mChartController.getYGreedCount());
            tmpSteepDataY = (float) ((maxY) / mChartController.getYGreedCount());
            float mSteepCoordinateY = tmpSteepCoordinateY;
            float mSteepDataY = tmpSteepDataY;
            List<GreedData> gridY = new ArrayList<GreedData>();
            gridY.add(new GreedData(height, 0));
            for (int i = 0; i < mChartController.getYGreedCount(); i++) {
                gridY.add(new GreedData(height - mSteepCoordinateY, mSteepDataY));
                mSteepCoordinateY = mSteepCoordinateY + tmpSteepCoordinateY;
                mSteepDataY = mSteepDataY + tmpSteepDataY;
            }
            ChartController.SurfaceText mYText = new ChartController.SurfaceText(mChartController.height, mChartController.width);

            mYText.setTextYSize(16);
//            Typeface mYTypeFace = Typeface.create(mTfRobo, Typeface.BOLD);
//            mYText.setTextYFont(mYTypeFace);
            mYText.seTextYColor(mContext.getResources().getColor(R.color.dashboard_graph_y_text_color));
            for (int i = 0; i < gridY.size(); i++) {
                mYText.addYText(gridY.get(i));
            }

            ChartController.Line mYLine = new ChartController.Line();
            mYLine.setStroke(2);
            mYLine.setColor(mContext.getResources().getColor(R.color.dashboard_graph_greed_color));
            for (int i = 0; i < gridY.size(); i++) {
                mYLine.addLine(AppConstants.GRAPH_PADDING, (int) (gridY.get(i).getCoordinate()), width, (int) gridY.get(i).getCoordinate());
            }

            mChartController.addGreedYLine(mYLine);
            mChartController.addTextY(mYText);

            float tmpSteepCoordinateX = 0;
            float tmpSteepDataX = 0;

            float mStartX = 0;
            float mEndX = 0;



            //TODO X lines text need be fixed
            mStartX = Collections.min(mTmpXForGraph);
            mEndX = Collections.max(mTmpXForGraph);

            if (mTmpXForGraph.size() > 2) {
                tmpSteepCoordinateX = (int) (((mEndX - mStartX)) / (mChartController.getXGreedCount()));
                tmpSteepDataX = (float) (((maxX - minX)) / (float) (mChartController.getXGreedCount()));
            }else {
                tmpSteepCoordinateX = (int) (((mEndX)) / (mChartController.getXGreedCount()));
                tmpSteepDataX = (float) (((maxX - minX)) / (float) (mChartController.getXGreedCount()));
            }
            float mSteepCoordinateX = mStartX;
            float mSteepDataX = minX;
            tmpSteepCoordinateX = (float) ((width - AppConstants.GRAPH_PADDING ) / (float) mChartController.getXGreedCount());
            tmpSteepDataX = (float) ((maxY) / mChartController.getXGreedCount());
            List<GreedData> gridX = new ArrayList<GreedData>();
            gridX.add(new GreedData(AppConstants.GRAPH_PADDING, String.format("%.2f", 0f)));
            for (int i = 0; i < mChartController.getXGreedCount(); i++) {
                gridX.add(new GreedData(mSteepCoordinateX + mSteepCoordinateX, String.format("%.2f", mSteepDataX)));
                mSteepCoordinateX = mSteepCoordinateX + tmpSteepCoordinateX;
                mSteepDataX = mSteepDataX + tmpSteepDataX;
            }
//            List<GreedData> gridX = new ArrayList<GreedData>();
//            //gridX.add(new GreedData(coordinateX[0], minX));
//            for (int i = 0; i < mChartController.getXGreedCount() + 1; i++) {
//                //String mTmpXData = Util.getDayFromTimestamp(mSteepDataX);
//                gridX.add(new GreedData(mSteepCoordinateX, Util.getDayFromTimestamp((long) mSteepDataX)));
//                mSteepCoordinateX = mSteepCoordinateX + tmpSteepCoordinateX;
//                mSteepDataX = tmpSteepDataX + mSteepDataX;
//            }
            //ChartController.SurfaceText mXText = new ChartController.SurfaceText(mChartController.width, mChartController.height);
            ChartController.SurfaceText mXText = new ChartController.SurfaceText(mChartController.width, mChartController.height);
            mXText.setTextXSize(16);
            //Typeface mXTypeFace = Typeface.create(Typeface.SANS_SERIF,Typeface.ITALIC);
//            Typeface mXTypeFace = Typeface.create(mTfRobo, Typeface.BOLD);
//            mXText.setTextXFont(mXTypeFace);
            mXText.setTextXColor(mContext.getResources().getColor(R.color.dashboard_graph_y_text_color));
            for (int i = 0; i < gridX.size(); i++) {
                mXText.addXText(gridX.get(i));
            }

            ChartController.Line mXLine = new ChartController.Line();
            mXLine.setStroke(1);
            mXLine.setColor(mContext.getResources().getColor(R.color.dashboard_graph_greed_color));
            for (int i = 0; i < gridX.size(); i++) {
                mXLine.addLine((int) gridX.get(i).getCoordinate(), height - AppConstants.GRAPH_PADDING, (int) gridX.get(i).getCoordinate(), (int) gridY.get(0).getCoordinate());
                mXLine.addLine((int) gridX.get(i).getCoordinate(), height - AppConstants.GRAPH_PADDING, (int) gridX.get(i).getCoordinate(), (int) gridY.get(gridY.size() - 1).getCoordinate());
            }

            mChartController.addGreedXLine(mXLine);
//            mChartController.addTextX(mXText);

            drawGraph();
        }
    }

    private void drawEmptyGraph(){
            float maxY = 1;
            float minY = 0;

            long maxX = 1;
            long minX = 0;

            int width = mChartController.width;
            int height = mChartController.height;
            width = width - 20;
            float devH = maxY - minY;
            //float tmp1  = height/devH;
            height = height - AppConstants.GRAPH_PADDING;
            float coefY_1 = (float) (mChartController.getYGreedCount() * 10) / (float) ((mChartController.getYGreedCount() + 1) * 10);
            float coefY_2 = ((float) (mChartController.getYGreedCount() + 1) * 10) / (float) (mChartController.getYGreedCount() * 10);
            //float tmp1 = (float) ((height*0.9)/(maxY * 1.1));
            float tmp1 = (float) ((height * coefY_1) / (maxY * coefY_2));

            float devW = maxX - minX;
            //float devW = maxX;
            float coefX_1 = (float) (mChartController.getXGreedCount() * 10) / (float) ((mChartController.getXGreedCount() + 1) * 10);
            float coefX_2 = ((float) (mChartController.getXGreedCount() + 1) * 10) / (float) (mChartController.getXGreedCount() * 10);

//            Typeface mTfRobo = Typeface.createFromAsset(mContext.getAssets(),
//                    "fonts/roboto_regular.ttf");
            // init greed
            float tmpSteepCoordinateY = 0;
            float tmpSteepDataY = 0;
            tmpSteepCoordinateY = (float) ((height - AppConstants.GRAPH_PADDING) / (float) mChartController.getYGreedCount());
            tmpSteepDataY = (float) ((maxY) / mChartController.getYGreedCount());
            float mSteepCoordinateY = tmpSteepCoordinateY;
            float mSteepDataY = tmpSteepDataY;
            List<GreedData> gridY = new ArrayList<GreedData>();
            gridY.add(new GreedData(height, String.format("%.2f", 0f)));
            for (int i = 0; i < mChartController.getYGreedCount(); i++) {
                gridY.add(new GreedData(height - mSteepCoordinateY, String.format("%.2f", mSteepDataY)));
                mSteepCoordinateY = mSteepCoordinateY + tmpSteepCoordinateY;
                mSteepDataY = mSteepDataY + tmpSteepDataY;
            }
            ChartController.SurfaceText mYText = new ChartController.SurfaceText(mChartController.height, mChartController.width);

            mYText.setTextYSize(16);
            mYText.seTextYColor(mContext.getResources().getColor(R.color.dashboard_graph_y_text_color));
            for (int i = 0; i < gridY.size(); i++) {
                mYText.addYText(gridY.get(i));
            }

            ChartController.Line mYLine = new ChartController.Line();
            mYLine.setStroke(2);
            mYLine.setColor(mContext.getResources().getColor(R.color.dashboard_graph_greed_color));
            for (int i = 0; i < gridY.size(); i++) {
                mYLine.addLine(AppConstants.GRAPH_PADDING, (int) (gridY.get(i).getCoordinate()), width, (int) gridY.get(i).getCoordinate());
            }

            mChartController.addGreedYLine(mYLine);
            mChartController.addTextY(mYText);

            float tmpSteepCoordinateX = 0;
            float tmpSteepDataX = 0;

            tmpSteepCoordinateX = (float) (width - (AppConstants.GRAPH_PADDING + (AppConstants.GRAPH_PADDING  /2 ))) / (mChartController.getXGreedCount());
            tmpSteepDataX = (float) (maxX) / (float) (mChartController.getXGreedCount());
            float mSteepCoordinateX = tmpSteepCoordinateX;
            float mSteepDataX = minX;
            List<GreedData> gridX = new ArrayList<GreedData>();
            //gridX.add(new GreedData(coordinateX[0], minX));
            for (int i = 0; i < mChartController.getXGreedCount() + 1; i++) {
                //String mTmpXData = Util.getDayFromTimestamp(mSteepDataX);
                gridX.add(new GreedData(mSteepCoordinateX,  String.format("%.2f",mSteepDataX)));
                mSteepCoordinateX = mSteepCoordinateX + tmpSteepCoordinateX;
                mSteepDataX = tmpSteepDataX + mSteepDataX;
            }
            ChartController.SurfaceText mXText = new ChartController.SurfaceText(mChartController.width, mChartController.height);
            mXText.setTextXSize(16);
            mXText.setTextXColor(mContext.getResources().getColor(R.color.dashboard_graph_y_text_color));
            for (int i = 0; i < gridX.size(); i++) {
                mXText.addXText(gridX.get(i));
            }

            ChartController.Line mXLine = new ChartController.Line();
            mXLine.setStroke(1);
            mXLine.setColor(mContext.getResources().getColor(R.color.dashboard_graph_greed_color));
            for (int i = 0; i < gridX.size(); i++) {
                mXLine.addLine((int) gridX.get(i).getCoordinate(), height - AppConstants.GRAPH_PADDING, (int) gridX.get(i).getCoordinate(), (int) gridY.get(0).getCoordinate());
                mXLine.addLine((int) gridX.get(i).getCoordinate(), height - AppConstants.GRAPH_PADDING, (int) gridX.get(i).getCoordinate(), (int) gridY.get(gridY.size() - 1).getCoordinate());
            }
            mChartController.addGreedXLine(mXLine);
            mChartController.addTextX(mXText);
            drawGraph();
    }

    public class GreedData{
        private float mCoordinate;
        private float mHeader;
        private String mStringHeader;


        public GreedData(float coordinate, float header) {
            this.mCoordinate = coordinate;
            this.mHeader = header;
        }

        public GreedData(float coordinate, String header) {
            this.mCoordinate = coordinate;
            this.mStringHeader = header;
        }

        public float getCoordinate() {
            return mCoordinate;
        }

        public void setCoordinate(float coordinate) {
            this.mCoordinate = coordinate;
        }

        public float getHeader() {
            return mHeader;
        }

        public void setHeader(float headerY) {
            this.mHeader = headerY;
        }

        public String getStringHeader() {
            return mStringHeader;
        }

        public void setStringHeader(String stringHeader) {
            this.mStringHeader = stringHeader;
        }
    }


    public void drawGraph(){
        mChartController.render();
    }

    public SurfaceHolder getGraphHolder(){
        return mHolder;
    }
}
