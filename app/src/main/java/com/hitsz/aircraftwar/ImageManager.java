package com.hitsz.aircraftwar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hitsz.aircraftwar.R;
import com.hitsz.aircraftwar.aircraft.Boss;
import com.hitsz.aircraftwar.aircraft.EliteEnemy;
import com.hitsz.aircraftwar.aircraft.HeroAircraft;
import com.hitsz.aircraftwar.aircraft.MobEnemy;
import com.hitsz.aircraftwar.bullet.EnemyBullet;
import com.hitsz.aircraftwar.bullet.HeroBullet;
import com.hitsz.aircraftwar.prop.BloodSupplyProp;
import com.hitsz.aircraftwar.prop.BombSupplyProp;
import com.hitsz.aircraftwar.prop.FireSupplyProp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap BLOODSUPPLY_PROP_IMAGE;
    public static Bitmap FIRESUPPLY_PROP_IMAGE;
    public static Bitmap BOMBSUPPLY_PROP_IMAGE;

    public ImageManager(Context context) {
        BACKGROUND_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);

        HERO_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.hero);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.mob);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet_enemy);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.elite);
        BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.boss);
        BLOODSUPPLY_PROP_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_blood);
        FIRESUPPLY_PROP_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bullet);
        BOMBSUPPLY_PROP_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.prop_bomb);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(Boss.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BloodSupplyProp.class.getName(), BLOODSUPPLY_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(FireSupplyProp.class.getName(), FIRESUPPLY_PROP_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BombSupplyProp.class.getName(), BOMBSUPPLY_PROP_IMAGE);
    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static void setBackgroundImage(Context context, String difficulty) {
        if (difficulty.equals("Easy")) {
            BACKGROUND_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        }
        else if (difficulty.equals("Medium")) {
            BACKGROUND_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg3);
        }
        else {
            BACKGROUND_IMAGE = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg5);
        }
    }
}
