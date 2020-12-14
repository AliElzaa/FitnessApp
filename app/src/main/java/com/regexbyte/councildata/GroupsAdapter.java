package com.regexbyte.councildata;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class GroupsAdapter extends ArrayAdapter<CompetitionData> {

    Activity context;
    int resource, textViewResourceId;
    List<CompetitionData> items, tempItems, suggestions;
    private Dialog pinDialog;
    private EditText popup_title, popup_review;
    private TextView submit;
    private RatingBar rating;

    public GroupsAdapter(Activity mainActivity, ArrayList<CompetitionData> dataArrayList) {
        super(mainActivity, 0, dataArrayList);
        this.context = mainActivity;
        this.items = dataArrayList;
    }
    private class ViewHolder {
        TextView description, name;
        ImageView image;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupsAdapter.ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_my_group_list, parent, false);
            holder = new GroupsAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            //holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.image = (ImageView) convertView.findViewById(R.id.imageUser);
            convertView.setTag(holder);
        } else {
            holder = (GroupsAdapter.ViewHolder) convertView.getTag();
        }
        CompetitionData productItems = items.get(position);
        holder.name.setText(productItems.getGroupname());
        // holder.description.setText(productItems.getEmial());
        // holder.image.setImageResource(productItems.getImage());
//        ImageUtil.loadImage(GlideApp.with(context), String.valueOf(productItems.getDownloadUrl()), holder.image);
        return convertView;
    }
}