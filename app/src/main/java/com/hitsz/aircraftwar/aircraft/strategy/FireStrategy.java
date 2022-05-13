package com.hitsz.aircraftwar.aircraft.strategy;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.bullet.AbstractBullet;

import java.util.List;

public interface FireStrategy {
    /**
     * 为不同机型设置不同火力
     * @param abstractAircraft
     * @return
     */
    public List<AbstractBullet> fireSet(AbstractAircraft abstractAircraft, int direction);
}
