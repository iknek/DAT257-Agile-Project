package com.example.finditv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finditv2.Fragments.CategoriesFragment;
import com.example.finditv2.Fragments.ItemsFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    private List<Category> categories;
    private HashMap<String,Integer> hashMap;
    private CategoriesFragment categoriesFragment;

    public CategoryRecyclerViewAdapter(List<Category> categories, HashMap<String,Integer> hashMap, CategoriesFragment categoriesFragment) {
        this.categories = categories;
        this.hashMap = hashMap;
        this.categoriesFragment = categoriesFragment;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_card,parent,false);
        return new CategoryRecyclerViewAdapter.ViewHolder(view);
    }

    public String checkForImagesInItems(Category c){
        String str = "";
        List<Item> items = FileManager.getObject().stream().filter(i -> i.getCategory().equals(c.getName())).collect(Collectors.toList());
        List <String> itemsLeft = items.stream().map(i -> i.getImageUri()).filter(imageUri -> imageUri != null).collect(Collectors.toList());
        if(!itemsLeft.isEmpty()) str = itemsLeft.get(0);
        return str;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        String str = checkForImagesInItems(categories.get(position));
        holder.name.setText(categories.get(position).getName());
        if(str.equals("")) holder.imageView.setImageResource(R.drawable.no_image);
        else holder.imageView.setImageBitmap(FileManager.loadImageFromStorage(str));
        holder.itemView.setOnClickListener(view -> categoriesFragment.categorySelected(categories.get(position).getName()));
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

    public void incrementCount (String category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(category)) {
                Integer count = hashMap.get(category);
                Integer newCount = count == null ? 1 : count + 1;
                hashMap.replace(category,newCount);
                notifyItemChanged(i);
            }
        }
    }

    public void addCategory(Category category) {
        categories.add(category);
        notifyItemInserted(getItemCount() + 1);
    }

    public void removeCategory(Category category) {

    }

    public void decrementCount(String category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getName().equals(category)) {

            }
        }
        notifyItemInserted(getItemCount() + 1);
    }

    public void setHashMap(HashMap<String, Integer> hashMap) {
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


