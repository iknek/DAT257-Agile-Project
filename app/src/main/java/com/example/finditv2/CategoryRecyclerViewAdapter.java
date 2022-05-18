package com.example.finditv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories;
    private HashMap<String,Integer> hashMap;

    public CategoryRecyclerViewAdapter(List<Category> categories, HashMap<String,Integer> hashMap) {
        this.categories = categories;
        this.hashMap = hashMap;

    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_card,parent,false);
        return new CategoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getName());
        holder.imageView.setImageResource(R.drawable.no_image);
        //holder.itemView.setOnClickListener(view -> categoryScreen.changeActivity(position));
        Integer count = hashMap.get(categories.get(position).getName());
        if (count != null) {
            holder.count.setText("Items: " + hashMap.get(categories.get(position).getName()));
        } else {
            holder.count.setText("Items: 0");
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    public void setCount(HashMap<String,Integer> hashMap) {
        this.hashMap = hashMap;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView = itemView.findViewById(R.id.imageView3);
        private final TextView name = itemView.findViewById(R.id.textView7);
        private final TextView count = itemView.findViewById(R.id.textView11);

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}


