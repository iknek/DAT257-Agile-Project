package com.example.finditv2.Fragments;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finditv2.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private CategoryRecyclerViewAdapter recyclerViewAdapter;
    private messagesFromFragments update;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        update = (messagesFromFragments) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_page, container, false);
        List<Category> categories = FileManager.getCategories();
        categories.add(0, new Category("All Categories"));
        recyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, getItemCount(), this,getContext());
        RecyclerView categoryRecyclerView = view.findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view1 -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new NewCategoryPage());
            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
            transaction.commit();
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewAdapter.setHashMap(getItemCount());
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private HashMap<String,Integer> getItemCount () {
        List<Item> items = FileManager.getObject();
        HashMap<String, Integer> hashMap = new HashMap<>();
        try {
            for (Item item : items) {
                hashMap.merge(item.getCategory(), 1, Integer::sum);
            }
            hashMap.put("All Categories",items.size());
        }
        catch (Exception ignored){};
        return hashMap;
    }

    public void categorySelected(String string) {
        update.categorySelected(string);
    }
}
