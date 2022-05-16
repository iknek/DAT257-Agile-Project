package com.example.finditv2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends Fragment {
    public MainActivity (CategoryScreen categoryScreen) {
        this.categoryScreen = categoryScreen;
    }

    private CategoryScreen categoryScreen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = container.findViewById(R.id.recentlyAdded);
        recyclerView.setLayoutManager(new LinearLayoutManager(categoryScreen));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(categoryScreen);
        recyclerView.setAdapter(adapter);
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    /**
     * Opens new_item_screen on button click.
     * @param view
     */
    public void openItemMenu(View view) {
        Intent intent = new Intent(categoryScreen.getApplicationContext(), AddItemScreen.class);
        startActivity(intent);
    }

    /**
     * Changes Activity to one where the Items are displayed in a list, the ItemScreen.
     * @param view a reference to the view which the method is connected to.
     */
    public void goToItemScreen(View view) {
        Intent intent = new Intent(categoryScreen.getApplicationContext(), ItemScreen.class);
        startActivity(intent);
    }

    /**
     * Opens add_category_screen on button click.
     * @param view a reference to the view which the method is connected to.
     */
    public void goToAddCategoryScreen(View view){
        Intent intent = new Intent(categoryScreen.getApplicationContext(), AddCategoryScreen.class);
        startActivity(intent);
    }
}