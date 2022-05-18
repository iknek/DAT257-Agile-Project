package com.example.finditv2.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.finditv2.FileManager;
import com.example.finditv2.Item;
import com.example.finditv2.R;

import java.text.DateFormat;
import java.util.Objects;

public class DetailedItemsView extends Fragment {
    protected static Item item;
    private EditText description;
    private EditText location;
    private View view;
    private Button editItemButton;

    /**
     * Method which is called when the add category page is started.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detailed_item_screen, container, false);
        Button removeButton = view.findViewById(R.id.button4);
        removeButton.setOnClickListener(v -> removeItem());
        editItemButton = view.findViewById(R.id.button8);

        textViewSetup();
        pictureSetUp();
        listeners();
        return view;
    }

    private void listeners(){
        editItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editItem();
            } });

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
        ImageView pic = view.findViewById(R.id.imageView5);
        if(item.getImageUri() != null){
            pic.setImageBitmap(FileManager.loadImageFromStorage(item.getImageUri()));
        }else{
            pic.setImageResource(R.drawable.no_image);
        }
    }

    private void textViewSetup(){
        description = view.findViewById(R.id.textView12);
        location = view.findViewById(R.id.textView9);
        TextView timeFound = view.findViewById(R.id.textView4);

        description.setText(item.getDescription());
        location.setText(item.getLocation());
        timeFound.setText(DateFormat.getInstance().format(item.getDate()));
    }

    private void removeItem(){
        FileManager.removeItem(item);
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    public static void giveItem(Item i){
        item = i;
    }

    private void editItem(){
        Item newItem;

        String descriptionText = description.getText().toString();
        String locationText = location.getText().toString();
        description.getText().clear();
        location.getText().clear();

        if(item.getImageUri() != null) newItem = new Item(descriptionText, item.getCategory(), item.getDate(), locationText, item.getImageUri());
        else newItem = new Item(descriptionText, item.getCategory(), item.getDate(), locationText);
        removeItem();
        FileManager.saveObject(newItem);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
