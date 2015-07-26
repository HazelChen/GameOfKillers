package nju.edu.gameofkillers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.umeng.fb.fragment.FeedbackFragment;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.views.MyFeedbackFragment;

public class FeedbackActivity extends AppCompatActivity {
    private FeedbackFragment mFeedbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_feedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String conversation_id =
                    getIntent().getStringExtra(FeedbackFragment.BUNDLE_KEY_CONVERSATION_ID);
            mFeedbackFragment = FeedbackFragment.newInstance(conversation_id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mFeedbackFragment)
                    .commit();
        }
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
            onBackPressed();
        }

        return true;
    }
}