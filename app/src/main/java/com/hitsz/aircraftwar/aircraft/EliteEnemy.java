package com.hitsz.aircraftwar.aircraft;

import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.aircraft.strategy.FireStrategy;

/**
 * 精英敌机
 * 可射击
 */
public class EliteEnemy extends AbstractAircraft {
    /**
     * @param locationX 精英机位置X坐标
     * @param locationY 精英机位置Y坐标
     * @param speedX 精英机射出的子弹的基准速度
     * @param speedY 精英机射出的子弹的基准速度
     * @param hp 精英机生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
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
