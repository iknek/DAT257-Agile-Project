package com.example.finditv2;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.finditv2.Fragments.AddNewFragment;
import com.example.finditv2.Fragments.CategoriesFragment;
import com.example.finditv2.Fragments.ItemsFragment;
import com.example.finditv2.Fragments.NewCategoryPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements messagesFromFragments{

    private Fragment itemScreen;
    private Fragment addItemScreen;
    private Fragment categoriesFragment;
    private Fragment newCategoryPage;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileManager(getApplicationContext());
        setContentView(R.layout.main_activity);
        categoriesFragment = new CategoriesFragment();
        newCategoryPage = new NewCategoryPage();
        itemScreen = new ItemsFragment();
        addItemScreen = new AddNewFragment();
        fragmentManager.beginTransaction().add(R.id.fragment_container,itemScreen).commit();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation1);
        bottomNavigation.setSelectedItemId(R.id.main_screen);
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectFragment;
            String tag;
            switch (item.getOrder()) {
                case 1:
                    selectFragment = categoriesFragment;
                    tag = "categoriesFragment";
                    break;
                case 2:
                    selectFragment = itemScreen;
                    tag = "itemScreen";
                    break;
                case 3:
                    selectFragment = addItemScreen;
                    tag = "addItemScreen";
                    break;
                default: return true;
            }
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container,selectFragment,tag).commit();
            return true;
        });
    }

    @Override
    public void categorySelected(String category) {
        ItemsFragment fragment = (ItemsFragment) fragmentManager.findFragmentByTag("itemScreen");
        if (fragment != null) {
            fragment.setCategory(category);
        }
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container,itemScreen,"itemScreen").commit();
        fragment = (ItemsFragment) fragmentManager.findFragmentByTag("itemScreen");
        fragment.setCategory(category);
    }
}
