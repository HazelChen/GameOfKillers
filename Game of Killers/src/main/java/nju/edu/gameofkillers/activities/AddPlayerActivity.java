package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.common.Tools;
import nju.edu.gameofkillers.model.Player;

import java.io.*;


public class AddPlayerActivity extends Activity {
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private String headerFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        addActionListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        getAndSetPlayerData();
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == Constants.CODE_NEW_PLAYER_PIC) {
            Uri uri = data.getData();
            headerFilePath = getImageFilePath(uri);
        }

        Bitmap header = null;
        try {
            header = BitmapFactory.decodeFile(headerFilePath);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (header != null) {
            ImageView imageView = (ImageView) findViewById(R.id.imageview_header);
            imageView.setImageBitmap(header);
        }
    }

    private void addActionListeners() {
        ImageButton cameraButton = (ImageButton) findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(generateCameraFile()));
                startActivityForResult(intent, Constants.CODE_NEW_PLAYER_CAMERA);
            }
        });

        ImageButton picButton = (ImageButton) findViewById(R.id.button_choose_pic);
        picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_UNSPECIFIED);
                startActivityForResult(intent, Constants.CODE_NEW_PLAYER_PIC);
            }
        });
    }

    private void getAndSetPlayerData() {
        EditText nameEditText = (EditText) findViewById(R.id.edittext_newplayer_name);
        String name = nameEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_NEW_PLAYER_NAME, name);
        intent.putExtra(Constants.KEY_NEW_PLAYER_HEADER_FILE, headerFilePath);
        setResult(Constants.CODE_NEW_PLAYER, intent);
    }

    private File generateCameraFile() {
        String picName = String.valueOf(System.currentTimeMillis()) + ".png";

        File headerFile = new File(
                Environment.getExternalStorageDirectory(),
                picName);

        headerFilePath = headerFile.getAbsolutePath();

        return headerFile;
    }

    private String getImageFilePath(Uri uri) {
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
            Cursor cursor = getContentResolver()
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
