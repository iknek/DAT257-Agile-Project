package com.example.finditv2;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

    private Context context;

    public CategoryRecyclerViewAdapter(List<Category> categories, HashMap<String,Integer> hashMap, CategoriesFragment categoriesFragment, Context context) {
        this.categories = categories;
        this.hashMap = hashMap;
        this.categoriesFragment = categoriesFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items_card,parent,false);
        return new CategoryRecyclerViewAdapter.ViewHolder(view);
    }

    public String checkForImagesInItems(Category c){
        String str;
        List<Item> items = FileManager.getObject().stream().filter(i -> i.getCategory().equals(c.getName())).collect(Collectors.toList());
        List <String> itemsLeft = items.stream().map(i -> i.getImageUri()).filter(imageUri -> imageUri != null).collect(Collectors.toList());
        if (c.getName().equalsIgnoreCase("All Categories")) str = "all";
        else if(!itemsLeft.isEmpty()) str = itemsLeft.get(0);
        else                          str = Character.toString(c.getName().charAt(0));
        return str;
    }
    public void setImages(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position){
        String str = checkForImagesInItems(categories.get(position));
        if(str.length() == 1) str = str.toLowerCase();
        switch (str){
            case "all":holder.imageView.setImageResource(R.drawable.all); break;
            case "a": holder.imageView.setImageResource(R.drawable.a); break;
            case "b": holder.imageView.setImageResource(R.drawable.b); break;
            case "c": holder.imageView.setImageResource(R.drawable.c); break;
            case "d": holder.imageView.setImageResource(R.drawable.d); break;
            case "e": holder.imageView.setImageResource(R.drawable.e); break;
            case "f": holder.imageView.setImageResource(R.drawable.f); break;
            case "g": holder.imageView.setImageResource(R.drawable.g); break;
            case "h": holder.imageView.setImageResource(R.drawable.h); break;
            case "i": holder.imageView.setImageResource(R.drawable.i); break;
            case "j": holder.imageView.setImageResource(R.drawable.j); break;
            case "k": holder.imageView.setImageResource(R.drawable.k); break;
            case "l": holder.imageView.setImageResource(R.drawable.l); break;
            case "m": holder.imageView.setImageResource(R.drawable.m); break;
            case "n": holder.imageView.setImageResource(R.drawable.n); break;
            case "o": holder.imageView.setImageResource(R.drawable.o); break;
            case "p": holder.imageView.setImageResource(R.drawable.p); break;
            case "q": holder.imageView.setImageResource(R.drawable.q); break;
            case "r": holder.imageView.setImageResource(R.drawable.r); break;
            case "s": holder.imageView.setImageResource(R.drawable.s); break;
            case "t": holder.imageView.setImageResource(R.drawable.t); break;
            case "u": holder.imageView.setImageResource(R.drawable.u); break;
            case "v": holder.imageView.setImageResource(R.drawable.v); break;
            case "w": holder.imageView.setImageResource(R.drawable.w); break;
            case "x": holder.imageView.setImageResource(R.drawable.x); break;
            case "y": holder.imageView.setImageResource(R.drawable.y); break;
            case "z": holder.imageView.setImageResource(R.drawable.z); break;
            default: holder.imageView.setImageBitmap(FileManager.loadImageFromStorage(str)); break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getName());
        holder.itemView.setOnClickListener(view -> categoriesFragment.categorySelected(categories.get(position).getName()));
        setImages(holder, position);
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


