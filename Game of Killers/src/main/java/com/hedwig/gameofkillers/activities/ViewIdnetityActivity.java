package com.hedwig.gameofkillers.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hedwig.gameofkillers.common.Constants;
import com.hedwig.gameofkillers.controller.GameController;
import com.hedwig.gameofkillers.controller.ImageDecoder;
import com.hedwig.gameofkillers.model.Player;
import com.umeng.analytics.MobclickAgent;
import com.hedwig.gameofkillers.R;;

public class ViewIdnetityActivity extends AppCompatActivity {
    private int playerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_idnetity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerIndex = getIntent().getExtras().getInt(Constants.KEY_PLAYER_INDEX);

        fillPlayerInformation();

        addButtonListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_idnetity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (playerIndex == 0) {
            super.onBackPressed();
        } else {
            Toast.makeText(this,
                    getString(R.string.back_pressed_view_identity),
                    Toast.LENGTH_SHORT)
                    .show();
        }

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

    private void fillPlayerInformation() {
        Player player = GameController.getPlayer(playerIndex);
        if (player.hasHeader()) {
            ImageView headerImageView = (ImageView) findViewById(R.id.imageview_view_identity);
            Bitmap bitmap = ImageDecoder.decodeWithCompression(player.getHeaderFile(),
                    headerImageView.getLayoutParams().width,
                    headerImageView.getLayoutParams().height);
            headerImageView.setImageBitmap(bitmap);
        }
        TextView textView = (TextView) findViewById(R.id.textview_name_view_identity);
        textView.setText(player.getName());
    }

    private void addButtonListener() {
        Button button = (Button) findViewById(R.id.button_view_identity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewIdnetityActivity.this, ShowIdentityActivity.class);
                intent.putExtra(Constants.KEY_PLAYER_INDEX, playerIndex);
                startActivity(intent);
            }
        });
    }
}
