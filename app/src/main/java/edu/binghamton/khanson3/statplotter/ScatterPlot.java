package edu.binghamton.khanson3.statplotter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ScatterPlot extends AppCompatActivity implements View.OnTouchListener {
    private MyGraph myGraph;
    private static List<List<Float>> dataPoints;
    private static int height;
    private static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_plot);

        dataPoints = (List<List<Float>>) getIntent().getSerializableExtra("DATA_POINTS");

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout graphLinearLayout = findViewById(R.id.graphLinearLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        myGraph = new MyGraph(getApplicationContext());
        graphLinearLayout.addView(myGraph);
        //myGraph.plotPoints(dataPoints);

        myGraph.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view instanceof SurfaceView) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            EditText xCoordinateEditText = findViewById(R.id.xCoordinateEditText);
            EditText yCoordinateEditText = findViewById(R.id.yCoordinateEditText);

            List<Float> closestPoint = myGraph.closestPoint(x, y);

            xCoordinateEditText.setText("" + closestPoint.get(0));
            yCoordinateEditText.setText("" + closestPoint.get(1));

            return true;
        }

        return false;
    }

    public static void createPlot(List<List<Float>> dataPoints) {

    }

    public static List<List<Float>> getDataPoints() {
        return dataPoints;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }
}
