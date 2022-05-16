package com.example.finditv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemScreen extends Fragment {
    public ItemScreen (Context categoryScreen) {
        this.categoryScreen = categoryScreen;
    }
    private Context categoryScreen;
    private Spinner itemOrderSpinner;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;
    private List<Item> modifiedListOfItems;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_items,container,false);
        items = FileManager.getObject();
        itemOrderSpinner = view.findViewById(R.id.spinner3);
        recyclerView = view.findViewById(R.id.recentlyAdded);
        OrderPickerSetUp();
        recyclerViewSetUp();
        /*
        Bundle bundle = cagetIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("pre_selected_category") != null) {
                modifyItemsToBeDisplayed(bundle.getString("pre_selected_category"), "no order");
            }
        }

         */
        return view;
    }

    /**
     * Sets up the spinner which is used to pick the sort order of the items displayed
     */
    private void OrderPickerSetUp() {
        String[] itemOrderArray = {"Date added ascending","Date added descending","Alphabetical", "Alphabetical reversed"}; //TODO remove and implement properly
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(categoryScreen, android.R.layout.simple_spinner_item, itemOrderArray);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemOrderSpinner.setAdapter(orderAdapter);
        itemOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modifyItemsToBeDisplayed("All Categories", itemOrderSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    /**
     * Setup for the recycler-view
     */
    private void recyclerViewSetUp() {
        recyclerView.setLayoutManager(new LinearLayoutManager(categoryScreen));
        this.recyclerViewAdapter = new RecyclerViewAdapter(categoryScreen);
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewAdapter.ItemClickListener clickListener = new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {            }

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(categoryScreen.getApplicationContext(), DetailedItemView.class);
                startActivity(intent);
                DetailedItemView.giveItem(recyclerViewAdapter.getItem(position));
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
        if(category.equals("All Categories")) {
            modifiedListOfItems = items;
        } else {
            modifiedListOfItems = items.stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList());
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
