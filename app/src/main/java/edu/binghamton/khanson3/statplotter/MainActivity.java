package edu.binghamton.khanson3.statplotter;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner plotTypeSpinner = findViewById(R.id.plotTypeSpinner);
        ArrayAdapter<CharSequence> plotTypes = ArrayAdapter.createFromResource(this, R.array.plot_types, android.R.layout.simple_spinner_item);
        plotTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plotTypeSpinner.setAdapter(plotTypes);

        plotTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(15f);
                //((TextView) adapterView.getChildAt(0)).setTypeface(Typeface.DEFAULT_BOLD);
                ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
