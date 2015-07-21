package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.views.CardView;

import java.util.List;


public class GameMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        initPlayers();
        initHintTextView();
    }

    private void initHintTextView() {
        final TextView hintTextView = (TextView) findViewById(R.id.textview_game_hint);
        LinearLayout hintLinearLayout = (LinearLayout) findViewById(R.id.linearlayout_game_hint);

        hintLinearLayout.setOnClickListener(new View.OnClickListener() {
            private String[] hints = getResources().getStringArray(R.array.game_hint);
            private int index = 0;

            @Override
            public void onClick(View v) {
                hintTextView.setText(hints[index]);
                index++;
                if (index == hints.length) {
                    index = 0;
                }
            }
        });

    }

    private void initPlayers() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.layout_game_main_cards);

        List<Player> players = GameController.getPlayers();

        for (final Player player : players) {
            final CardView cardView = new CardView(this);
            cardView.setOnClickListener(new View.OnClickListener() {
                private ImageView deadImageView;

                @Override
                public void onClick(View v) {
                    if (player.isDead()) {
                        cardView.removeView(deadImageView);
                        player.setIsDead(false);
                    } else if (deadImageView == null) {
                        deadImageView = new ImageView(GameMainActivity.this);
                        deadImageView.setImageResource(R.drawable.dead);
                        FrameLayout.LayoutParams layoutParams =
                                new FrameLayout.LayoutParams(
                                        cardView.getShouldWidth(),
                                        cardView.getShouldHeight());
                        deadImageView.setLayoutParams(layoutParams);
                        cardView.addView(deadImageView);
                        player.setIsDead(true);
                    } else {
                        cardView.addView(deadImageView);
                        player.setIsDead(true);
                    }
                }
            });
            gridLayout.addView(cardView);
            cardView.initWithIdentity(player, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
