package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.model.Player;

import java.io.ByteArrayOutputStream;


public class AddPlayerActivity extends Activity {
    private Bitmap header;

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
        if (requestCode == Constants.CODE_NEW_PLAYER_CAMERA) {
            super.onActivityResult(requestCode, resultCode, data);
            Bundle cameraExtras = data.getExtras();
            if (cameraExtras == null) {//User cancelled.
                return;
            }

            header = (Bitmap) cameraExtras.get("data");
            ImageView headerImageView = (ImageView) findViewById(R.id.imageview_header);
            headerImageView.setImageBitmap(header);
        }
    }

    private void addActionListeners() {
        ImageButton cameraButton = (ImageButton) findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Constants.CODE_NEW_PLAYER_CAMERA);
            }
        });
    }

    private void getAndSetPlayerData() {
        EditText nameEditText = (EditText) findViewById(R.id.edittext_newplayer_name);
        String name = nameEditText.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_NEW_PLAYER_NAME, name);

        byte[] headerByteArray = null;
        if (header != null) {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            header.compress(Bitmap.CompressFormat.PNG, 50, bs);
            headerByteArray = bs.toByteArray();
        }
        intent.putExtra(Constants.KEY_NEW_PLAYER_HEADER, headerByteArray);
        setResult(Constants.CODE_NEW_PLAYER, intent);
    }

}
