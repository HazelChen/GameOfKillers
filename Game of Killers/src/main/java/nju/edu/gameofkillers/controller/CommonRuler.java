package nju.edu.gameofkillers.controller;

import nju.edu.gameofkillers.model.Identity;

import java.util.ArrayList;
import java.util.List;

/**
 * We can get how many killers, polices, civilians in common rule.
 * Created by hazel on 2015-07-12.
 */
public class CommonRuler {
    public static final int MIN_PLAYER_NUMBERS = 6;

    private int killersNum;
    private int policesNum;
    private int civiliansNum;

    public CommonRuler() {
        this(GameController.getPlayersNum());
    }

    public CommonRuler(int playersNum) {
        calculateIdentitiesNum(playersNum);
    }

    public CommonRuler(int killerNum, int policesNum, int civiliansNum) {
        this.killersNum = killerNum;
        this.policesNum = policesNum;
        this.civiliansNum = civiliansNum;
    }

    public List<Identity> generateIdentityList() {
        List<Identity> identities = new ArrayList<>();

        int i = 0;
        for (; i < killersNum; i++) {
            identities.add(Identity.KILLER);
        }
        for (; i < policesNum + killersNum; i++) {
            identities.add(Identity.POLICE);
        }
        for (; i < GameController.getPlayersNum(); i++) {
            identities.add(Identity.CIVILIAN);
        }

        return identities;
    }

    public void refresh() {
        int playerNum = GameController.getPlayersNum();
        calculateIdentitiesNum(playerNum);
    }

    public int getKillersNum() {
        return killersNum;
    }

    public int getPolicesNum() {
        return policesNum;
    }

    public int getCiviliansNum() {
        return civiliansNum;
    }

    private void calculateIdentitiesNum(int playersNum) {
        if (playersNum < MIN_PLAYER_NUMBERS) {
            throw new RuntimeException("The players num is " + playersNum);
        }

        if (playersNum < 11) {
            killersNum = 2;
            policesNum = 2;
        } else if (playersNum < 15) {
            killersNum = 3;
            policesNum = 3;
        } else {
            killersNum = 4;
            policesNum = 4;
        }

        civiliansNum = playersNum - killersNum - policesNum;
    }


}
