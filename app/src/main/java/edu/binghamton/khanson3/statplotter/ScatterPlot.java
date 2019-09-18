package edu.binghamton.khanson3.statplotter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class ScatterPlot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_plot);

        List<List<Double>> dataPoints = (List<List<Double>>) getIntent().getSerializableExtra("DATA_POINTS");

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout graphLinearLayout = findViewById(R.id.graphLinearLayout);
    }
}
