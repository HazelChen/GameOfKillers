package nju.edu.gameofkillers.controller;

import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.model.Players;

import java.util.List;

/**
 * Created by hazel on 2015-07-07.
 */
public class GameController {
    private static Players players = new Players();

    public static void addPlayer(Player player) {
        players.addPlayer(player);
    }

    public static Player getLastAddedPlayer() {
        return players.getLast();
    }

    public static List<Player> getPlayers() {
        return players.getPlayers();
    }

    public static void removePlayer(Player player) {
        players.removePlayer(player);
    }

    public static int getPlayersNum() {
        return players.getPlayersNum();
    }
}
