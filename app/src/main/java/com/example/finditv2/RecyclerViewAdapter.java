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

    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ImageView image;
    private List<Item> items;

    /**
     * RecyclerViewAdapter constructor
     * @param context
     */
    protected RecyclerViewAdapter(Context context) {
        List<String> itemNames = new ArrayList<>();
        List<String> itemCategory = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);

        items = FileManager.getObject();
        for (Item item : items) {
            itemNames.add(item.getDescription());
            itemCategory.add(item.getCategory());
        }
    }

    /**
     * Inflates the row layout from the XML file if necessary
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_items_card, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data in each row to the TextView
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
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
            } else holder.imageView.setImageResource(R.drawable.no_image);

            holder.bind(items.get(position), mClickListener);
        }
    }

    /**
     * Returns the total number of rows (items)
     * @return the total amount of items
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Stores the View and recycles them as they are scrolled off screen
     */
    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView itemCategory;
        ImageView imageView;
        TextView location;
        private ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView5);
            imageView = itemView.findViewById(R.id.imageView);
            itemCategory = itemView.findViewById(R.id.textView8);
            location = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(this);
        }

        /**
         * Registers clicks in view, calls onItemClick
         * @param view the view that should be clickable
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
        private void bind(final Item item, final ItemClickListener listener) {
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
     * @param id = item id
     * @return the item
     */
    protected Item getItem(int id) {
        return items.get(id);
    }

    /**
     * Allows for itemClickListener to catch events
     * @param itemClickListener = click listener.
     */
    protected void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    protected interface ItemClickListener extends RecyclerView.OnItemTouchListener {
        void onItemClick(View view, int position);
    }

    /**
     * Removes an item from a list of items at a specified position
     * @param position = position of item in view
     */
    protected void removeItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Binds items to itemsList
     * @param itemsList = list of items.
     */
    protected void setItemsList(List<Item> itemsList){
        this.items=itemsList;
    }
}
