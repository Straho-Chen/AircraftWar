package com.hitsz.aircraftwar.prop;

import com.hitsz.aircraftwar.aircraft.HeroAircraft;

public class BloodSupplyProp extends AbstractProp {

    private int hp = 100;

    public BloodSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void increaseHp(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(hp);
        System.out.println("BloodSupply active!");
    }

}
