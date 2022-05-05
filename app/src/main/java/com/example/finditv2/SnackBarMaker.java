package com.example.finditv2;

import android.view.View;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarMaker {
    protected Snackbar makeSnackBar(View view, String text){
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        TextView mainTextView = (TextView) (snackbar.getView()).findViewById(R.id.snackbar_text);
        mainTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return snackbar;
    }
}
