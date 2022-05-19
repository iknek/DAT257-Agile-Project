package com.example.finditv2.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.finditv2.Category;
import com.example.finditv2.FileManager;
import com.example.finditv2.R;

public class NewCategoryPage extends Fragment {

    private Button addCategory;
    private EditText descriptionBox;

    /**
     * Method which is called when the add category page is started.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_category_screen, container, false);
        addCategory = view.findViewById(R.id.button7);
        descriptionBox = view.findViewById(R.id.categoryTextInput);
        listeners();
        return view;
    }

    private void listeners(){
        addCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                grabDescriptionText();
            }
        });
        descriptionBox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) hideKeyboard(v);
            }
        });
    }

    /**
     * Method for hiding keyboard.
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Takes the text from the text view and saves it into the local files.
     */
    private void grabDescriptionText(){
        saveItem(descriptionBox.getText().toString());
        descriptionBox.getText().clear();
    }

    /**
     * Recieves the text and creates a category which is then saved in local files, if category doesn't exist and textfield isn't empty.
     * @param category = the text from the textView
     */
    private void saveItem(String category){
        Toast toast = new Toast(getActivity());
        if(category.isEmpty()){
            toast.setText("Category Name Must Be Specified!");
            toast.show();
            return;
        }
        boolean match = FileManager.getCategories().stream().anyMatch(cat-> cat.getName().equals(category));
        if(match || category.equalsIgnoreCase("All categories")){
            toast.setText("Category Already Exists!");
            toast.show();
            return;
        }
        FileManager.saveCategory(new Category(category));
        toast.setText("Category Added!");
        toast.show();
    }
}


