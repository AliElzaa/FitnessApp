package com.FitAlly.MyFitAllyApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Custom implementation of how to display list using the data saved on firebase
public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.MyViewHolder> { //overwriting the recycler view
    public static Context context;
    private List<CompetitionData> items;
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
//        public void showImage()
//        {
//            img_service.setVisibility(View.VISIBLE);
//        }
    }
//    public HabitTestAdapter(List<HabitModel> itemsNew, OnItemClickListener listener) {
//        this.itemsNew = itemsNew;
//        this.listener = listener;
//    }

    public CompetitionAdapter(Context context, ArrayList<CompetitionData> userList, MyViewHolder.OnItemSkillClickListener listener ) { //constructor for the class
        this.context = context;
        this.items = userList;
        this.mListener = listener;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_user, parent, false); //each item will look as the XML file


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) { //binding the data with the list of array items

        CompetitionData postItems = items.get(position);

        arrayList_services=new ArrayList<>();

        holder.namr.setText(postItems.getGroupname());


    }

    public ArrayList get_array_services()
    {
        return arrayList_services;
    }
    public void Remove(String id)
    {
        int index= arrayList_services.indexOf(id);
        arrayList_services.remove(index);
    }

    @Override
    public int getItemCount() { //getting the count

        return items.size();
    }



}
