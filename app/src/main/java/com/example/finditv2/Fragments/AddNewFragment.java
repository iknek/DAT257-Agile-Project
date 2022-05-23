package com.example.finditv2.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.finditv2.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewFragment extends Fragment {
    private Button addItem;
    private EditText descriptionBox;
    private EditText locationBox;
    private Spinner spinner;
    private ImageView imageView;
    private Bitmap bitmap;
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_item_screen, container,false);
        addItem = view.findViewById(R.id.button2);
        descriptionBox = view.findViewById(R.id.descriptionTextInput);
        locationBox = view.findViewById(R.id.locationTextInput);
        imageView = view.findViewById(R.id.imageView2);
        spinner = view.findViewById(R.id.spinner);
        imageView.setImageResource(R.drawable.no_img);
        List<String> categoryArray = new ArrayList<>();
        for(Category cat : FileManager.getCategories()){
            categoryArray.add(cat.getName());
        }
        categoryArray.add(0, "All Categories");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        toast = new Toast(getContext());
        toast.setText("Item has been added");
        listeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * Sets listeners for buttons/other input fields.
     */
    private void listeners(){
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
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
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
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                    imageView.setImageURI(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}
