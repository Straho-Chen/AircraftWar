package edu.hitsz.application.Game;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.Boss;
import edu.hitsz.application.MusicThread;
import edu.hitsz.factory.BossFactory;
import edu.hitsz.factory.EliteFactory;
import edu.hitsz.factory.MobFactory;

public class MediumGame extends GameTemplate{

    public MediumGame(boolean bgmStart) {
        super(bgmStart);
    }

    @Override
    public void createEnemy() {
        if (enemyAircrafts.size() < enemyMaxNumber && isCreate(eliteEnemyProbability)) {
            enemyFactory = new EliteFactory();
            enemyAircrafts.add(enemyFactory.creatEnemy((int) (200 * ratio)));
        }
        else if (enemyAircrafts.size() < enemyMaxNumber) {
            enemyFactory = new MobFactory();
            enemyAircrafts.add(enemyFactory.creatEnemy((int) (100 * ratio)));
        }
        if(isThreshold()) {
            bossExit = false;
            for (AbstractAircraft enemy : enemyAircrafts) {
                if (Boss.class.isAssignableFrom(enemy.getClass())) {
                    bossExit = true;
                    break;
                }
            }
            if (!bossExit) {
                enemyFactory = new BossFactory();
                enemyAircrafts.add(enemyFactory.creatEnemy(1000));
                if (bgmStart) {
                    bossBgmThread = new MusicThread("src/videos/bgm_boss.wav", 2);
                    bossBgmThread.start();
                }
            }
        }
    }

    @Override
    public void difficultyIncrease() {
        if (time % 100000 == 0) {
            if (enemyMaxNumber < 15) {
                enemyMaxNumber += 1;
            }
            if (bossScoreThreshold > 200) {
                bossScoreThreshold -= 10;
            }
            if (eliteEnemyProbability < 0.4) {
                eliteEnemyProbability += 0.1;
            }
            if (ratio < 1.5) {
                ratio += 0.02;
            }
            if (enemyCycleDuration > 400) {
                enemyCycleDuration -= 10;
            }
            System.out.println("提高难度！boss机阈值："+bossScoreThreshold+','+"敌机最大数量："+enemyMaxNumber+','
                    +"精英机概率："+String.format("%.2f", eliteEnemyProbability)+','+"敌机属性提升倍率："+String.format("%.2f", ratio)
                    +"敌机子弹和敌机产生周期："+enemyCycleDuration+'。');
        }
    }
}
