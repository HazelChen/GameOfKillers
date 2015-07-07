package nju.edu.gameofkillers.model;

import android.os.Bundle;
import nju.edu.gameofkillers.common.Constants;

/**
 * Created by hazel on 2015-07-06.
 */
public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public static Player makePlayer (Bundle bundle) {
        String name = bundle.getString(Constants.KEY_NEW_PLAYER_NAME);
        if (name == null || name.equals("")) {
            return null;
        }
        return new Player(name);
    }

    public String getName() {
        return name;
    }
}
