package com.hitsz.aircraftwar.UI;

import android.content.Context;

import com.hitsz.aircraftwar.MusicService;
import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.aircraft.Boss;
import com.hitsz.aircraftwar.factory.BossFactory;
import com.hitsz.aircraftwar.factory.EliteFactory;
import com.hitsz.aircraftwar.factory.MobFactory;

public class HardGameView extends GameTemplateView{
    public HardGameView(Context context, boolean bgmStart) {
        super(context, bgmStart);
    }

    @Override
    public void createEnemy() {
        //敌机属性随时间增加， 精英机的产生概率随时间提升
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
                //每次召唤提升Boss血量
                enemyAircrafts.add(enemyFactory.creatEnemy(1000 + bossNum * 500));
                if (bgmStart) {
                    bossBgmThread = new MusicService("bgm_boss", 2);
                    bossBgmThread.playMusic();
                }
            }
        }
    }

    @Override
    public void difficultyIncrease() {
        //Boss机产生的分数阈值随时间提升，敌机数量最大值随时间提升，两者都有限制，根据难度不同设置
        if (time % 10000 == 0) {
            if (enemyMaxNumber < 20) {
                enemyMaxNumber += 1;
            }
            if (bossScoreThreshold > 100) {
                bossScoreThreshold -= 10;
            }
            if (eliteEnemyProbability < 0.5) {
                eliteEnemyProbability += 0.1;
            }
            if (ratio < 2) {
                ratio += 0.02;
            }
            if (enemyCycleDuration > 200) {
                enemyCycleDuration -= 10;
            }
            System.out.println("提高难度！boss机阈值："+bossScoreThreshold+','+"敌机最大数量："+enemyMaxNumber+','
                    +"精英机概率："+String.format("%.2f", eliteEnemyProbability)+','+"敌机属性提升倍率："+String.format("%.2f", ratio)
                    +"敌机子弹和敌机产生周期："+enemyCycleDuration+'。');
        }
    }
}
