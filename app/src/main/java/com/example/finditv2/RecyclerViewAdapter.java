package com.example.finditv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> itemNamesList;
    private List<String> itemCatList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ImageView image;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context) {
        List<String> itemNames = new ArrayList<>();
        List<String> itemCategory = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);

        try {
            List<Item> items = FileManager.getObject();
            for (Item item : items) {
                itemNames.add(item.getDescription());
                itemCategory.add(item.getCategory());
            }
        }
        catch (Exception e){

        }

        this.itemCatList = itemCategory;
        this.itemNamesList = itemNames;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_items, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = itemNamesList.get(position);
        String category = itemCatList.get(position);
        holder.itemName.setText(item);
        holder.itemCategory.setText(category);
        holder.imageView.setImageResource(R.drawable.ic_action_name);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return itemNamesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView itemCategory;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView4);
            imageView = itemView.findViewById(R.id.imageView);
            itemCategory = itemView.findViewById(R.id.textView8);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return itemNamesList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
