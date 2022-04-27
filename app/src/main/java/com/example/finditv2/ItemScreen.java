package com.example.finditv2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;

public class ItemScreen extends AppCompatActivity {
    private TextView showItems;
    private Button backButton;
    private Spinner categorySpinner;
    private Spinner itemOrderSpinner;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;

    /**
     * Creates a new activity with the layout "item_screen" to display the lost items.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        items =FileManager.getObject();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);
        showItems = findViewById(R.id.item_list);
        String items = getItems("All Categories", "Date added ascending");
        displayItems(items);

        RecyclerView recyclerView = findViewById(R.id.recentlyAdded);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewAdapter.ItemClickListener clickListener = new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            @Override
            public void onItemClick(View view, int position) {
                FileManager.removeItem(recyclerViewAdapter.getItem(position));
                recyclerViewAdapter.removeItem(position);
            }
        };
        recyclerView.addOnItemTouchListener(clickListener);
        recyclerViewAdapter.setClickListener(clickListener);


        backButton = findViewById(R.id.button5);
        backButton.setOnClickListener(view -> finish());

        String[] categoryArray = {"All Categories","One","Two"}; //TODO remove and implement properly

        categorySpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displayItems(getItems(categorySpinner.getSelectedItem().toString(), itemOrderSpinner.getSelectedItem().toString()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                displayItems(getItems("All Categories", itemOrderSpinner.getSelectedItem().toString()));
            }
        });


        String[] itemOrderArray = {"Date added ascending","Date added descending","Alphabetical", "Alphabetical reversed"}; //TODO remove and implement properly

        itemOrderSpinner = findViewById(R.id.spinner3);
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, itemOrderArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemOrderSpinner.setAdapter(orderAdapter);

        itemOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                displayItems(getItems(categorySpinner.getSelectedItem().toString(), itemOrderSpinner.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                displayItems(getItems(categorySpinner.getSelectedItem().toString(), "Date added ascending"));
            }
        });


    }

    /**
     * Gets the lost items from the memory. Likely to be changed
     * @return A String of lost items
     */
    /*protected String getItems () {
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
    }*/

    /**
     * Displays only items of a certain category, in a given order
     * @param category The category of item
     * @param order What order the items should be displayed in
     * @return The list of items
     */
    protected String getItems (String category, String order) {
        try {
            List<Item> itemsToSort = items;

            Comparator dateAscending = new Comparator<Item>() {
                @Override
                public int compare(Item item, Item t1) {
                    int comp = item.getDate().compareTo(t1.getDate());
                    return comp;
                }
            };

            Comparator dateDescending = new Comparator<Item>() {
                @Override
                public int compare(Item item, Item t1) {
                    int comp = item.getDate().compareTo(t1.getDate());
                    return -comp;
                }
            };

            Comparator alphabetical = new Comparator<Item>() {
                @Override
                public int compare(Item item, Item t1) {
                    int comp = item.getDescription().compareToIgnoreCase(t1.getDescription());
                    return comp;
                }
            };

            Comparator alphabeticalReversed = new Comparator<Item>() {
                @Override
                public int compare(Item item, Item t1) {
                    int comp = item.getDescription().compareToIgnoreCase(t1.getDescription());
                    return -comp;

                }
            };

           if (order.equals("Alphabetical reversed")) {
               itemsToSort.sort(alphabeticalReversed);
               recyclerViewAdapter.setItemsList(items);
               recyclerViewAdapter.notifyDataSetChanged();
           }

           else if (order.equals("Date added descending")){
               itemsToSort.sort(dateDescending);
               recyclerViewAdapter.setItemsList(items);
               recyclerViewAdapter.notifyDataSetChanged();
           }

           else if (order.equals("Alphabetical")){
               itemsToSort.sort(alphabetical);
               recyclerViewAdapter.setItemsList(items);
               recyclerViewAdapter.notifyDataSetChanged();


           }

           else {
               itemsToSort.sort(dateAscending);
               recyclerViewAdapter.setItemsList(items);
               recyclerViewAdapter.notifyDataSetChanged();
           }


            StringBuilder itemString = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                if(items.get(i).getCategory().equals(category) || category.equals("All Categories")){ //TODO when category is "" it means all items.
                    itemString.append(items.get(i).getDescription());
                    itemString.append(" ");
                    itemString.append(items.get(i).getLocation());
                    itemString.append("\n");
                }
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
        if(items != null) {

            showItems.setText(items);
        }
    }
}
