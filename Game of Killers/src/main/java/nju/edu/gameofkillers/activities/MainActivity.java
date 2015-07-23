package nju.edu.gameofkillers.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.CommonRuler;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.model.Player;
import nju.edu.gameofkillers.views.CardView;
import android.support.v7.widget.Toolbar;
import nju.edu.gameofkillers.views.DrawerListAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        addActionListeners();

        refreshCardViews();

        initDrawer();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
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
        //Init drawer list adapter
        String[] drawerTexts = getResources().getStringArray(R.array.drawer_text);
        TypedArray drawerIconsTypedArray = getResources().obtainTypedArray(R.array.drawer_icons);
        int[] drawIcons = new int[drawerTexts.length];
        for (int i = 0; i < drawIcons.length; i++) {
            drawIcons[i] = drawerIconsTypedArray.getResourceId(i, -1);
        }
        drawerIconsTypedArray.recycle();

        ListAdapter listAdapter = new DrawerListAdapter(this, drawerTexts, drawIcons);
        ListView drawerListView = (ListView) findViewById(R.id.listview_main_left_drawer);
        drawerListView.setAdapter(listAdapter);

        //Add drawer header
        View drawerHeader = getLayoutInflater().inflate(R.layout.drawer_header, null);
        drawerListView.addHeaderView(drawerHeader);

        //Init drawerListener
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout_main);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme900));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setHomeAsUpIndicator(R.drawable.hamburger_menu);
    }

}
