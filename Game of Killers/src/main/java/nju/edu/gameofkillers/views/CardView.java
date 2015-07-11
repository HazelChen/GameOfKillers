package nju.edu.gameofkillers.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import nju.edu.gameofkillers.R;
import nju.edu.gameofkillers.model.Player;

/**
 * Created by hazel on 2015-07-06.
 */
public class CardView extends FrameLayout{

    public CardView(Context context) {
        this(context, new Player("", null));
    }

    public CardView(Context context, Player player) {
        super(context, null);
        LayoutInflater.from(context).inflate(R.layout.card, this, true);

        TextView textView = (TextView) findViewById(R.id.card_textview_name);
        textView.setText(player.getName());

        if (!player.hasHeader()) {
            return;
        }
        ImageView headerImageView = (ImageView) findViewById(R.id.card_imageview_header);
        Bitmap bitmap = BitmapFactory.decodeFile(player.getHeaderFile());
        headerImageView.setImageBitmap(bitmap);
    }
}
