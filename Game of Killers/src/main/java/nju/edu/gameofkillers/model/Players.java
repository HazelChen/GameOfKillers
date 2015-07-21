package nju.edu.gameofkillers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hazel on 2015-07-07.
 */
public class Players {
    private ArrayList<Player> players = new ArrayList<Player>();

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public int getPlayersNum() {
        return players.size();
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void setIdentity(List<Identity> identities) {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setIdentity(identities.get(i));
        }
    }
}
