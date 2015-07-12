package nju.edu.gameofkillers.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.controller.CommonRuler;
import nju.edu.gameofkillers.controller.GameController;
import nju.edu.gameofkillers.views.NumberPickerView;

public class GameSettingActivity extends Activity {
    private CommonRuler commonRuler = new CommonRuler();

    private NumberPickerView killerNumberPicker;
    private NumberPickerView policeNumberPicker;
    private NumberPickerView civilianNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textView = (TextView) findViewById(R.id.textview_player_num);
        textView.setText(getString(R.string.player_number_show1) +
                GameController.getPlayersNum() +
                getString(R.string.player_number_show2));

        commonRuler.refresh();
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

        MenuItem item = menu.findItem(R.id.menu_item_go);
        item.setActionView(R.layout.actionbar_go);

        ImageView goButton = (ImageView) item.getActionView()
                .findViewById(R.id.imagebutton_go);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int killersNum = killerNumberPicker.getNumber();
                int policeNum = policeNumberPicker.getNumber();
                int civilianNum = civilianNumberPicker.getNumber();

                if (killersNum + policeNum + civilianNum !=
                        GameController.getPlayersNum()) {
                    String toast = getString(R.string.identity_number_error);
                    Toast.makeText(GameSettingActivity.this, toast, Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                //todo
//                Intent intent = new Intent(MainActivity.this,
//                        GameSettingActivity.class);
//                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
