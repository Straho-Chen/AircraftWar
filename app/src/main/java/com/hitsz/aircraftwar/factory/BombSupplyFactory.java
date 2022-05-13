package com.hitsz.aircraftwar.factory;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.prop.AbstractProp;
import com.hitsz.aircraftwar.prop.BombSupplyProp;

public class BombSupplyFactory extends AbstractPropFactory {

    @Override
    public AbstractProp creatProp(AbstractAircraft enemyAircraft) {
        return new BombSupplyProp(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                speedX,
                speedY
        );
    }
}
