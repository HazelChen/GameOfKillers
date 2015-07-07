package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.views.CardView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addActionListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_item_likeme);
        item.setActionView(R.layout.actionbar_likeme);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.CODE_NEW_PLAYER) &&
                (resultCode == Constants.CODE_NEW_PLAYER)) {
            Player player = Player.makePlayer(data.getExtras());
            GameController.addPlayer(player);
            refreshCardViews();
        }

    }

    private void refreshCardViews() {
        GridLayout cardGridLayout = (GridLayout) findViewById(R.id.layout_cards);
        Player lastAddedPlayer = GameController.getLastAddedPlayer();
        cardGridLayout.addView(new CardView(this, lastAddedPlayer));
    }

    private void addActionListeners() {
        ImageButton imageButton  = (ImageButton) findViewById(R.id.button_add);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        AddPlayerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, Constants.CODE_NEW_PLAYER);
            }
        });
    }

}
