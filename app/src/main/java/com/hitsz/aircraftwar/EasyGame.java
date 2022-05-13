package edu.hitsz.application.Game;

import com.hitsz.aircraftwar.GameTemplate;

public class EasyGame extends GameTemplate {

    public EasyGame(boolean bgmStart) {
        super(this, bgmStart);
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
