package com.hitsz.aircraftwar;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MusicService extends Service {
    private static  final String TAG = "MusicService";
    private final String musicName;
    private final int times;
    public MusicService(String musicName, int times) {
        this.musicName = musicName;
        this.times = times;
    }
    //创建播放器对象
    private MediaPlayer player;
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "==== MusicService onStartCommand ===");
        String action = intent.getStringExtra("action");
        if ("play".equals(action)) {
            //播放
            playMusic();
        } else if ("stop".equals(action)) {
            //停止
            stopMusic();
        } else if ("pause".equals(action)) {
            //暂停
            pauseMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //播放音乐
    public void playMusic(){
        if(player == null){
            switch (musicName) {
                case "bgm":
                    player = MediaPlayer.create(this, R.raw.bgm);
                    break;
                case "bgm_boss":
                    player = MediaPlayer.create(this, R.raw.bgm_boss);
                    break;
                case "bomb_explosion":
                    player = MediaPlayer.create(this, R.raw.bomb_explosion);
                    break;
                case "bullet_hit":
                    player = MediaPlayer.create(this, R.raw.bullet_hit);
                    break;
                case "game_over":
                    player = MediaPlayer.create(this, R.raw.game_over);
                    break;
                case "get_supply":
                    player = MediaPlayer.create(this, R.raw.get_supply);
                    break;
                default:
                    player = null;
                    break;
            }
        }
        reverseMusic();
    }
    //暂停播放
    public void pauseMusic(){
        if(player != null && player.isPlaying()){
            player.pause();
        }
    }

    /**
     * 停止播放
     */
    public void stopMusic() {
        if (player != null) {
            player.stop();
            player.reset();//重置
            player.release();//释放
            player = null;
        }
    }

    /**
     * 重复播放
     */
    public void reverseMusic() {
        if (times == 1) {
            player.start();
        }
        else {
            while (player != null) {
                player.start();
            }
        }
    }
}
