package nju.edu.gameofkillers.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import nju.edu.gameofkillers.R;

/**
 * Created by hazel on 2015-07-12.
 */
public class NumberPickerView extends LinearLayout{

    public NumberPickerView(Context context) {
        this(context, null);
    }

    public NumberPickerView (Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.number_picker, this, true);

        ImageButton addButton = (ImageButton)findViewById(R.id.imagebutton_numberpicker_add);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edittext_numberpicker_number);
                String numString = editText.getText().toString();
                int num = Integer.parseInt(numString);
                num++;
                editText.setText(String.valueOf(num));
            }
        });

        ImageButton minusButton = (ImageButton)findViewById(R.id.imagebutton_numberpicker_minus);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edittext_numberpicker_number);
                String numString = editText.getText().toString();
                int num = Integer.parseInt(numString);
                num--;

                if (num < 0) {
                    num = 0;
                }
                editText.setText(String.valueOf(num));
            }
        });
    }

    public void init(int initNumber) {
        EditText editText = (EditText) findViewById(R.id.edittext_numberpicker_number);
        editText.setText(String.valueOf(initNumber));
    }

    public int getNumber() {
        EditText editText = (EditText) findViewById(R.id.edittext_numberpicker_number);
        String numString = editText.getText().toString();
        return Integer.parseInt(numString);
    }
}
