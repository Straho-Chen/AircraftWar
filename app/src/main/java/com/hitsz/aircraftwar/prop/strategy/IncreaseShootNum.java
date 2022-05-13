package com.hitsz.aircraftwar.prop.strategy;

import com.hitsz.aircraftwar.aircraft.HeroAircraft;
import com.hitsz.aircraftwar.aircraft.strategy.DirectBallistic;
import com.hitsz.aircraftwar.aircraft.strategy.DoubleBullet;

public class IncreaseShootNum implements PropStrategy{
    @Override
    public void firePorpSet(HeroAircraft heroAircraft) {
        Runnable r = () -> {
            heroAircraft.setFireStrategy(new DoubleBullet());
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (DoubleBullet.class.isAssignableFrom(heroAircraft.getFireStrategy().getClass())) {
                heroAircraft.setFireStrategy(new DirectBallistic());
            }
        };
        new Thread(r).start();
    }
}
