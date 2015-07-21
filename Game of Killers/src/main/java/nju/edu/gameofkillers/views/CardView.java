package nju.edu.gameofkillers.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.controller.ImageDecoder;
import nju.edu.gameofkillers.controller.Tools;
import nju.edu.gameofkillers.model.Identity;
import nju.edu.gameofkillers.model.Player;

import java.util.ResourceBundle;

/**
 * Created by hazel on 2015-07-06.
 */
public class CardView extends FrameLayout {
    private Player player;

    public CardView(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.card, this, true);
    }

    public CardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.card, this, true);
    }

    public void init(Player player) {
        this.player = player;

        TextView textView = (TextView) findViewById(R.id.card_textview_name);
        textView.setText(player.getName());

        if (!player.hasHeader()) {
            return;
        }

        ImageView headerImageView = (ImageView) findViewById(R.id.card_imageview_header);

        String headerFilePath = player.getHeaderFile();
        int requireWidth = headerImageView.getLayoutParams().width;
        int requireHeight = headerImageView.getLayoutParams().height;
        Bitmap bitmap = ImageDecoder.decodeWithCompression(headerFilePath,
                requireWidth, requireHeight);

        headerImageView.setImageBitmap(bitmap);
    }

    public void initWithIdentity(Player player, Context context) {
        init(player);

        Identity identity = player.getIdentity();
        String identityString = Tools.getIdentityName(context, identity);

        TextView textView = (TextView) findViewById(R.id.card_textview_identity);
        textView.setVisibility(VISIBLE);
        textView.setText(identityString);
    }

    public Player getPlayer() {
        return player;
    }

    public int getShouldWidth() {
        View backgroundImageView = findViewById(R.id.imageview_card_background);
        return backgroundImageView.getLayoutParams().width;
    }

    public int getShouldHeight() {
        View backgroundImageView = findViewById(R.id.imageview_card_background);
        return backgroundImageView.getLayoutParams().height;
    }

}
