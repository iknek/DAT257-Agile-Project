package com.example.finditv2;

import android.content.Context;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    /**
     * Instance variables
     */
    Context context = this;

    /**
     * Run on app start. Sets the current view window, and makes a file manager which takes in the current context.
     * @param savedInstanceState = something or other.
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
}