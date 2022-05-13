package com.hitsz.aircraftwar.aircraft.strategy;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.bullet.AbstractBullet;
import com.hitsz.aircraftwar.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class DoubleBullet implements FireStrategy{
    private int shootNum = 2;
    private int power = 100;
    @Override
    public List<AbstractBullet> fireSet(AbstractAircraft abstractAircraft, int direction) {
        List<AbstractBullet> bullets = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        int speedY = abstractAircraft.getSpeedY() + direction*5;
        int speedX = abstractAircraft.getSpeedX();
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            abstractBullet = new HeroBullet(x + (i * 2 - shootNum + 1)*20, y, speedX, speedY, power);
            bullets.add(abstractBullet);
        }
        return bullets;
    }
}
