package edu.binghamton.khanson3.statplotter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

        EditText xDeleteTextView = findViewById(R.id.xDeleteTextView);
        EditText yDeleteTextView = findViewById(R.id.yDeleteTextView);

        nextEditText(xDeleteTextView, yDeleteTextView);
        editTextUnfocusAndHideKeyboard(yDeleteTextView);
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
