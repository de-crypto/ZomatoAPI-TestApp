package com.example.decrypto.gyandhan;

/**
 * Created by decrypto on 7/1/18.
 */

import java.io.File;
import java.util.List;
import com.example.decrypto.gyandhan.RowItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtname;
        TextView txtid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtid = (TextView) convertView.findViewById(R.id.c_id);
            holder.txtname = (TextView) convertView.findViewById(R.id.c_name);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        // holder.txtDesc.setText(rowItem.getDesc());
        holder.txtid.setText(rowItem.getid());
        holder.txtname.setText(rowItem.getname());

        return convertView;
    }
}
