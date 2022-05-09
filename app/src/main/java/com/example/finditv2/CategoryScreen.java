package com.example.finditv2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CategoryScreen extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FileManager(this);
        setContentView(R.layout.category_screen);
        recyclerViewAdapter = new CategoryRecyclerViewAdapter(this);
        categoryRecyclerView = findViewById(R.id.category_rc);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecyclerView.setAdapter(recyclerViewAdapter);
    }

    //callas när aktiviteten startas eller när man kommer tillbaka till activityn, gjorde såhär nu för att updatera listan, kanske finns bättre sätt
    @Override
    protected void onResume() {
        super.onResume();
        //kan göra detta annorlunda senare ev.
        List<Category> categories = FileManager.getCategories();
        if(categories.size() == 0) {
            categories.add(new Category("All items"));
        } else {
            Category firstCategory = categories.get(0);
            categories.add(0, new Category("All items"));
            categories.add(firstCategory);
        }
        recyclerViewAdapter.setCategories(categories);
        //Fixa så den inte uppdaterar alla element
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void changeActivity(int position) {
        if (position == 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
