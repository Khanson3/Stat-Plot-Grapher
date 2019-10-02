package edu.binghamton.khanson3.statplotter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner plotTypeSpinner = findViewById(R.id.plotTypeSpinner);
        ArrayAdapter<CharSequence> plotTypes = ArrayAdapter.createFromResource(this, R.array.plot_types, android.R.layout.simple_spinner_item);
        plotTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plotTypeSpinner.setAdapter(plotTypes);

        plotTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(15f);
                ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText xAddTextView = findViewById(R.id.xAddTextView);
        final EditText yAddTextView = findViewById(R.id.yAddTextView);

        nextEditText(xAddTextView, yAddTextView);
        editTextUnfocusAndHideKeyboard(yAddTextView);

        final EditText xDeleteTextView = findViewById(R.id.xDeleteTextView);
        final EditText yDeleteTextView = findViewById(R.id.yDeleteTextView);

        nextEditText(xDeleteTextView, yDeleteTextView);
        editTextUnfocusAndHideKeyboard(yDeleteTextView);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);

        final Snackbar numberFormatExceptionSnackbar = Snackbar.make(constraintLayout, "NumberFormatException: Data point too large", Snackbar.LENGTH_LONG);
        final Snackbar incompleteDataPointSnackbar = Snackbar.make(constraintLayout, "Data point is incomplete", Snackbar.LENGTH_LONG);
        final Snackbar dataPointAddedSnackbar = Snackbar.make(constraintLayout, "Data point has been added", Snackbar.LENGTH_LONG);
        final Snackbar dataPointDeletedSnackbar = Snackbar.make(constraintLayout, "Data point has been deleted", Snackbar.LENGTH_LONG);
        final Snackbar dataPointNotFoundSnackbar = Snackbar.make(constraintLayout, "Data point not found", Snackbar.LENGTH_LONG);
        final Snackbar notANumberSnackbar = Snackbar.make(constraintLayout, "Invalid Data Point: Inputted data point is not a number", Snackbar.LENGTH_LONG);
        final Snackbar noDataPointsSnackbar = Snackbar.make(constraintLayout, "Data points must be added in order to plot", Snackbar.LENGTH_LONG);

        final List<List<Float>> dataPoints = new ArrayList<>();

        final TextView dataPointsTextView = findViewById(R.id.dataPointsTextView);

        Button addDataPointButton = findViewById(R.id.addDataPointButton);
        Button deleteDataPointButton = findViewById(R.id.deleteDataPointButton);

        addDataPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dataPoints.add(new ArrayList<>(Arrays.asList(Float.parseFloat(xAddTextView.getText().toString()), Float.parseFloat(yAddTextView.getText().toString()))));
                    dataPointsTextView.append("(" + Float.parseFloat(xAddTextView.getText().toString()) + ", " + Float.parseFloat(yAddTextView.getText().toString()) + ")\n");

                    dataPointAddedSnackbar.show();

                    xAddTextView.setText("");
                    yAddTextView.setText("");
                } catch(NumberFormatException e) {
                    if(xAddTextView.getText().toString().equals("") || yAddTextView.getText().toString().equals(""))
                        incompleteDataPointSnackbar.show();
                    else if(xAddTextView.getText().toString().equals(".") || xAddTextView.getText().toString().equals("-") || xAddTextView.getText().toString().equals("-.") || yAddTextView.getText().toString().equals(".") || yAddTextView.getText().toString().equals("-") || yAddTextView.getText().toString().equals("-."))
                        notANumberSnackbar.show();
                    else
                        numberFormatExceptionSnackbar.show();
                }
            }
        });

        deleteDataPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    List<Float> dataPoint = new ArrayList<>(Arrays.asList(Float.parseFloat(xDeleteTextView.getText().toString()), Float.parseFloat(yDeleteTextView.getText().toString())));
                        if(dataPoints.contains(dataPoint)) {
                            dataPoints.remove(dataPoint);
                            dataPointsTextView.setText("");

                            for(List<Float> elem : dataPoints) {
                                dataPointsTextView.append("(" + elem.get(0).toString() + ", " + elem.get(1).toString() + ")\n");
                            }

                            dataPointDeletedSnackbar.show();

                            xDeleteTextView.setText("");
                            yDeleteTextView.setText("");
                        } else if(xDeleteTextView.getText().toString().equals(".") || xDeleteTextView.getText().toString().equals("-") || xDeleteTextView.getText().toString().equals("-.") || yDeleteTextView.getText().toString().equals(".") || yDeleteTextView.getText().toString().equals("-") || yDeleteTextView.getText().toString().equals("-."))
                            notANumberSnackbar.show();
                        else
                            dataPointNotFoundSnackbar.show();
                } catch(NumberFormatException e) {
                    if(xDeleteTextView.getText().toString().equals("") || yDeleteTextView.getText().toString().equals(""))
                        incompleteDataPointSnackbar.show();
                    else
                        numberFormatExceptionSnackbar.show();
                }
            }
        });

        Button plotButton = findViewById(R.id.plotButton);

        plotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataPoints.size() == 0) {
                    noDataPointsSnackbar.show();
                    return;
                }

                switch(plotTypeSpinner.getSelectedItem().toString()) {
                    case "Scatter Plot":
                        Intent scatterPlotIntent = new Intent(getApplicationContext(), ScatterPlot.class);
                        scatterPlotIntent.putExtra("DATA_POINTS", (Serializable) dataPoints);
                        startActivity(scatterPlotIntent);
                        break;
                    case "Box-and-Whisker Plot":
                        Intent boxAndWhiskerPlotIntent = new Intent(getApplicationContext(), BoxAndWhiskerPlot.class);
                        boxAndWhiskerPlotIntent.putExtra("DATA_POINTS", (Serializable) dataPoints);
                        startActivity(boxAndWhiskerPlotIntent);
                        break;
                }
            }
        });
    }

    public static void nextEditText(EditText e1, final EditText e2) {
        e1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE
                        || i == EditorInfo.IME_ACTION_GO
                        || i == EditorInfo.IME_ACTION_NEXT
                        || i == EditorInfo.IME_ACTION_SEARCH) {
                    e2.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    public static void editTextUnfocusAndHideKeyboard(final EditText e) {
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE
                        || i == EditorInfo.IME_ACTION_GO
                        || i == EditorInfo.IME_ACTION_NEXT
                        || i == EditorInfo.IME_ACTION_SEARCH) {
                    editTextHideKeyboard(e);
                    e.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }

    public static void editTextHideKeyboard(EditText e) {
        InputMethodManager imm = (InputMethodManager) e.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
    }
}
