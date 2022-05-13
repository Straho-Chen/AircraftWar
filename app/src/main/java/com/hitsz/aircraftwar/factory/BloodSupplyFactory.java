package com.hitsz.aircraftwar.factory;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.prop.AbstractProp;
import com.hitsz.aircraftwar.prop.BloodSupplyProp;

public class BloodSupplyFactory extends AbstractPropFactory {

    @Override
    public AbstractProp creatProp(AbstractAircraft enemyAircraft) {
        return new BloodSupplyProp(
                enemyAircraft.getLocationX(),
                enemyAircraft.getLocationY(),
                speedX,
                speedY
        );
    }
}
