package nju.edu.gameofkillers.model;

import java.util.ArrayList;

/**
 * Created by hazel on 2015-07-07.
 */
public class Players {
    private ArrayList<Player> players = new ArrayList<Player>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Player getLast() {
        return players.get(players.size() - 1);
    }
}
