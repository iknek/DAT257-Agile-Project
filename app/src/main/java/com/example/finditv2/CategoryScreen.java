package com.example.finditv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryScreen extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private List<Category> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileManager(this);
        categories = FileManager.getCategories();
        setContentView(R.layout.category_screen);
        Context context = getBaseContext();
        Fragment itemScreen = new ItemScreen(context);
        Fragment addItemScreen = new AddItemScreen(context);
        Fragment categoriesPageFragment = new CategoriesPageFragment(context, categories);

        bottomNavigation = findViewById(R.id.bottom_navigation1);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;
                switch (item.getItemId()) {
                    case R.id.categories:
                        selectFragment = categoriesPageFragment;
                        break;
                    case R.id.main_screen:
                        selectFragment = itemScreen;
                        break;
                    case R.id.add_item:
                        selectFragment = addItemScreen;
                        break;
                    default: return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                return false;

            }
        });
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
