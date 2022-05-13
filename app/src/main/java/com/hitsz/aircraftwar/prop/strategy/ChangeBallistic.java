package com.hitsz.aircraftwar.prop.strategy;

import com.hitsz.aircraftwar.aircraft.HeroAircraft;
import com.hitsz.aircraftwar.aircraft.strategy.DirectBallistic;
import com.hitsz.aircraftwar.aircraft.strategy.ScatteringBallistic;

public class ChangeBallistic implements PropStrategy{

    @Override
    public void firePorpSet(HeroAircraft heroAircraft) {
        Runnable r = () -> {
            int i = 10;
            while (i > 0) {
                heroAircraft.setFireStrategy(new ScatteringBallistic());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                i--;
            }
            heroAircraft.setFireStrategy(new DirectBallistic());
        };
        new Thread(r).start();
    }
}
