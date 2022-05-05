package com.example.finditv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddCategoryScreen extends AppCompatActivity {

    private Button back;
    private Button addCategory;
    private EditText descriptionBox;

    private SnackBarMaker maker;


    /**
     * Method which is called when the add category page is started.
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.maker = new SnackBarMaker();
        setContentView(R.layout.add_category_screen);
        back = findViewById(R.id.button3);
        back.setOnClickListener(view -> finish());
        addCategory = findViewById(R.id.button7);
        descriptionBox = findViewById(R.id.categoryTextInput);

        addCategory.setOnClickListener(new View.OnClickListener() {
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
     * Takes the text from the text view and saves it into the local files.
     */
    private void grabDescriptionText(){
        String description = descriptionBox.getText().toString();
        saveItem(description);
        descriptionBox.getText().clear();
    }

    /**
     * Recieves the text and creates a category which is then saved in local files.
     * @param category = the text from the textView
     */
    //TODO: Center snackbar text
    private void saveItem(String category){
        if(category.isEmpty()){
            Toast toast = new Toast(getApplicationContext());
            toast.setText("Category Name Must Be Specified!");
            toast.show();
            return;
        }
        boolean match = FileManager.getCategories().stream().anyMatch(c-> c.getName().equals(category));
        if(match){
            Toast toast = new Toast(getApplicationContext());
            toast.setText("Category Already Exists!");
            toast.show();
            return;
        }
        FileManager.saveCategory(new Category(category));
    }

}
