package com.hitsz.aircraftwar.prop;

import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.basic.AbstractFlyingObject;

/**
 * 所有道具的抽象父类：
 * 回血、火力、炸弹（increaseHp, increaseFire, boom）
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        //判定 y 轴出界
        if (speedY > 0 && locationY >= MainActivity.WINDOW_HEIGHT) {
            vanish();
        }
    }
}