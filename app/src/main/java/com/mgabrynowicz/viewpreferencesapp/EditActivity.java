package com.mgabrynowicz.viewpreferencesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {


    public static final String BUTTON_PREFERENCES = "button preferences";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String COLOR = "color";
    public static final String FONT_SIZE = "font size";
    public int fontSize;


    EditText editTextHeight;
    EditText editTextWidth;
    Spinner colorSpinner;
    SeekBar seekBar;
    Button saveButton;
    TextView fontSizeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextHeight = (EditText) findViewById(R.id.editText_height);
        editTextWidth = (EditText) findViewById(R.id.editText_width);
        colorSpinner = (Spinner) findViewById(R.id.spinner);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        saveButton = (Button) findViewById(R.id.button_save);
        fontSizeTextView = (TextView) findViewById(R.id.font_size_textview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.colors_array));
        colorSpinner.setAdapter(adapter);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(100);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                if (b) {

                    if (progress >= 0 && progress <= seekBar.getMax()) {

                        fontSize = progress;
                        fontSizeTextView.setText(String.valueOf(fontSize));

                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                savePreferences();
                returnToMainActivity();

            }
        });





    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void savePreferences() {

        SharedPreferences sharedPreferences = getSharedPreferences(BUTTON_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String widthString = editTextWidth.getText().toString();
        String heightString = editTextHeight.getText().toString();
        String buttonColor = colorSpinner.getSelectedItem().toString();

        int buttonWidth = 0;
        int buttonHeight = 0;

        try {
            buttonWidth = Integer.parseInt(widthString);
            buttonHeight = Integer.parseInt(heightString);
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid width or height!", Toast.LENGTH_SHORT).show();
            return;
        }


        editor.putInt(WIDTH, buttonWidth);
        editor.putInt(HEIGHT, buttonHeight);
        editor.putInt(FONT_SIZE, fontSize);
        editor.putString(COLOR, buttonColor);
        editor.commit();


    }

    private void loadPreferences() {

        int width, heigth, fontSize, colorInt;
        String color;
        SharedPreferences sharedPreferences = getSharedPreferences(BUTTON_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        width = sharedPreferences.getInt(WIDTH, -1);
        heigth = sharedPreferences.getInt(HEIGHT, -1);
        fontSize = sharedPreferences.getInt(FONT_SIZE, -1);
        color = sharedPreferences.getString(COLOR, "");

        editTextHeight.setText(String.valueOf(heigth));
        editTextWidth.setText(String.valueOf(width));
        seekBar.setProgress(fontSize);
        ArrayAdapter<String> adapter =  (ArrayAdapter<String>) colorSpinner.getAdapter();
        colorSpinner.setSelection(setSpinner(color, adapter));



    }


    public int setSpinner(String spinnerValue, ArrayAdapter spinnerAdapter) {
        int spinnerPosition = 0;

        if (spinnerValue != null) {
            spinnerPosition = spinnerAdapter.getPosition(spinnerValue);
            return spinnerPosition;

        }
        return spinnerPosition;
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class );
        setResult(1, intent);
        finish();


    }
}
