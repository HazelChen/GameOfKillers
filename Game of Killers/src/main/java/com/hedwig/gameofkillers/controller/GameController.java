package com.hedwig.gameofkillers.controller;

import com.hedwig.gameofkillers.model.GameResult;
import com.hedwig.gameofkillers.model.Identity;
import com.hedwig.gameofkillers.model.Player;
import com.hedwig.gameofkillers.model.Players;

import java.util.Collections;
import java.util.List;

/**
 * Created by hazel on 2015-07-07.
 */
public class GameController {
    private static Players players = new Players();

    public static void addPlayer(Player player) {
        players.addPlayer(player);
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

    public static Player getPlayer(int index) {
        return players.getPlayer(index);
    }

    public static void arrangeIdentity(CommonRuler commonRuler) {
        List<Identity> identities = commonRuler.generateIdentityList();
        Collections.shuffle(identities);
        players.setIdentity(identities);
    }

    public static GameResult getResult() {
        if (players.allKillersDead()) {
            return GameResult.CIVILIAN_WIN;
        } else if (players.allGoodMenDead()) {
            return GameResult.KILLER_WIN;
        } else {
            return GameResult.ING;
        }
    }

    public static void allAlive() {
        players.allAlive();
    }
}
