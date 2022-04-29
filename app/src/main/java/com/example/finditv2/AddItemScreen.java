package com.example.finditv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.Date;

public class AddItemScreen extends AppCompatActivity {

    private Button addItem;
    private Button back;
    private EditText descriptionBox;
    private EditText locationBox;
    private Spinner spinner;

    /**
     * Called when view is opened. Creates view and button listeners within it.
     * @param savedInstanceState = idk?
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_screen);
        back = findViewById(R.id.button3);
        back.setOnClickListener(view -> finish());
        addItem = findViewById(R.id.button2);
        descriptionBox = findViewById(R.id.descriptionTextInput);
        locationBox = findViewById(R.id.locationTextInput);

        String[] array = {"All Categories","One","Two"}; //TODO remove and implement properly

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grabDescriptionText();
            }
        });

        descriptionBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        locationBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    /**
     * Method for hiding keyboard.
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Gets text from editText box on button click. Also clears the text.
     */
    public void grabDescriptionText(){
        EditText descriptionText = findViewById(R.id.descriptionTextInput);
        String description = descriptionText.getText().toString();
        EditText locationText = findViewById(R.id.locationTextInput);
        String location = locationText.getText().toString();
        saveItem(description, location);
        descriptionBox.getText().clear();
        locationBox.getText().clear();
    }

    /**
     * Saves item with category (set in the spinner), description and location
     * @param description = the description of the item
     * @param location = the location of the item
     */
    public void saveItem(String description, String location){
        if(!description.equals("")){
            String currentCategory = spinner.getSelectedItem().toString();
            Date date = new Date(System.currentTimeMillis());
            Item item = new Item(description, currentCategory, date, location);
            FileManager.saveObject(item);
        }
    }

}
