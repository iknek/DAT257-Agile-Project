package com.example.finditv2.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private HashMap<String,Integer> count;

    public CategoriesFragment(Context context, List<Category> categories, HashMap<String,Integer> count) {
        this.mainActivityContext = context;
        this.categories = categories;
        this.count = count;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_page, container, false);

        categories = FileManager.getCategories();
        categories.add(0, new Category("All Categories"));

        recyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, count);
        RecyclerView categoryRecyclerView = view.findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(mainActivityContext, 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);

        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NewCategoryPage());
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateNumbers (HashMap<String,Integer> hashMap) {
        count = hashMap;
        recyclerViewAdapter.setCount(count);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
