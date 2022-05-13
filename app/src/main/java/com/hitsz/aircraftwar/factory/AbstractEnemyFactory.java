package com.hitsz.aircraftwar.factory;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;

public abstract class AbstractEnemyFactory {

    /**
     * 创建敌机
     * @return
     * 返回抽象类AbstractAircraft
     */
    public abstract AbstractAircraft creatEnemy(int hp);

}
