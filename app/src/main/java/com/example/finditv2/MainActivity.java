package com.example.finditv2;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Instance variables
     */
    Context context = this;
    private List<Category> categories = new ArrayList<>();

    /**
     * Run on app start. Sets the current view window, and makes a file manager which takes in the current context.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileManager fileManager = new FileManager(context);
        Intent intent = new Intent(getApplicationContext(), ItemScreen.class);
        Bundle extras = intent.getExtras();
    }

    /**
     * Opens new_item_screen on button click.
     * @param view
     */
    public void openItemMenu(View view) {
       // setContentView(R.layout.new_item_screen);
        Intent intent = new Intent(getApplicationContext(), AddItemScreen.class);
        startActivity(intent);
    }

    /**
     * Changes Activity to one where the Items are displayed in a list, the ItemScreen.
     * @param view a reference to the view which the method is connected to.
     */
    public void goToItemScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), ItemScreen.class);
        startActivity(intent);

    }

    /**
     * TODO: Remove
     * TODO: Return all objects, not just the most recently added on.
     * Currently prints the description of all items in the previously specified list. Also sets the textView text to the item description. (used for debugging)
     * More importantly, it calls FileManager.getObject to retrieve an object from memory.
     * @param view
     */
    /*public void printList(View view){
        for (Item item:itemList) {
            System.out.println(item.getDescription());
        }
        //FileManager.getObject();
        final TextView mTextView = (TextView) findViewById(R.id.textView2);
        //mTextView.setText(FileManager.getObject().getDescription());
        List<Item> items = FileManager.getObject();
        StringBuilder itemString = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemString.append(items.get(i).getDescription());
            itemString.append("\n");
        }
        mTextView.setText(itemString);
    }*/

    /**
     * Changes content view to the main page
     * @param view
     */
    public void goBack(View view) {
        setContentView(R.layout.activity_main);
    }


}