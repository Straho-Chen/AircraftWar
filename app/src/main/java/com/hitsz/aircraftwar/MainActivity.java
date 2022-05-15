package com.hitsz.aircraftwar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hitsz.aircraftwar.UI.EasyGameView;
import com.hitsz.aircraftwar.UI.HardGameView;
import com.hitsz.aircraftwar.UI.MediumGameView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static boolean bgmStart;

    Button easyButton;
    Button mediumButton;
    Button hardButton;

    Switch musicSwitch;

    private EasyGameView easyGameView;
    private MediumGameView mediumGameView;
    private HardGameView hardGameView;

    public static ImageManager imageManager;

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

        imageManager = new ImageManager(this);
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
                easyGameView = new EasyGameView(this, bgmStart);
                setContentView(easyGameView);
                break;
            case R.id.medium:
                mediumGameView = new MediumGameView(this, bgmStart);
                setContentView(mediumGameView);
                break;
            case R.id.hard:
                hardGameView = new HardGameView(this, bgmStart);
                setContentView(hardGameView);
                break;
            default:
                break;
        }
    }
}