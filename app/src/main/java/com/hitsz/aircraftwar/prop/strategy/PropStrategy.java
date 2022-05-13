package com.hitsz.aircraftwar.prop.strategy;

import com.hitsz.aircraftwar.aircraft.HeroAircraft;

public interface PropStrategy {

    /**
     * 对不同的火力道具设置不同的效果
     * @param heroAircraft
     * @return AbstractProp
     */
    public void firePorpSet(HeroAircraft heroAircraft);
}
