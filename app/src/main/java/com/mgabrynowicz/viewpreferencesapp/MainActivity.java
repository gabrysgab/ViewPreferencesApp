package com.mgabrynowicz.viewpreferencesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String BUTTON_PREFERENCES = "button preferences";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String COLOR = "color";
    public static final String FONT_SIZE = "font size";
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOne = (Button) findViewById(R.id.button_one);
        buttonTwo = (Button) findViewById(R.id.button_two);
        buttonThree = (Button) findViewById(R.id.button_three);
        buttonFour = (Button) findViewById(R.id.button_four);
    }

    @Override
    protected void onResume() {
        getButtonPreferences();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit) {

            startEditActivity();


        } else if (id == R.id.action_clear) {

            clearPreferences();


        }

        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(BUTTON_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    private void startEditActivity() {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        startActivityForResult(intent, 1);
    }


    private void setButtonStyle(Button buttonToSet, int width, int height, int fontSize, int color) {


        buttonToSet.setWidth(width);
        buttonToSet.setHeight(height);
        buttonToSet.setTextSize(fontSize);
        buttonToSet.setBackgroundColor(color);


    }

    private void getButtonPreferences() {

        int width, heigth, fontSize, colorInt;
        String color;
        SharedPreferences sharedPreferences = getSharedPreferences(BUTTON_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        width = sharedPreferences.getInt(WIDTH, -1);
        heigth = sharedPreferences.getInt(HEIGHT, -1);
        fontSize = sharedPreferences.getInt(FONT_SIZE, -1);
        color = sharedPreferences.getString(COLOR, "");

        colorInt = parseColor(color);
        if(heigth == -1 || width == -1) {
            return;
        }

        setButtonStyle(buttonOne, width, heigth, fontSize, colorInt);
        setButtonStyle(buttonTwo, width, heigth, fontSize, colorInt);
        setButtonStyle(buttonThree, width, heigth, fontSize, colorInt);
      //  setButtonStyle(buttonFour, width, heigth, fontSize, colorInt);



    }


    private int parseColor(String color) {

        switch (color) {
            case "Red":
                return Color.RED;
            case "Green":
                return Color.GREEN;
            case "Blue":
                return Color.BLUE;
            case "Black":
                return Color.BLACK;
            case "white":
                return Color.WHITE;
            default:
                return Color.BLACK;


        }


    }


}
