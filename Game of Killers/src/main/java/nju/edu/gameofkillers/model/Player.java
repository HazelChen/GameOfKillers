package nju.edu.gameofkillers.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.common.Tools;

import java.io.File;

/**
 * Created by hazel on 2015-07-06.
 */
public class Player {
    private String name;
    private String headerFile;

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
}
