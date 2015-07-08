package nju.edu.gameofkillers.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.common.Tools;

/**
 * Created by hazel on 2015-07-06.
 */
public class Player {
    private String name;
    private Bitmap header;

    public Player(String name, Bitmap bitmap) {
        this.name = name;
        this.header = bitmap;
    }

    public static Player makePlayer (Bundle bundle) {
        String name = bundle.getString(Constants.KEY_NEW_PLAYER_NAME);
        byte[] headerByteArray = bundle.getByteArray(Constants.KEY_NEW_PLAYER_HEADER);

        if (Tools.isEmptyString(name) && headerByteArray == null) {
            return null;
        }

        Bitmap header = null;
        if (headerByteArray != null) {
            header = BitmapFactory.decodeByteArray(
                    headerByteArray, 0, headerByteArray.length);
        }

        return new Player(name, header);
    }

    public String getName() {
        return name;
    }

    public Bitmap getHeader() {
        return header;
    }

    public boolean hasHeader() {
        return header != null;
    }
}
