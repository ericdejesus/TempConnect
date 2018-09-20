package com.example.edejesus1097.miniproj;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Graphing extends AppCompatActivity {
    String storeVals[];       // stores time and temperature value from single row in CSV
    String storeTitle[];      // store first line of CSV to distinguish between temperature and humidity
    String storeX;            // store string in first row and column of CSV (always "time")
    String timeOrHumid;       // store string in first row and second column of CSV ("time" or "humidity")
    Double time;              // time value pulled from a single row in the CSV
    Double temp_humid;        // temperature or humidity value pulled from a single row in the CSV
    GraphView graph;          // graph object for graphing

    private LineGraphSeries<DataPoint> series1; // stores all of the data from CSV for graphing

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}









