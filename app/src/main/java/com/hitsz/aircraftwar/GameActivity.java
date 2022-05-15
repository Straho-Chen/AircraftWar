package com.hitsz.aircraftwar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hitsz.aircraftwar.UI.EasyGameView;
import com.hitsz.aircraftwar.UI.GameTemplateView;
import com.hitsz.aircraftwar.UI.HardGameView;
import com.hitsz.aircraftwar.UI.MediumGameView;

public class GameActivity extends AppCompatActivity {
    GameTemplateView gameTemplateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (MainActivity.difficulty) {
            case "easy":
                gameTemplateView = new EasyGameView(this, MainActivity.bgmStart);
                break;
            case "medium":
                gameTemplateView = new MediumGameView(this, MainActivity.bgmStart);
                break;
            case "hard":
                gameTemplateView = new HardGameView(this, MainActivity.bgmStart);
                break;
            default:
                gameTemplateView = null;
                break;
        }
        setContentView(gameTemplateView);
    }
}
