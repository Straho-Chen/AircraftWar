package com.hitsz.aircraftwar.prop;

import com.hitsz.aircraftwar.aircraft.HeroAircraft;
import com.hitsz.aircraftwar.prop.strategy.PropStrategy;

public class FireSupplyProp extends AbstractProp {
    private PropStrategy propStrategy;
    public FireSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void setPropStrategy(PropStrategy propStrategy) {
        this.propStrategy = propStrategy;
    }

    public void executeStrategy(HeroAircraft heroAircraft) {
        propStrategy.firePorpSet(heroAircraft);
        System.out.println("FireSupply active!");
    }
}
