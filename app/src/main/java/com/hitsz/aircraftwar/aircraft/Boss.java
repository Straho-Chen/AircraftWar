package com.hitsz.aircraftwar.aircraft;

import com.hitsz.aircraftwar.MainActivity;
import com.hitsz.aircraftwar.aircraft.strategy.FireStrategy;

public class Boss extends AbstractAircraft{
    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, FireStrategy fireStrategy, int direction) {
        super(locationX, locationY, speedX, speedY, hp, fireStrategy, direction);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY >= MainActivity.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
