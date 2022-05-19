package com.example.finditv2.Fragments;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finditv2.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemsFragment extends Fragment {
    private Spinner itemOrderSpinner;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> items;
    private List<Item> modifiedListOfItems;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private String category = "All Categories";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_items,container,false);
        items = FileManager.getObject();
        itemOrderSpinner = view.findViewById(R.id.spinner3);
        recyclerView = view.findViewById(R.id.recentlyAdded);
        searchView = view.findViewById(R.id.itemSearch);
        searchSetup();

        OrderPickerSetUp();
        recyclerViewSetUp();
        return view;
    }

    private void searchSetup(){

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {

                    // Override onQueryTextSubmit method
                    // which is call
                    // when submitquery is searched

                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {

                        searchItems(query);
                        return false;
                    }

                    // This method is overridden to filter
                    // the adapter according to a search query
                    // when the user is typing search
                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        searchItems(newText);
                        return false;
                    }
                });


    }

    private void searchItems(String query){

        List<Item> searchResult = new ArrayList<>();
        for (Item item : modifiedListOfItems){
            if (item.getDescription().contains(query)){
                searchResult.add(item);
            }
            recyclerViewAdapter.setItemsList(searchResult);
        }

    }

    /**
     * Sets up the spinner which is used to pick the sort order of the items displayed
     */
    private void OrderPickerSetUp() {
        String[] itemOrderArray = {"Date added ascending","Date added descending","Alphabetical", "Alphabetical reversed"}; //TODO remove and implement properly
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, itemOrderArray);
        orderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemOrderSpinner.setAdapter(orderAdapter);
        itemOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                modifyItemsToBeDisplayed(category, itemOrderSpinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    /**
     * Setup for the recycler-view
     */
    private void recyclerViewSetUp() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);
        RecyclerViewAdapter.ItemClickListener clickListener = new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e){}
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){}

            @Override
            public void onItemClick(View view, int position) {
                DetailedItemsView.giveItem(recyclerViewAdapter.getItem(position));
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new DetailedItemsView());
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
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
    @SuppressLint("NotifyDataSetChanged")
    protected void modifyItemsToBeDisplayed (String category, String order) {
        //List<Item> modifiedListOfItems;
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

    @SuppressLint("NotifyDataSetChanged")
    public void setCategory (String category) {
        this.category = category;
        modifyItemsToBeDisplayed(category, "no order");
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
