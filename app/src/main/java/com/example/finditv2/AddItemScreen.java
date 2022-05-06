package com.example.finditv2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddItemScreen extends AppCompatActivity {

    private Button addItem;
    private Button back;
    private EditText descriptionBox;
    private EditText locationBox;
    private Spinner spinner;
    private ImageView imageView;
    private Bitmap bitmap;
    private Toast toast;

    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private EditText locationStored;
    private int requestCode;

    /**
     * Called when view is opened. Creates view and button listeners within it.
     * @param savedInstanceState = earlier saved instance data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item_screen);
        back = findViewById(R.id.button3);
        addItem = findViewById(R.id.button2);
        descriptionBox = findViewById(R.id.descriptionTextInput);
        locationBox = findViewById(R.id.locationTextInput);
        imageView = findViewById(R.id.imageView2);
        spinner = findViewById(R.id.spinner);
        imageView.setImageResource(R.drawable.no_image);
        //locationStored = findViewById(R.id.locationTextInput2); TODO: Cleanup
        List<String> categoryArray = new ArrayList<>();
        for(Category cat : FileManager.getCategories()){
            categoryArray.add(cat.getName());
        }
        categoryArray.add(0, "All Categories");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        toast = new Toast(getApplicationContext());
        toast.setText("Item has been added");
        listeners();
    }

    /**
     * Sets listeners for buttons/other input fields.
     */
    private void listeners(){
        back.setOnClickListener(view -> finish());

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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraOrGallery();
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
        if(!description.isEmpty()){
            String currentCategory = spinner.getSelectedItem().toString();
            //Använder för att identifiera bilderna
            Date date = new Date(System.currentTimeMillis());
            Item item;
            if (bitmap != null) {
                String imagePath = FileManager.saveToInternalStorage(bitmap, date.toString());
                item = new Item(description, currentCategory, date, location, imagePath);
            } else {
                item = new Item(description, currentCategory, date, location);
            }
            FileManager.saveObject(item);
            bitmap = null;
            imageView.setImageResource(R.drawable.no_image);
            toast.show();
        }
    }

    /**
     * Opens gallery or Camera to retrieve an Image.
     */
    private void openCameraOrGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooser = Intent.createChooser(galleryIntent, "Camera or gallery");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
        someActivityResultLauncher.launch(chooser);
    }

    /**
     * Retrieves the image from either the gallery or directly via the camera app.
     */
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            if (data.hasExtra("data")) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            } else {
                try {
                    Uri imageUri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imageView.setImageURI(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}
