package com.hitsz.aircraftwar.dao;

import java.util.List;

/**
 * @author Strah
 */
public interface PlayerDao {

    /**
     * 获取所有玩家信息
     * @return
     */
    public List<Player> getAllPlayers();

    /**
     * 添加玩家信息
     * @param player
     */
    public void doAdd(Player player);

    /**
     * 根据名字删除指定玩家信息
     * @param name
     */
    public void doDelete(String name, String time);

    /**
     * 写文件
     */
    public void writeFile();
}
