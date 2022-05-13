package com.hitsz.aircraftwar.factory;

import com.hitsz.aircraftwar.ImageManager;
import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.aircraft.EliteEnemy;
import com.hitsz.aircraftwar.aircraft.strategy.DirectBallistic;

import java.util.Random;

public class EliteFactory extends AbstractEnemyFactory {

    private int randomDirection() {
        Random r = new Random();
        double t = r.nextDouble();
        if (t > 0.5) {
            return -1;
        }
        else {
            return 1;
        }
    }

    @Override
    public AbstractAircraft creatEnemy(int hp) {
        return new EliteEnemy(
                (int) (Math.random() * (MainActivity.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2),
                2 * this.randomDirection(),
                2,
                hp,
                new DirectBallistic(),
                1
        );
    }
}
