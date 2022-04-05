package com.example.finditv2;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView showItems = (TextView) findViewById(R.id.item_list);
        //showItems.setText("hej");
    }

    public void printHi(View view) {
        setContentView(R.layout.new_item_screen);
    }

    public void backToMain(View view) {
        setContentView(R.layout.activity_main);
    }

    public void goToItemScreen(View view) {
        setContentView(R.layout.item_screen);
        //showItems.setText("hej");
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