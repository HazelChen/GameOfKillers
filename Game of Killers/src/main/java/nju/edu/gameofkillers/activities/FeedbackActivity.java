package nju.edu.gameofkillers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.mail.GMailSender;
import nju.edu.gameofkillers.R;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            sendFeedback();
            onBackPressed();
            return true;
        } else if (id == R.id.menu_item_go) {
            sendFeedback();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendFeedback() {
        EditText contentEditText = (EditText) findViewById(R.id.edittext_feedback_content);
        String content = contentEditText.getText().toString();
        if (content.equals("")) {
            return;
        }

        GMailSender mailSender = new GMailSender("442166178@qq.com", "");
        mailSender.sendMail("", "", "", "");
    }
}
