package com.example.finditv2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ItemScreen extends AppCompatActivity {

    TextView showItems;
    Button backButton;

    /**
     * Creates a new activity with the layout "item_screen" to display the lost items.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);
        showItems = findViewById(R.id.item_list);
        String items = getItems();
        displayItems(items);

        backButton = findViewById(R.id.button5);
        backButton.setOnClickListener(view -> finish());
    }

    /**
     * Gets the lost items from the memory. Likely to be changed
     * @return A String of lost items
     */
    private String getItems () {
        try {
            List<Item> items = FileManager.getObject();
            StringBuilder itemString = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                itemString.append(items.get(i).getDescription());
                itemString.append("\n");
            }
            return itemString.toString();
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * This method displays all the lost items. Will probably be changed later.
     * @param items A String that contains all the lost items
     */
    private void displayItems(String items) {
        if(items != null)showItems.append(items);
    }
}
