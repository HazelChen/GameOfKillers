package nju.edu.gameofkillers.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.GridLayout;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.model.Identity;
import nju.edu.gameofkillers.views.CardView;

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

    public static int getIdentityColor(Context context, Identity identity) {
        if (identity.equals(Identity.KILLER)) {
            return context.getResources().getColor(R.color.red_dark);
        }
        if (identity.equals(Identity.POLICE)) {
            return context.getResources().getColor(R.color.green_dark);
        }
        if (identity.equals(Identity.CIVILIAN)) {
            return context.getResources().getColor(R.color.yellow_dark);
        }

        throw new RuntimeException("The identity is:" + identity);
    }

    public static void averageGridLayoutUnderCard(Context context, GridLayout gridLayout) {
        int cardWidth = CardView.getShouldWidth(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSizePoint = new Point();
        wm.getDefaultDisplay().getSize(screenSizePoint);
        int screenWidth = screenSizePoint.x;
        int numOfColumn = screenWidth / cardWidth;
        if (numOfColumn < 1) {
            numOfColumn = 1;
        }

        gridLayout.setColumnCount(numOfColumn);
        gridLayout.setUseDefaultMargins(true);
        gridLayout.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
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
