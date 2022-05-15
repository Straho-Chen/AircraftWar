package com.hitsz.aircraftwar.UI;

import android.content.Context;

import com.hitsz.aircraftwar.factory.EliteFactory;
import com.hitsz.aircraftwar.factory.MobFactory;

public class EasyGameView extends GameTemplateView{
    public EasyGameView(Context context, boolean bgmStart) {
        super(context, bgmStart);
    }

    @Override
    public void createEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber && isCreate(eliteEnemyProbability)) {
            enemyFactory = new EliteFactory();
            enemyAircrafts.add(enemyFactory.creatEnemy(200));
        }
        else if (enemyAircrafts.size() < enemyMaxNumber) {
            enemyFactory = new MobFactory();
            enemyAircrafts.add(enemyFactory.creatEnemy(100));
        }
    }

    @Override
    public void difficultyIncrease() {}
}
