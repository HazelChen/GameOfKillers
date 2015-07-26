package nju.edu.gameofkillers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.common.Constants;
import nju.edu.gameofkillers.controller.CommonRuler;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.views.NumberPickerView;

public class GameSettingActivity extends AppCompatActivity {
    private CommonRuler commonRuler;

    private NumberPickerView killerNumberPicker;
    private NumberPickerView policeNumberPicker;
    private NumberPickerView civilianNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.textview_player_num);
        textView.setText(getString(R.string.player_number_show1) +
                GameController.getPlayersNum() +
                getString(R.string.player_number_show2));

        commonRuler = new CommonRuler();
        killerNumberPicker =
                (NumberPickerView) findViewById(R.id.numberpicker_killernum);
        killerNumberPicker.init(commonRuler.getKillersNum());
        policeNumberPicker =
                (NumberPickerView) findViewById(R.id.numberpicker_policenum);
        policeNumberPicker.init(commonRuler.getPolicesNum());
        civilianNumberPicker =
                (NumberPickerView) findViewById(R.id.numberpicker_civiliannum);
        civilianNumberPicker.init(commonRuler.getCiviliansNum());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_item_go) {
            int killersNum = killerNumberPicker.getNumber();
            int policeNum = policeNumberPicker.getNumber();
            int civilianNum = civilianNumberPicker.getNumber();

            if (killersNum + policeNum + civilianNum !=
                    GameController.getPlayersNum()) {
                String toast = getString(R.string.identity_number_error);
                Toast.makeText(GameSettingActivity.this, toast, Toast.LENGTH_SHORT)
                        .show();
                return true;
            }

            CommonRuler commonRuler = new CommonRuler(killersNum, policeNum, civilianNum);
            GameController.arrangeIdentity(commonRuler);

            Intent intent = new Intent(GameSettingActivity.this,
                    ViewIdnetityActivity.class);
            intent.putExtra(Constants.KEY_PLAYER_INDEX, 0);
            startActivity(intent);
        }

        return true;
    }
}
