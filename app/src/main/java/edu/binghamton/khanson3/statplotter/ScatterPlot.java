package edu.binghamton.khanson3.statplotter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class ScatterPlot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_plot);

        List<List<Double>> dataPoints = (List<List<Double>>) getIntent().getSerializableExtra("DATA_POINTS");
    }
}
