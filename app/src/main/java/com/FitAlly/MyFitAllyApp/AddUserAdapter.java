package com.FitAlly.MyFitAllyApp;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.FitAlly.MyFitAllyApp.Utils.GlideApp;
import com.FitAlly.MyFitAllyApp.Utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.MyViewHolder> {
    public static Context context;
    private List<AddUserGroupData> items;
    ArrayList<String> arrayList_services;


    public static MyViewHolder.OnItemSkillClickListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView namr;
        ImageView category_image;


        public MyViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
            namr=view.findViewById(R.id.name);

            category_image=view.findViewById(R.id.imageUser);

        }

        public interface OnItemSkillClickListener {

            void OnItemSkillClickListener(View view, int position, long id, int viewType);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mListener.OnItemSkillClickListener(v, position, getItemId(), getItemViewType());
            }
        }
//
    }
//

    public AddUserAdapter(Context context, ArrayList<AddUserGroupData> userList, MyViewHolder.OnItemSkillClickListener listener ) {
        this.context = context;
        this.items = userList;
        this.mListener = listener;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //when the view is called, it calls the layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_user, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) { //connects the view of the users by binding it to the array list

        AddUserGroupData postItems = items.get(position);

        arrayList_services=new ArrayList<>();

        holder.namr.setText(postItems.getUsername());




        ImageUtil.loadImage(GlideApp.with(context), String.valueOf(postItems.getDownloadUrl()), holder.category_image);


    }


    @Override
    public int getItemCount() { //counts the amount of users that is displayed

        return items.size();
    }







}
