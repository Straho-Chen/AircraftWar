package com.hitsz.aircraftwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static String difficulty;
    public static boolean bgmStart;

    Button easyButton;
    Button mediumButton;
    Button hardButton;

    Switch musicSwitch;

    Intent easy, medium, hard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getScreenHW();

        easyButton = (Button) findViewById(R.id.easy);
        easyButton.setOnClickListener(this);

        mediumButton = (Button) findViewById(R.id.medium);
        mediumButton.setOnClickListener(this);

        hardButton = (Button) findViewById(R.id.hard);
        hardButton.setOnClickListener(this);

        musicSwitch = (Switch) findViewById(R.id.musicStart);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                bgmStart = isChecked;
            }
        });
    }

    public void getScreenHW() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.easy:
                easy = new Intent(MainActivity.this, GameActivity.class);
                difficulty = "easy";
                startActivity(easy);
                break;
            case R.id.medium:
                medium = new Intent(MainActivity.this, GameActivity.class);
                difficulty = "medium";
                startActivity(medium);
                break;
            case R.id.hard:
                hard = new Intent(MainActivity.this, GameActivity.class);
                difficulty = "hard";
                startActivity(hard);
                break;
            default:
                break;
        }
    }
}