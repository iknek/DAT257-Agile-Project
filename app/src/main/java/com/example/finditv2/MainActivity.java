package com.example.finditv2;

import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printHi(View view) {
        setContentView(R.layout.new_item_screen);
    }

    public void saveItem(View view){
        EditText text = (EditText)findViewById(R.id.editTextTextPersonName);
        String value = text.getText().toString();
        if(!value.equals("")){
            Item item = new Item(value);
            itemList.add(item);
        }
    }

    public void printList(View view) {
        for (Item item:itemList) {
            System.out.println(item.getDescription());
        }
    }
}