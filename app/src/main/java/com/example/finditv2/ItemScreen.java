package com.example.finditv2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ItemScreen extends AppCompatActivity {

    TextView showItems;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);
        showItems = findViewById(R.id.item_list);
        String[] Items = getIntent().getExtras().getStringArray("Items");

        for (String item : Items) {
            showItems.append("\n" + item);
        }


        backButton = findViewById(R.id.button5);
        backButton.setOnClickListener(view -> finish());
    }
}
