package com.hitsz.aircraftwar.dao;

import java.io.*;
import java.util.*;

/**
 * @author Strah
 */
public class PlayerDaoImpl implements PlayerDao{
    private List<Player> players = new ArrayList<>();

    public PlayerDaoImpl() {
        File file = new File("src/edu/hitsz/dao/data/PlayerInfo.dat");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Player[] player = (Player[]) ois.readObject();
            players = new ArrayList<Player>(Arrays.asList(player));
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Player> getAllPlayers() {
        return players;
    }

    @Override
    public void doAdd(Player player) {
        players.add(player);
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setRank(i + 1);
        }
    }

    @Override
    public void doDelete(String name, String time) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getName().equals(name)) {
                if (player.getTime().equals(time)){
                    players.remove(player);
                }
            }
        }
        Collections.sort(players);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setRank(i + 1);
        }
    }

    public void writeFile() {
        File file = new File("src/edu/hitsz/dao/data/PlayerInfo.dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Player[] pl = new Player[players.size()];
            players.toArray(pl);
            oos.writeObject(pl);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
