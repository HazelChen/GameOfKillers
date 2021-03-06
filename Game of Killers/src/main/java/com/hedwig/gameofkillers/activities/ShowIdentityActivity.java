package com.hedwig.gameofkillers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hedwig.gameofkillers.common.Constants;
import com.hedwig.gameofkillers.controller.GameController;
import com.hedwig.gameofkillers.controller.Tools;
import com.hedwig.gameofkillers.model.Player;
import com.umeng.analytics.MobclickAgent;
import com.hedwig.gameofkillers.R;;

public class ShowIdentityActivity extends AppCompatActivity {
    private int playerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_identity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        playerIndex = getIntent().getExtras().getInt(Constants.KEY_PLAYER_INDEX);

        fillPlayerInformation();

        addButtonListener();
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

    private void addButtonListener() {
        Button button = (Button) findViewById(R.id.button_after_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPlayerIndex = playerIndex + 1;
                if (nextPlayerIndex < GameController.getPlayersNum()) {
                    Intent intent = new Intent(ShowIdentityActivity.this,
                            ViewIdnetityActivity.class);
                    intent.putExtra(Constants.KEY_PLAYER_INDEX, nextPlayerIndex);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ShowIdentityActivity.this,
                            BackToJudgeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void fillPlayerInformation() {
        Player player = GameController.getPlayer(playerIndex);

        TextView textView = (TextView) findViewById(R.id.textview_identity_show_identity);
        textView.setText(Tools.getIdentityName(this, player.getIdentity()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_identity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
