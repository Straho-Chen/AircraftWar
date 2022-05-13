package com.hitsz.aircraftwar.aircraft;

import com.hitsz.aircraftwar.aircraft.strategy.FireStrategy;
import com.hitsz.aircraftwar.basic.AbstractFlyingObject;
import com.hitsz.aircraftwar.bullet.AbstractBullet;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected FireStrategy fireStrategy;
    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    protected int direction;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.fireStrategy = fireStrategy;
        this.direction = direction;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if(hp <= 0) {
            hp = 0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    /**
     * 设置火力
     * @param fireStrategy
     */
    public void setFireStrategy(FireStrategy fireStrategy) {
        this.fireStrategy = fireStrategy;
    }

    public FireStrategy getFireStrategy() {
        return fireStrategy;
    }

    /**
     * 执行火力设置，相当于shoot
     * @param abstractAircraft
     * @return
     */
    public List<AbstractBullet> executeStrategy(AbstractAircraft abstractAircraft) {
        return fireStrategy.fireSet(abstractAircraft, direction);
    }

    /**
     * 获取speedX，便于散射火力设置
     * @return
     */
    public int getSpeedX() {
        return speedX;
    }
}


