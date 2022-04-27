package com.example.finditv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private List<Item> items;


    // data is passed into the constructor
    RecyclerViewAdapter(Context context) {
        List<String> itemNames = new ArrayList<>();
        List<String> itemCategory = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);

        try {
            items = FileManager.getObject();
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
        if(position<items.size()){
            String item = items.get(position).getDescription();
            String category = items.get(position).getCategory();
            holder.itemName.setText(item);
            holder.itemCategory.setText(category);
            holder.imageView.setImageResource(R.drawable.ic_action_name);
            holder.bind(items.get(position), mClickListener);
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        try{
            return items.size();
        }catch(Exception e){
           return 0;
        }
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
        public void bind(final Item item, final ItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    // convenience method for getting data at click position
    Item getItem(int id) {
        return items.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener extends RecyclerView.OnItemTouchListener {
        void onItemClick(View view, int position);
    }

    public void removeItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }
}
