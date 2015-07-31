package com.hedwig.gameofkillers.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.android.swipedismiss.SwipeDismissTouchListener;
import com.hedwig.gameofkillers.common.Constants;
import com.hedwig.gameofkillers.controller.CommonRuler;
import com.hedwig.gameofkillers.controller.GameController;
import com.hedwig.gameofkillers.controller.Tools;
import com.hedwig.gameofkillers.model.Player;
import com.hedwig.gameofkillers.views.CardView;
import com.hedwig.gameofkillers.views.DrawerListAdapter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.fragment.FeedbackFragment;
import com.umeng.fb.push.FeedbackPush;
import com.hedwig.gameofkillers.R;;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerListView;

    private GridLayout cardGridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //umeng
        FeedbackPush.getInstance(this).init(false);
        MobclickAgent.updateOnlineConfig(this);

        initToolBar();

        addActionListeners();

        cardGridLayout = (GridLayout) findViewById(R.id.layout_cards);
        Tools.averageGridLayoutUnderCard(this, cardGridLayout);

        refreshCardViews();

        initDrawer();

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

    private void refreshCardViews() {
        cardGridLayout.removeAllViews();

        List<Player> players = GameController.getPlayers();

        for (Player player : players) {
            final CardView cardView = new CardView(this);

            cardView.setOnTouchListener(new SwipeDismissTouchListener(
                    cardView,
                    null,
                    new SwipeDismissTouchListener.DismissCallbacks() {
                        @Override
                        public boolean canDismiss(Object token) {
                            return true;
                        }

                        @Override
                        public void onDismiss(View view, Object token) {
                            GameController.removePlayer(cardView.getPlayer());
                            refreshCardViews();
                        }
                    }));
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
        drawerListView = (ListView) findViewById(R.id.listview_main_left_drawer);

        //Init drawer header
        View drawerHeader = getLayoutInflater().inflate(R.layout.drawer_header, null);
        drawerListView.addHeaderView(drawerHeader);

        initDrawerListAdapter(drawerListView);

        addDrawerListListener(drawerListView);

        initDrawerLayout();
    }

    private void initDrawerLayout() {
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

    private void initDrawerListAdapter(ListView drawerListView) {
        String[] drawerTexts = getResources().getStringArray(R.array.drawer_text);
        TypedArray drawerIconsTypedArray = getResources().obtainTypedArray(R.array.drawer_icons);
        int[] drawIcons = new int[drawerTexts.length];
        for (int i = 0; i < drawIcons.length; i++) {
            drawIcons[i] = drawerIconsTypedArray.getResourceId(i, -1);
        }
        drawerIconsTypedArray.recycle();

        ListAdapter listAdapter = new DrawerListAdapter(this, drawerTexts, drawIcons);

        drawerListView.setAdapter(listAdapter);
    }

    private void addDrawerListListener(ListView drawerListView) {
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                    String conversationId = new FeedbackAgent(MainActivity.this)
                            .getDefaultConversation().getId();
                    intent.putExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID,
                            conversationId);
                    startActivity(intent);
                }
            }
        });
        drawerListView.setItemChecked(1, true);
    }

}
