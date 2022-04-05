package com.example.finditv2;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Instance variables
     */
    Context context = this;
    private List<Item> itemList = new ArrayList<>();

    /**
     * Run on app start. Sets the current view window, and makes a file manager which takes in the current context.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileManager fileManager = new FileManager(context);
    }

    /**
     * Opens new_item_screen on button click.
     * @param view
     */
    public void openItemMenu(View view) {
        setContentView(R.layout.new_item_screen);
    }
    /**
    Grabs text from the text box where users can input an item description.
     */
    public void grabDescriptionText(View view){
        EditText text = (EditText)findViewById(R.id.descriptionTextInput);
        String value = text.getText().toString();
        saveItem(value,view);
    }

    /**
     Checks if item has a description. If it does, it makes a new Item object with said description, and passes it onto FileManager.saveObject.
     Also adds the item to an itemlist. Used for debugging currently.
     */
    public void saveItem(String value, View view){
        if(!value.equals("")){
            Item item = new Item(value);
            FileManager.saveObject(item);
            itemList.add(item);
        }
    }

    /**
     * TODO: Return all objects, not just the most recently added on.
     * Currently prints the description of all items in the previously specified list. Also sets the textView text to the item description. (used for debugging)
     * More importantly, it calls FileManager.getObject to retrieve an object from memory.
     * @param view
     */
    public void printList(View view){
        for (Item item:itemList) {
            System.out.println(item.getDescription());
        }
        //FileManager.getObject();
        final TextView mTextView = (TextView) findViewById(R.id.textView2);
        mTextView.setText(FileManager.getObject().getDescription());
    }

    /**
     * Changes content view to the main page
     * @param view
     */
    public void goBack(View view) {
        setContentView(R.layout.activity_main);
    }


}