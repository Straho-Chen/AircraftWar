package com.hitsz.aircraftwar.aircraft;

import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.aircraft.strategy.FireStrategy;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
        super(locationX, locationY, speedX, speedY, hp, fireStrategy, direction);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
