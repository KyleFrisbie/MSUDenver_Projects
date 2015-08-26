package com.kylefrisbie.colorpicker.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;


public class MainActivity extends Activity {

    private NumberPicker mRedButton, mBlueButton, mGreenButton;
    private ImageView mColorBox;
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 255;
    private int red, green, blue = 0;

    protected void initializeNumberPicker(NumberPicker picker) {
        picker.setMinValue(MIN_VALUE);
        picker.setMaxValue(MAX_VALUE);
    }

    protected void resetBackgroundColor() {
        mColorBox.setBackgroundColor(Color.rgb(red, green, blue));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColorBox = (ImageView)findViewById(R.id.imageView);
        resetBackgroundColor();

        mRedButton = (NumberPicker)findViewById(R.id.numberPicker_Red);
        initializeNumberPicker(mRedButton);

        mRedButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                red = newVal;
                resetBackgroundColor();
            }
        });

        mGreenButton = (NumberPicker)findViewById(R.id.numberPicker_Green);
        initializeNumberPicker(mGreenButton);

        mGreenButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                green = newVal;
                resetBackgroundColor();
            }
        });

        mBlueButton = (NumberPicker)findViewById(R.id.numberPicker_Blue);
        initializeNumberPicker(mBlueButton);

        mBlueButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                blue = newVal;
                resetBackgroundColor();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
