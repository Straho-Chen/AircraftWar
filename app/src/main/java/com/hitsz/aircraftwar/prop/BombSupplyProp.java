package com.hitsz.aircraftwar.prop;

import com.hitsz.aircraftwar.aircraft.Boss;
import com.hitsz.aircraftwar.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class BombSupplyProp extends AbstractProp {

    private List<AbstractFlyingObject> objects = new ArrayList<>();

    public BombSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void addEnemy(AbstractFlyingObject object) {
        objects.add(object);
    }

    public void notifyAllEnemy() {
        for (AbstractFlyingObject object : objects) {
            if (Boss.class.isAssignableFrom(object.getClass())) {
                ((Boss) object).decreaseHp(100);
                if (((Boss) object).getHp() == 0) {
                    object.vanish();
                }
            }
            else {
                object.vanish();
            }
        }
    }

    public void executeBombSupply() {
        notifyAllEnemy();
        System.out.print("BoomSupply active!");
    }

}
