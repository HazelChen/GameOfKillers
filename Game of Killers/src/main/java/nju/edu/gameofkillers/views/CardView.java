package nju.edu.gameofkillers.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.controller.ImageDecoder;
import nju.edu.gameofkillers.model.Player;

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

    public Player getPlayer() {
        return player;
    }

}
