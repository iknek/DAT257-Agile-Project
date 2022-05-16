package com.example.finditv2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finditv2.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private final Context mainActivityContext;
    private CategoryRecyclerViewAdapter recyclerViewAdapter;
    private List<Category> categories;
    private FloatingActionButton fab;

    public CategoriesFragment(Context context, List<Category> categories) {
        this.mainActivityContext = context;
        this.categories = categories;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_page, container, false);
        recyclerViewAdapter = new CategoryRecyclerViewAdapter(categories);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(mainActivityContext, 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);
        fab = view.findViewById(R.id.floatingActionButton);
        categories = FileManager.getCategories();
        categories.add(0, new Category("All Categories"));
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(mainActivityContext,AddCategoryScreen.class);
            startActivity(intent);
        });
        return view;
    }

    public void updateNumbers (HashMap<String,Integer> hashMap) {
        recyclerViewAdapter.setCount(hashMap);
    }
}
