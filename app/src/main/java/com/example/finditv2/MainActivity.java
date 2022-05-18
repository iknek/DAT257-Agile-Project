package com.example.finditv2;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.finditv2.Fragments.AddNewFragment;
import com.example.finditv2.Fragments.CategoriesFragment;
import com.example.finditv2.Fragments.ItemsFragment;
import com.example.finditv2.Fragments.NewCategoryPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Category> categories;
    private Fragment itemScreen;
    private Fragment addItemScreen;
    private Fragment categoriesFragment;
    private Fragment newCategoryPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Context context = getBaseContext();

        new FileManager(this);
        categories = FileManager.getCategories();
        categoriesFragment = new CategoriesFragment(context, categories, getItemCount());
        newCategoryPage = new NewCategoryPage();
        itemScreen = new ItemsFragment(context);
        addItemScreen = new AddNewFragment(context);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,itemScreen).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment,tag).commit();
            return true;
        });
    }

    private void updateCategoriesNumbers () {
        CategoriesFragment fragment = (CategoriesFragment) getSupportFragmentManager().findFragmentByTag("categoriesFragment");
        if (fragment != null) {
            fragment.updateNumbers(getItemCount());
        }
    }

    private HashMap<String,Integer> getItemCount () {
        List<Item> items = FileManager.getObject();
        HashMap<String, Integer> hashMap = new HashMap<>();
        try {
            for (Item item : items) {
                hashMap.merge(item.getCategory(), 1, Integer::sum);
            }
            hashMap.put(categories.get(0).getName(),items.size());
        }
        catch (Exception ignored){};

        return hashMap;
    }
}
