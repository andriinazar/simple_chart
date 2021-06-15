package com.andrii.nazar.simplechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.andrii.nazar.simplechart.models.GraphData;
import com.andrii.nazar.simplechart.models.RoundGraphData;
import com.andrii.nazar.simplechart.views.GraphChartView;
import com.andrii.nazar.simplechart.views.RoundGraphChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GraphChartView _cvGraph;
    private RoundGraphChartView _rgcvCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setTestGraphData();
        setTestCircleData();
    }

    private void initUI() {
        _cvGraph = (GraphChartView) findViewById(R.id.cv_circle_graph);
        _rgcvCircle = (RoundGraphChartView) findViewById(R.id.rgcv_circle_graph);
        _cvGraph.start();
        _rgcvCircle.start();
    }

    private void setTestGraphData() {
        ArrayList<GraphData> graphsData = new ArrayList<>();
        GraphData graph1 = new GraphData();
        graph1.setLineColor(Color.GREEN);
        graph1.addPoint(0l,0f);
        graph1.addPoint(1l,5f);
        graph1.addPoint(3l,7f);
        graph1.addPoint(6l,9f);
        graph1.addPoint(7l,11f);
        graph1.addPoint(12l,12f);
        graph1.addPoint(13l,12f);

        GraphData graph2 = new GraphData();
        graph2.setLineColor(Color.BLUE);
        graph2.setSplice(true);
        graph2.addPoint(0l,3f);
        graph2.addPoint(1l,5f);
        graph2.addPoint(2l,7f);
        graph2.addPoint(3l,2f);
        graph2.addPoint(5l,0f);
        graph2.addPoint(10l,7f);
        graph2.addPoint(12l,12f);

        GraphData graph3 = new GraphData();
        graph3.setLineColor(Color.RED);
        graph3.setSplice(false);
        graph3.addPoint(5l,0f);
        graph3.addPoint(10l,5f);
        graph3.addPoint(15l,7f);
        graph3.addPoint(20l,2f);
        graph3.addPoint(25l,1f);
        graph3.addPoint(30l,0f);
        graph3.addPoint(36l,11f);

        graphsData.add(graph1);
        graphsData.add(graph2);
        graphsData.add(graph3);

        _cvGraph.setData(graphsData);
    }

    private void setTestCircleData() {
        ArrayList<RoundGraphData> testData = new ArrayList<>();
        testData.add(new RoundGraphData("Test1", 100, Color.RED));
        testData.add(new RoundGraphData("Test2", 40, Color.GRAY));
        testData.add(new RoundGraphData("Test3", 124, Color.GREEN));
        testData.add(new RoundGraphData("Test4", 120, Color.BLUE));
        testData.add(new RoundGraphData("Test5", 70, Color.YELLOW));
        testData.add(new RoundGraphData("Test6", 90, Color.DKGRAY));
        testData.add(new RoundGraphData("Test7", 50, Color.BLACK));
        _rgcvCircle.setData(testData, 1000);
    }

}