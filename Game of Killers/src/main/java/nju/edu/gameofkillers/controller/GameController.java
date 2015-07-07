package nju.edu.gameofkillers.controller;

import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.model.Players;

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
}
