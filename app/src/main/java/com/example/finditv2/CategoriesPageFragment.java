package com.example.finditv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.List;

public class CategoriesPageFragment extends Fragment {

    private Context categoryScreen;
    private CategoryRecyclerViewAdapter recyclerViewAdapter;
    private List<Category> categories;

    public CategoriesPageFragment (Context categoryScreen, List<Category> categories) {
        this.categoryScreen = categoryScreen;
        this.categories = categories;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_page, container, false);
        recyclerViewAdapter = new CategoryRecyclerViewAdapter(categories);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(categoryScreen, 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);
        categories = FileManager.getCategories();
        categories.add(0, new Category("All Categories"));
        return view;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        recyclerViewAdapter.setCount(getItemCount());
    }

    private HashMap<String,Integer> getItemCount () {
        List<Item> items = FileManager.getObject();
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (Item item : items) {
            hashMap.merge(item.getCategory(), 1, Integer::sum);
        }
        hashMap.put(categories.get(0).getName(),items.size());
        return hashMap;
    }
}
