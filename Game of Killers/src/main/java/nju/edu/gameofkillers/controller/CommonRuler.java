package nju.edu.gameofkillers.controller;

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
