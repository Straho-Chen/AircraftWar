package com.hitsz.aircraftwar;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hitsz.aircraftwar.aircraft.AbstractAircraft;
import com.hitsz.aircraftwar.aircraft.Boss;
import com.hitsz.aircraftwar.aircraft.EliteEnemy;
import com.hitsz.aircraftwar.aircraft.HeroAircraft;
import com.hitsz.aircraftwar.aircraft.MobEnemy;
import com.hitsz.aircraftwar.aircraft.strategy.DirectBallistic;
import com.hitsz.aircraftwar.aircraft.strategy.DoubleBullet;
import com.hitsz.aircraftwar.basic.AbstractFlyingObject;
import com.hitsz.aircraftwar.bullet.AbstractBullet;
import com.hitsz.aircraftwar.factory.AbstractEnemyFactory;
import com.hitsz.aircraftwar.factory.AbstractPropFactory;
import com.hitsz.aircraftwar.factory.BloodSupplyFactory;
import com.hitsz.aircraftwar.factory.BombSupplyFactory;
import com.hitsz.aircraftwar.factory.FireSupplyFactory;
import com.hitsz.aircraftwar.prop.AbstractProp;
import com.hitsz.aircraftwar.prop.BloodSupplyProp;
import com.hitsz.aircraftwar.prop.BombSupplyProp;
import com.hitsz.aircraftwar.prop.FireSupplyProp;
import com.hitsz.aircraftwar.prop.strategy.ChangeBallistic;
import com.hitsz.aircraftwar.prop.strategy.IncreaseShootNum;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class GameTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    int timeInterval = 40;

    final HeroAircraft heroAircraft = HeroAircraft.getSingleton();
    AbstractEnemyFactory enemyFactory;
    final List<AbstractAircraft> enemyAircrafts = new LinkedList<>();
    final List<AbstractBullet> heroBullets = new LinkedList<>();
    final List<AbstractBullet> enemyBullets = new LinkedList<>();
    AbstractPropFactory propFactory;
    final List<AbstractProp> propSupply = new LinkedList<>();
    int enemyMaxNumber = 5;

    boolean gameOverFlag = false;
    public int score = 0;
    boolean bossExit = false;
    int bossNum = 0;
    int bossScoreThreshold = 500;
    int bossVanishScore = 0;
    int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    int enemyCycleDuration = 600;
    int heroCycleDuration = 400;
    int enemyCycleTime = 0;
    int heroCycleTime = 0;
    boolean bgmStart;
    MusicService bgmThread;
    MusicService bossBgmThread;
    MusicService bombBgmThread;
    MusicService bulletHitBgmThread;
    MusicService gameOverBgmThread;
    MusicService getSupplyBgmThread;

    double eliteEnemyProbability = 0.2;
    double ratio = 1;

    public GameTemplate(Context context, boolean bgmStart) {
        super(context);
        this.bgmStart = bgmStart;
        new HeroController(this, heroAircraft);
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {
        if (bgmStart) {
            bgmThread = new MusicService("bgm", 2);
            bgmThread.playMusic();
        }

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;
            enemyCycleTime += 1;
            heroCycleTime += 1;

            // 周期性执行（控制频率）
            if (enemyTimeCountAndNewCycleJudge()) {
                this.createEnemy();
                enemyShootAction();
            }
            if (heroTimeCountAndNewCycleJudge()) {
                heroShootAction();
            }

            // 子弹移动
            this.bulletsMoveAction();

            // 飞机移动
            this.aircraftsMoveAction();

            //道具移动
            this.propSupplyMoveAction();

            // 撞击检测
            this.crashCheckAction();

            // 后处理
            this.postProcessAction();

            //增加难度
            this.difficultyIncrease();

            //每个时刻重绘界面
//            this.repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                synchronized (Main.object) {
                    // 游戏结束
                    executorService.shutdown();
                    if (bgmStart) {
                        gameOverBgmThread = new MusicService("game_over", 1);
                        gameOverBgmThread.start();
                        bgmThread.stopMusic();
                        if (bossExit) {
                            bossBgmThread.stopMusic();
                        }
                    }
                    gameOverFlag = true;
                    Main.object.notify();
                    System.out.println("Game Over!");
                }
            }
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    public final boolean enemyTimeCountAndNewCycleJudge() {
        enemyCycleTime += timeInterval;
        if (enemyCycleTime >= enemyCycleDuration && enemyCycleTime - timeInterval < enemyCycleTime) {
            // 跨越到新的周期
            enemyCycleTime %= enemyCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    public final boolean heroTimeCountAndNewCycleJudge() {
        heroCycleTime += timeInterval;
        if (heroCycleTime >= heroCycleDuration && heroCycleTime - timeInterval < heroCycleTime) {
            // 跨越到新的周期
            heroCycleTime %= heroCycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 控制敌机产生，传入概率
     * @param probability
     * @return
     */
    public final boolean isCreate(double probability) {
        Random r = new Random();
        double t = r.nextDouble();
        if (t <= probability) {
            return true;
        }
        else {
            return false;
        }
    }

    public abstract void createEnemy();
    public abstract void difficultyIncrease();
    public final boolean isThreshold() {
        if (score > bossScoreThreshold * (bossNum+1) + bossVanishScore) {
            return true;
        }
        else {
            return false;
        }
    }

    public final void heroShootAction() {
        heroBullets.addAll(heroAircraft.executeStrategy(heroAircraft));
    }

    public final void enemyShootAction() {
        for (AbstractAircraft enemy : enemyAircrafts) {
            if (EliteEnemy.class.isAssignableFrom(enemy.getClass())) {
                enemyBullets.addAll(enemy.executeStrategy(enemy));
            }
            else if (Boss.class.isAssignableFrom(enemy.getClass())) {
                enemyBullets.addAll(enemy.executeStrategy(enemy));
            }
        }
    }

    public final void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    public final void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    public final void propSupplyMoveAction() {
        for (AbstractProp propsupply: propSupply) {
            propsupply.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    public final void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹或敌机击毁的英雄机，不再检测
                break;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
                if (heroAircraft.notValid()) {
                    // 英雄机被击毁，不再检测
                    break;
                }
            }
        }
        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    if (bgmStart) {
                        bulletHitBgmThread = new MusicService("bullet_hit", 1);
                        bulletHitBgmThread.playMusic();
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        if (!MobEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            Random r = new Random();
                            int t = r.nextInt(10);
                            if (t==1 || t == 9) {
                                propFactory = new BloodSupplyFactory();
                                propSupply.add(propFactory.creatProp(enemyAircraft));
                            }
                            else if (t == 3 || t == 7) {
                                propFactory = new FireSupplyFactory();
                                propSupply.add(propFactory.creatProp(enemyAircraft));
                            }
                            else if (t == 5) {
                                propFactory = new BombSupplyFactory();
                                BombSupplyProp bombSupplyProp = (BombSupplyProp) propFactory.creatProp(enemyAircraft);
                                propSupply.add(bombSupplyProp);
                            }
                        }
                        if (MobEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            score += 10;
                        }
                        else if (EliteEnemy.class.isAssignableFrom(enemyAircraft.getClass())) {
                            score += 20;
                        }
                        else {
                            score += 30;
                            bossNum += 1;
                            bossVanishScore = score;
                            if (bgmStart) {
                                bossBgmThread.stopMusic();
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : propSupply) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹或敌机击毁的英雄机，不再检测
                break;
            }
            if (heroAircraft.crash(prop)) {
                // 英雄机撞击到道具
                if (bgmStart) {
                    getSupplyBgmThread = new MusicService("get_supply", 1);
                    getSupplyBgmThread.playMusic();
                }
                if (BloodSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    ((BloodSupplyProp) prop).increaseHp(heroAircraft);
                }
                else if (FireSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    /**
                     * 如果捡到火力道具时英雄机处于散射状态，那么生成的道具策略为散射，起到延长作用
                     * 如果捡到火力道具时处于单子弹直射状态，那么生成的道具策略为增加子弹
                     * 如果捡到火力道具时处于双子弹直射状态，那么生成的道具策略为散射
                     */
                    if (DirectBallistic.class.isAssignableFrom(heroAircraft.getFireStrategy().getClass())) {
                        ((FireSupplyProp) prop).setPropStrategy(new IncreaseShootNum());
                        ((FireSupplyProp) prop).executeStrategy(heroAircraft);
                    }
                    else if (DoubleBullet.class.isAssignableFrom(heroAircraft.getFireStrategy().getClass())) {
                        ((FireSupplyProp) prop).setPropStrategy(new ChangeBallistic());
                        ((FireSupplyProp) prop).executeStrategy(heroAircraft);
                    }
                    else {
                        ((FireSupplyProp) prop).setPropStrategy(new ChangeBallistic());
                        ((FireSupplyProp) prop).executeStrategy(heroAircraft);
                    }
                }
                else if (BombSupplyProp.class.isAssignableFrom(prop.getClass())) {
                    if (bgmStart) {
                        bombBgmThread = new MusicService("bomb_explosion", 1);
                        bombBgmThread.playMusic();
                    }
                    for (AbstractAircraft enemy : enemyAircrafts) {
                        if (!enemy.notValid()) {
                            ((BombSupplyProp) prop).addEnemy(enemy);
                            if (EliteEnemy.class.isAssignableFrom(enemy.getClass())) {
                                score += 20;
                            }
                            else if (MobEnemy.class.isAssignableFrom(enemy.getClass())) {
                                score += 10;
                            }
                            else {
                                if (enemy.getHp() == 100) {
                                    score += 30;
                                }
                            }
                        }
                    }
                    for (AbstractBullet bullet : enemyBullets) {
                        if (!bullet.notValid()) {
                            ((BombSupplyProp) prop).addEnemy(bullet);
                        }
                    }
                    ((BombSupplyProp) prop).executeBombSupply();
                }
                prop.vanish();
                if (heroAircraft.notValid()) {
                    // 英雄机被击毁，不再检测
                    break;
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    public final void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        propSupply.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public final void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - MainActivity.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == MainActivity.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, propSupply);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    public final void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    public final void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

}
