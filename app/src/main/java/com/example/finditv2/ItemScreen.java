package com.example.finditv2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemScreen extends AppCompatActivity {
    private TextView showItems;
    private Button backButton;
    private Spinner categorySpinner;
    private Spinner itemOrderSpinner;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;
    private List<Item> modifiedListOfItems;

    /**
     * Creates a new activity with the layout "item_screen" to display the lost items.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = FileManager.getObject();
        setContentView(R.layout.view_items);
        //showItems = findViewById(R.id.item_list);
        recyclerViewSetUp();
        backButton = findViewById(R.id.button5);
        backButton.setOnClickListener(view -> finish());
        categoryPickerSetUp();
        OrderPickerSetUp();
    }

    /**
     * Sets up the spinner which is used to pick the sort order of the items displayed
     */
    private void OrderPickerSetUp() {
        String[] itemOrderArray = {"Date added ascending","Date added descending","Alphabetical", "Alphabetical reversed"}; //TODO remove and implement properly

        itemOrderSpinner = findViewById(R.id.spinner3);
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemOrderArray);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemOrderSpinner.setAdapter(orderAdapter);
        itemOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modifyItemsToBeDisplayed(categorySpinner.getSelectedItem().toString(), itemOrderSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                modifyItemsToBeDisplayed(categorySpinner.getSelectedItem().toString(), "Date added ascending");
            }
        });
    }

    /**
     * Setup for the spinner which picks which categories should be shown.
     */
    private void categoryPickerSetUp() {
        List<String> categoryArray = new ArrayList<>();
        for(Category cat : FileManager.getCategories()){
            categoryArray.add(cat.getName());
        }
        categoryArray.add(0, "All Categories");

        categorySpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modifyItemsToBeDisplayed(categorySpinner.getSelectedItem().toString(), itemOrderSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                modifyItemsToBeDisplayed("All Categories", itemOrderSpinner.getSelectedItem().toString());
            }
        });
    }

    /**
     * Setup for the recycler-view
     */
    private void recyclerViewSetUp() {
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
    }

    /**
     * Displays only items of a certain category, in a given order
     * @param category The category of item
     * @param order What order the items should be displayed in
     * @return The list of items
     */
    protected void modifyItemsToBeDisplayed (String category, String order) {
        /*Comparator<Item> dateAscending = Comparator.comparing(Item::getDate);
        Comparator<Item> dateDescending = (item, t1) -> {
            int comp = item.getDate().compareTo(t1.getDate());
            return -comp;
        };
        Comparator<Item> alphabetical = (item, t1) -> item.getDescription().compareToIgnoreCase(t1.getDescription());
        Comparator<Item> alphabeticalReversed = (item, t1) -> {
            int comp = item.getDescription().compareToIgnoreCase(t1.getDescription());
            return -comp;
        };*/
        if(category.equals("All Categories")) {
            modifiedListOfItems = items;
        } else {
            modifiedListOfItems = new ArrayList<>();
            for (Item value : items) {
                if (value.getCategory().equals(category)) {
                    modifiedListOfItems.add(value);
                }
            }
        }
        switch (order) {
            case "Alphabetical reversed":
                modifiedListOfItems.sort((item, t1) -> {
                    int comp = item.getDescription().compareToIgnoreCase(t1.getDescription());
                    return -comp;
                });
                break;
            case "Date added descending":
                modifiedListOfItems.sort((item, t1) -> {
                    int comp = item.getDate().compareTo(t1.getDate());
                    return -comp;
                });
                break;
            case "Alphabetical":
                modifiedListOfItems.sort((item, t1) -> item.getDescription().compareToIgnoreCase(t1.getDescription()));
                break;
            default:
                modifiedListOfItems.sort(Comparator.comparing(Item::getDate));
                break;
        }
        recyclerViewAdapter.setItemsList(modifiedListOfItems);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
