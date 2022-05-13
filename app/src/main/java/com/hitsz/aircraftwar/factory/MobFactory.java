package com.hitsz.aircraftwar.factory;

import com.hitsz.aircraftwar.ImageManager;
import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.aircraft.MobEnemy;
import com.hitsz.aircraftwar.aircraft.strategy.DirectBallistic;

public class MobFactory extends AbstractEnemyFactory {

    @Override
    public AbstractAircraft creatEnemy(int hp) {
        return new MobEnemy(
                (int) (Math.random() * (MainActivity.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2),
                0,
                2,
                hp,
                new DirectBallistic(),
                1
        );
    }
}
