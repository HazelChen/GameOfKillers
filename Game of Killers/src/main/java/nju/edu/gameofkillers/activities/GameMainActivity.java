package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.controller.Tools;
import nju.edu.gameofkillers.model.GameResult;
import nju.edu.gameofkillers.model.Identity;
import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.views.CardView;

import java.util.List;


public class GameMainActivity extends Activity {
    private RelativeLayout gameResultLayout;
    private boolean showResult;

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
                        return;
                    }

                    //If player is not dead.
                    deadImageView = checkAndInitDeadImageView(deadImageView,
                            cardView.getShouldWidth(),
                            cardView.getShouldHeight());
                    cardView.addView(deadImageView);
                    player.setIsDead(true);
                    checkGameStatusAndReaction();
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

    @Override
    public void onBackPressed() {
        if (showResult) {
            showResult = false;
            FrameLayout gameMainFrameLayout =
                    (FrameLayout) findViewById(R.id.framelayout_game_main);
            gameMainFrameLayout.removeView(gameResultLayout);
        } else {
            super.onBackPressed();
        }

    }


    private ImageView checkAndInitDeadImageView(ImageView deadImageView,
                                           int width, int height) {
        if (deadImageView == null) {
            ImageView newDeadImageView = new ImageView(GameMainActivity.this);
            newDeadImageView.setImageResource(R.drawable.dead);
            FrameLayout.LayoutParams layoutParams =
                    new FrameLayout.LayoutParams(width, height);
            newDeadImageView.setLayoutParams(layoutParams);
            return newDeadImageView;
        }
        return deadImageView;
    }

    private void checkGameStatusAndReaction() {
        GameResult gameResult = GameController.getResult();

        //if someone won.
        if (gameResult != GameResult.ING) {
            checkAndInitGameResultLayout(gameResult.getWinner());

            FrameLayout gameMainFrameLayout =
                    (FrameLayout) findViewById(R.id.framelayout_game_main);
            gameMainFrameLayout.addView(gameResultLayout);
            showResult = true;
        }
    }

    private void checkAndInitGameResultLayout(Identity winner) {
        if (gameResultLayout == null) {
            gameResultLayout = new RelativeLayout(GameMainActivity.this);
            LayoutInflater.from(GameMainActivity.this)
                    .inflate(R.layout.layout_game_end, gameResultLayout, true);
            Button restartButton =
                    (Button) gameResultLayout.findViewById(R.id.button_end_restart);
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameMainActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    GameController.allAlive();
                }
            });
        }

        TextView winnerTextView = (TextView)
                gameResultLayout.findViewById(R.id.textview_end_winner);
        String winnerString = Tools.getIdentityName(this, winner);
        winnerTextView.setText(winnerString);
    }
}
