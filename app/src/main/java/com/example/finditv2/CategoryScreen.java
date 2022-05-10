package com.example.finditv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class CategoryScreen extends AppCompatActivity {

    private CategoryRecyclerViewAdapter recyclerViewAdapter;
    private BottomNavigationView bottomNavigation;
    private List<Category> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileManager(this);
        setContentView(R.layout.category_screen);
        recyclerViewAdapter = new CategoryRecyclerViewAdapter(this);
        RecyclerView categoryRecyclerView = findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);



        bottomNavigation = findViewById(R.id.bottom_navigation1);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.categories:

                        break;
                    case R.id.main_screen:
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent2);
                        //ta bort default animation vid byte av activity, kan göras på snyggare sätt
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.add_item:
                        Intent intent3 = new Intent(getApplicationContext(), AddItemScreen.class);
                        startActivity(intent3);
                        //ta bort default animation vid byte av activity, kan göras på snyggare sätt
                        overridePendingTransition(0, 0);
                        break;
                    default: return false;
                }
                return false;
            }
        });
    }

    //callas när aktiviteten startas eller när man kommer tillbaka till activityn, gjorde såhär nu för att updatera listan, kanske finns bättre sätt
    @Override
    protected void onResume() {
        super.onResume();
        //kan göra detta annorlunda senare ev.
        categories = FileManager.getCategories();
        if(categories.size() == 0) {
            categories.add(new Category("All Categories"));
        } else {
            Category firstCategory = categories.get(0);
            categories.add(0, new Category("All Categories"));
            categories.add(firstCategory);
        }
        recyclerViewAdapter.setCategories(categories);
        //Fixa så den inte uppdaterar alla element
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void changeActivity(int position) {
        Intent intent = new Intent(getApplicationContext(), ItemScreen.class);
        intent.putExtra("pre_selected_category", categories.get(position).getName());
        startActivity(intent);
        //ta bort default animation vid byte av activity, kan göras på snyggare sätt
        overridePendingTransition(0, 0);
    }
}
