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

        GraphView graph = (GraphView) findViewById(R.id.graph);

        // initialize reader to read CSV
        BufferedReader reader = null;
        String title;
        try {
            Uri uri;
            ContentResolver cr = getContentResolver();
            InputStream is = cr.openInputStream(uri);
            reader = new BufferedReader(new InputStreamReader(is)); // read from CSV file
            title = reader.readLine();  //skip first line of file
            storeTitle = title.split(",");
            storeX = storeTitle[0];
            timeOrHumid = storeTitle[1];

            String comma; // used to separate time and temp by comma in file

            ArrayList<DataPoint> dataPoints = new ArrayList<>(); // create an ArrayList to store all time and temp values

            // while loop to separate time and temp values in CSV and transfer to ArrayList
            while ((comma = reader.readLine()) != null) {
                storeVals = comma.split(",");
                time = Double.parseDouble(storeVals[0]);
                temp_humid = Double.parseDouble(storeVals[1]);

                DataPoint dp = new DataPoint(time, temp_humid);
                dataPoints.add(dp);
            }

            // transfer data from ArrayList to an Array
            DataPoint[] allData = new DataPoint[dataPoints.size()];
            for (int i = 0; i < dataPoints.size(); i++) {
                allData[i] = dataPoints.get(i);
            }

            // retrieve data from Array for graphing
            LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(allData);
            graph.addSeries(series1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Customize numbers for X and Y axes for graph
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show time for x values
                    return super.formatLabel(value, isValueX) + ":00";
                } else {
                    // show normal integers for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        if (timeOrHumid == "temperature") {
            // set title of graph
            graph.setTitle("Temperature Over The Next Day");

            // set the X-axis of bounds manually
            graph.getViewport().setXAxisBoundsManual(true);

            // set min value on X-axis of to 0
            graph.getViewport().setMinX(0);

            // set max value on X-axis to 24
            graph.getViewport().setMaxX(24);

            // set X-axis label
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");

            // set Y-axis label
            graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (°F)");

            // enables horizontal zooming and scrolling
            graph.getViewport().setScalable(true);

            // enables horizontal scrolling
            graph.getViewport().setScrollable(true);

            // enables horizontal and vertical zooming and scrolling
            graph.getViewport().setScalableY(true);

            // enables vertical scrolling
            graph.getViewport().setScrollableY(true);
        }

        if (timeOrHumid == "humidity") {
            // set title of graph
            graph.setTitle("Humidity Over The Next Day");

            // set the X-axis bounds manually
            graph.getViewport().setXAxisBoundsManual(true);

            // set the Y-axis bounds manually
            graph.getViewport().setYAxisBoundsManual(true);

            // set min value on X-axis to 0
            graph.getViewport().setMinX(0);

            // set max value on X-axis to 24
            graph.getViewport().setMaxX(24);

            // set min value on Y-axis to 0
            graph.getViewport().setMinY(0);

            // set max value on Y-axis to 100
            graph.getViewport().setMaxY(100);

            // set X-axis label
            graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");

            // set Y-axis label
            graph.getGridLabelRenderer().setVerticalAxisTitle("Humidity (%)");

            // enables horizontal zooming and scrolling
            graph.getViewport().setScalable(true);

            // enables horizontal scrolling
            graph.getViewport().setScrollable(true);

            // enables horizontal and vertical zooming and scrolling
            graph.getViewport().setScalableY(true);

            // enables vertical scrolling
            graph.getViewport().setScrollableY(true);
        }
    }
}









