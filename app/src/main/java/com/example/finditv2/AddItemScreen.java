package com.example.finditv2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddItemScreen extends AppCompatActivity {

    private Button addItem;
    private Button back;
    private EditText descriptionBox;
    private EditText locationBox;
    private Spinner spinner;

    private EditText locationStored;

    private ImageView imageView;
    private Button addImage;
    private static final int PICK_IMAGE = 100;
    private String imagePath;

    //Använder för att identifiera bilderna
    private Date date;

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
        locationStored = findViewById(R.id.locationTextInput2);
        descriptionBox = findViewById(R.id.descriptionTextInput);
        locationBox = findViewById(R.id.locationTextInput);
        addImage = findViewById(R.id.imageButton);
        imageView = findViewById(R.id.imageView2);
        date = new Date(System.currentTimeMillis());
        List<String> categoryArray = new ArrayList<>();
        try {
            for(Category cat : FileManager.getCategories()){
                categoryArray.add(cat.getName());
            }
        }catch(Exception e){
            categoryArray = new ArrayList<>();
        }
        categoryArray.add(0, "All Categories");

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryArray);
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


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    /**
     * Method for hiding keyboard.
     * @param view
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Gets text from editText box on button click. Also clears the text.
     */
    private void grabDescriptionText(){
        String description = descriptionBox.getText().toString();
        String location = locationBox.getText().toString();
        saveItem(description, location);
        descriptionBox.getText().clear();
        locationBox.getText().clear();
    }

    /**
     * Saves item with category (set in the spinner), description and location
     * @param description = the description of the item
     * @param location = the location of the item
     */
    private void saveItem(String description, String location){
        if(!description.equals("")){
            String currentCategory = spinner.getSelectedItem().toString();
            Item item = new Item(description, currentCategory, date, location, imagePath);
            FileManager.saveObject(item);
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePath = FileManager.saveToInternalStorage(bitmap, date.toString());
        }
    }
}
