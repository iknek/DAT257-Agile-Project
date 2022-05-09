package com.example.finditv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    CategoryScreen categoryScreen;
    List<Category> categories;

    public CategoryRecyclerViewAdapter(CategoryScreen categoryScreen) {
        this.categoryScreen = categoryScreen;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_card,parent,false);
        return new CategoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textView.setText(categories.get(position).getName());
        holder.imageView.setImageResource(R.drawable.no_image);
        holder.itemView.setOnClickListener(view -> categoryScreen.changeActivity(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // Den här classen representerar elementen i recycler viewen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        ImageView imageView = itemView.findViewById(R.id.imageView3);
        TextView textView = itemView.findViewById(R.id.textView7);
    }
}


