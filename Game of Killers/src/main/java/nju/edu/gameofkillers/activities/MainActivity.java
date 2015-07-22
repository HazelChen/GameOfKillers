package nju.edu.gameofkillers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.CommonRuler;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.views.CardView;
import android.support.v7.widget.Toolbar;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        addActionListeners();

        refreshCardViews();

        initDrawer();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_item_go) {
            if (GameController.getPlayersNum() < CommonRuler.MIN_PLAYER_NUMBERS) {
                String toast = getString(R.string.player_number_error1) +
                        CommonRuler.MIN_PLAYER_NUMBERS +
                        getString(R.string.player_number_error2);
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT)
                        .show();
                return false;
            }

            Intent intent = new Intent(MainActivity.this,
                    GameSettingActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.CODE_NEW_PLAYER) &&
                (resultCode == Constants.CODE_NEW_PLAYER)) {
            Player player = Player.makePlayer(data.getExtras());
            if (player != null) {
                GameController.addPlayer(player);
                refreshCardViews();
            }
        }

    }

    private void refreshCardViews() {
        final GridLayout cardGridLayout = (GridLayout) findViewById(R.id.layout_cards);
        cardGridLayout.removeAllViews();

        List<Player> players = GameController.getPlayers();

        for (Player player : players) {
            final CardView cardView = new CardView(this);

            cardView.setOnTouchListener(new View.OnTouchListener() {
                private float firstTouchX;
                private float originX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float x = event.getX();

                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        firstTouchX = x;
                        originX = cardView.getX();
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        float currentX = (x - firstTouchX) + originX;
                        cardView.setX(currentX);

                        float alpha = 1 - Math.abs(currentX - originX) / cardView.getWidth();
                        if (alpha < 0) {
                            alpha = 0;
                        }
                        cardView.setAlpha(alpha);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        float currentX = (x - firstTouchX) + originX;
                        if (Math.abs(currentX - originX) >= cardView.getWidth()) {
                            GameController.removePlayer(cardView.getPlayer());
                        }
                        refreshCardViews();
                    }
                    return true;
                }
            });
            cardGridLayout.addView(cardView);
            cardView.init(player);
        }
    }

    private void addActionListeners() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.button_add);
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

    private void initDrawer() {
        String[] drawerTexts = getResources().getStringArray(R.array.drawer_text);

        ListView drawerListView = (ListView) findViewById(R.id.listview_main_left_drawer);
        drawerListView.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, drawerTexts));
    }

}
