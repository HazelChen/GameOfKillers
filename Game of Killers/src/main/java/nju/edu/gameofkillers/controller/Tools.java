package nju.edu.gameofkillers.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.model.Identity;

/**
 * Created by hazel on 2015-07-08.
 */
public class Tools {

    public static boolean isEmptyString(String s) {
        return (s == null || s.equals(""));
    }

    public static String getIdentityName(Context context, Identity identity) {
        if (identity.equals(Identity.KILLER)) {
            return context.getString(R.string.identity_killer);
        }
        if (identity.equals(Identity.POLICE)) {
            return context.getString(R.string.identity_police);
        }
        if (identity.equals(Identity.CIVILIAN)) {
            return context.getString(R.string.identity_civilian);
        }

        throw new RuntimeException("The identity is:" + identity);
    }

    public static String getImageFilePath(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver()
                    .query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
