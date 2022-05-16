package com.example.finditv2;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.finditv2.Fragments.AddNewFragment;
import com.example.finditv2.Fragments.CategoriesFragment;
import com.example.finditv2.Fragments.ItemsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Category> categories;
    private Fragment itemScreen;
    private Fragment addItemScreen;
    private Fragment categoriesPageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileManager(this);
        categories = FileManager.getCategories();
        setContentView(R.layout.main_activity);
        Context context = getBaseContext();
        itemScreen = new ItemsFragment(context);
        addItemScreen = new AddNewFragment(context);
        categoriesPageFragment = new CategoriesFragment(context, categories);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,itemScreen).commit();
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation1);
        bottomNavigation.setSelectedItemId(R.id.main_screen);
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectFragment = null;
            switch (item.getOrder()) {
                case 1:
                    selectFragment = categoriesPageFragment;
                    break;
                case 2:
                    selectFragment = itemScreen;
                    break;
                case 3:
                    selectFragment = addItemScreen;
                    break;
                default: return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
            return true;
        });
    }

    public void updateItemCounts () {
        categoriesPageFragment.updateNumbers(getItemCount());
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
