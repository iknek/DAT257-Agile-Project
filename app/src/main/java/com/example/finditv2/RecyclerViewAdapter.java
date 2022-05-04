package com.example.finditv2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
    public List<Item> items;

    // data is passed into the constructor

    /**
     * RecyclerViewAdapter constructor
     * @param context
     */
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

    /**
     * Inflates the row layout from the XML file if necessary
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_items_card, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row

    /**
     * Binds the data in each row to the TextView
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position<items.size()){
            String item = items.get(position).getDescription();
            String category = items.get(position).getCategory();
            holder.itemName.setText(item);
            holder.itemCategory.setText(category);
            holder.location.setText(items.get(position).getLocation());

            if (items.get(position).getImageUri() != null) {
                holder.imageView.setImageBitmap(FileManager.loadImageFromStorage(items.get(position).getImageUri()));
            } else {

                // lägga till defaultbiler till olika kategorier
            }

            holder.bind(items.get(position), mClickListener);
        }
    }

    // total number of rows

    /**
     * Returns the total number of rows (items)
     * @return
     */
    @Override
    public int getItemCount() {
        try{
            return items.size();
        }catch(Exception e){
           return 0;
        }
    }


    // stores and recycles views as they are scrolled off screen

    /**
     * Stores the View and recycles them as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView itemCategory;
        ImageView imageView;
        TextView location;
        ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView5);
            imageView = itemView.findViewById(R.id.imageView);
            itemCategory = itemView.findViewById(R.id.textView8);
            location = itemView.findViewById(R.id.textView5);
            itemView.setOnClickListener(this);
        }

        /**
         * Registers clicks in view, calls onItemClick
         * @param view
         */
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        /**
         * Binds an item to a listener
         * @param item
         * @param listener
         */
        public void bind(final Item item, final ItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
        }
    }

    // convenience method for getting data at click position

    /**
     * Method for return an item at a specific position equal to id
     * @param id
     * @return
     */
    Item getItem(int id) {
        return items.get(id);
    }

    // allows clicks events to be caught

    /**
     * Allows for itemClickListener to catch events
     * @param itemClickListener
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener extends RecyclerView.OnItemTouchListener {
        void onItemClick(View view, int position);
    }

    /**
     * Removes an item from a list of items at a specified position
     * @param position
     */
    public void removeItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Binds items to itemsList
     * @param itemsList
     */
    public void setItemsList(List<Item> itemsList){
        this.items=itemsList;
    }
}
