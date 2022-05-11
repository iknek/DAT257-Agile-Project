package com.example.finditv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;


public class DetailedItemView extends AppCompatActivity {
    private Button removeButton;
    protected static Item item;
    private EditText description;
    private EditText location;
    private TextView timeFound;
    private ImageView pic;
    private Button editItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item_screen);

        removeButton = findViewById(R.id.button4);
        removeButton.setOnClickListener(v -> removeItem());

        editItemButton = findViewById(R.id.button8);
        editItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editItem();
            }
        });
        textViewSetup();
        pictureSetUp();

        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void pictureSetUp() {
        pic = findViewById(R.id.imageView5);
        if(item.getImageUri() != null){
            pic.setImageBitmap(FileManager.loadImageFromStorage(item.getImageUri()));
        }else{
            pic.setImageResource(R.drawable.no_image);
        }
    }

    private void textViewSetup(){
        description = findViewById(R.id.textView12);
        location = findViewById(R.id.textView9);
        timeFound = findViewById(R.id.textView4);

        description.setText(item.getDescription());
        location.setText(item.getLocation());
        timeFound.setText(DateFormat.getInstance().format(item.getDate()));
    }

    private void removeItem(){
        FileManager.removeItem(item);
        this.finish();
    }

    public static void giveItem(Item i){
        item = i;
    }

    private void editItem(){
        String descriptionText = description.getText().toString();
        String locationText = location.getText().toString();

        description.getText().clear();
        location.getText().clear();

        Item newItem;
        if(item.getImageUri() != null){
            newItem = new Item(descriptionText, item.getCategory(), item.getDate(), locationText, item.getImageUri());
        }else{
            newItem = new Item(descriptionText, item.getCategory(), item.getDate(), locationText);
        }

        removeItem();
        FileManager.saveObject(newItem);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
