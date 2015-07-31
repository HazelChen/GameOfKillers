package com.hedwig.gameofkillers.model;

import android.os.Bundle;
import android.util.Log;
import com.hedwig.gameofkillers.common.Constants;
import com.hedwig.gameofkillers.controller.Tools;

/**
 * Created by hazel on 2015-07-06.
 */
public class Player {
    private String name;
    private String headerFile;
    private Identity identity;

    private boolean isDead;

    public Player(String name, String headerFile) {
        this.name = name;
        this.headerFile = headerFile;
    }

    public static Player makePlayer (Bundle bundle) {
        String name = bundle.getString(Constants.KEY_NEW_PLAYER_NAME);
        String headerFile = bundle.getString(Constants.KEY_NEW_PLAYER_HEADER_FILE);

        if (Tools.isEmptyString(name) && Tools.isEmptyString(headerFile)) {
            return null;
        }

        return new Player(name, headerFile);
    }

    public String getName() {
        return name;
    }

    public String getHeaderFile() {
        return headerFile;
    }

    public boolean hasHeader() {
        return (!Tools.isEmptyString(headerFile));
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Identity getIdentity() {
        return identity;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean isKiller() {
        return identity == Identity.KILLER;
    }

    public boolean isGoodMan() {
        return !isKiller();
    }
}
