package nju.edu.gameofkillers.activities;

import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.umeng.analytics.MobclickAgent;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.ImageDecoder;
import nju.edu.gameofkillers.controller.Tools;

import java.io.*;


public class AddPlayerActivity extends AppCompatActivity {
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private String headerFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_addplayer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addActionListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    public void onResume() {
        super.onResume();
        //umeng
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //umeng
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == Constants.CODE_NEW_PLAYER_PIC) {
            Uri uri = data.getData();
            headerFilePath = Tools.getImageFilePath(this, uri);
        }
        //If request code == CODE_NEW_CAMERA, we will do nothing

        if (headerFilePath == null) {
            return;
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageview_header);
        Bitmap header = ImageDecoder.decodeWithCompression(headerFilePath,
                imageView.getWidth(), imageView.getHeight());
        imageView.setImageBitmap(header);

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


}
