package com.example.finditv2;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    /**
     * Run on app start. Sets the current view window, and makes a file manager which takes in the current context.
     * @param savedInstanceState = something or other.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recentlyAdded);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Opens new_item_screen on button click.
     * @param view
     */
    public void openItemMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), AddItemScreen.class);
        startActivity(intent);
    }

    /**
     * Changes Activity to one where the Items are displayed in a list, the ItemScreen.
     * @param view a reference to the view which the method is connected to.
     */
    public void goToItemScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ItemScreen.class);
        startActivity(intent);
    }

    /**
     * Opens add_category_screen on button click.
     * @param view a reference to the view which the method is connected to.
     */
    public void goToAddCategoryScreen(View view){
        Intent intent = new Intent(getApplicationContext(), AddCategoryScreen.class);
        startActivity(intent);
    }
}