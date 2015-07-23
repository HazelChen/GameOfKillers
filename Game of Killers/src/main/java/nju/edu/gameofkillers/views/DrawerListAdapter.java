package nju.edu.gameofkillers.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import nju.edu.gameofkillers.R;


/**
 * Created by hazel on 2015-07-23.
 */
public class DrawerListAdapter extends BaseAdapter{
    private Context context;
    private String[] listItemsTitles;
    private int[] listItemsIcons;

    public DrawerListAdapter(Context context,
                             String[] listItemsTitles,
                             int[] listItemsIcons) {
        this.context = context;
        this.listItemsTitles = listItemsTitles;
        this.listItemsIcons = listItemsIcons;
    }

    @Override
    public int getCount() {
        return listItemsTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return listItemsTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(listItemsIcons[position]);
        txtTitle.setText(listItemsTitles[position]);

        return convertView;
    }
}
