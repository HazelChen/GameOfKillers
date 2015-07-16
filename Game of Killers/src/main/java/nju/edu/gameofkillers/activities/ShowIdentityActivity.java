package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.Tools;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.model.Player;

public class ShowIdentityActivity extends Activity {
    private int playerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_identity);

        playerIndex = getIntent().getExtras().getInt(Constants.KEY_PLAYER_INDEX);

        fillPlayerInformation();

        addButtonListener();
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
