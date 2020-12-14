package com.regexbyte.councildata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.regexbyte.councildata.Utils.GlideApp;
import com.regexbyte.councildata.Utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.MyViewHolder> {
    public static Context context;
    private List<CompetitionData> items;
    ArrayList<String> arrayList_services;


    public static MyViewHolder.OnItemSkillClickListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView namr,detailsTextView,ui_counts,tv_status;
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

    public CompetitionAdapter(Context context, ArrayList<CompetitionData> cartList, MyViewHolder.OnItemSkillClickListener listener ) {
        this.context = context;
        this.items = cartList;
        this.mListener = listener;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_user, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        CompetitionData postItems = items.get(position);

        arrayList_services=new ArrayList<>();

        holder.namr.setText(postItems.getGroupname());





//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked)
//                {
////
//
//                    arrayList_services.add(postItems.getId());
//                    Log.e("err", String.valueOf(arrayList_services));
//
//                }
//                else
//                {
//
//                    Remove(postItems.getId());
////                    arrayList_services.remove(position);
//                    Log.e("err", String.valueOf(arrayList_services));
//                }
//
//            }
//        });

//            holder.itemView.setOnClickListener( this);

//
//        dateTextView.setText(date);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(context, SubProductDetailActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra(SubProductDetailActivity.POST_IMAGE_LIST,newImageslist);
//                intent.putExtra(SubProductDetailActivity.PRODUCT_PRICES,postItems.getArrayList_prices());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_UNITS,postItems.getArrayList_units());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_UNIT_ID,postItems.getArrayList_units_id());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_PRICES_ID,postItems.getArrayList_prices_id());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_TITLE,postItems.getProduct_title());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_DESCRIPTION,postItems.getProduct_description());
//                intent.putExtra(SubProductDetailActivity.CATEGORY_ID,postItems.getCategory_id());
//                intent.putExtra(SubProductDetailActivity.MERCHANT_ID,postItems.getAdmin_id());
//                intent.putExtra(SubProductDetailActivity.PRODUCT_ID,postItems.getProduct_id());
//
//                context.startActivity(intent);
//            }
//        });

//        ImageUtil.loadImage(GlideApp.with(context), String.valueOf(postItems.getDownloadUrl()), holder.category_image);


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
    public int getItemCount() {

        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);

        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }





}
