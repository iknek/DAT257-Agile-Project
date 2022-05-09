package com.example.finditv2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;


public class DetailedItemView extends AppCompatActivity {
    private Button removeButton;
    protected static Item item;
    private TextView description;
    private TextView location;
    private TextView timeFound;
    private ImageView pic;
    private Button editItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO crashes when the item has no specific image.
        //TODO connect edit button
        //TODO update the recyclerView in item view when going back
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item_screen);

        removeButton = findViewById(R.id.button4);
        removeButton.setOnClickListener(v -> removeItem(item));

        editItemButton = findViewById(R.id.button8);
        editItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeItem(item);
            }
        });
        textViewSetup();
        pictureSetUp();
    }

    private void pictureSetUp() {
        pic = findViewById(R.id.imageView5);
        pic.setImageBitmap(FileManager.loadImageFromStorage(item.getImageUri()));
    }

    private void textViewSetup(){
        description = findViewById(R.id.textView12);
        location = findViewById(R.id.textView9);
        timeFound = findViewById(R.id.textView4);

        description.setText(item.getDescription());
        location.setText(item.getLocation());
        timeFound.setText(DateFormat.getInstance().format(item.getDate()));
    }

    private void removeItem(Item item){
        FileManager.removeItem(item);
        this.finish();
    }

    public static void giveItem(Item i){
        item = i;
    }
}
