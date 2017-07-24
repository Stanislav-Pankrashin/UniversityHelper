package com.self_employed.stase.universityhelper;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

//This class is essentially a data-structure for managing elements in the page
public class SemGpaElement {

    private LinearLayout layout;
    private int layoutId;
    private TextView label;
    private RadioButton check;
    private EditText amount;
    private EditText outOf;
    private EditText weight;

    public SemGpaElement(LinearLayout layout){
        this.layout = layout;
        this.layoutId = layout.getId();
        this.label = (TextView) layout.getChildAt(0);
        this.check = (RadioButton) layout.getChildAt(1);
        this.amount = (EditText) layout.getChildAt(3);
        this.outOf = (EditText) layout.getChildAt(5);
        this.weight = (EditText) layout.getChildAt(7);
    }

    //This method returns the total number of percentage points (out of 100) that a particular element contributes to the total.
    public double getContributionToTotal(){
        double amount = Double.parseDouble(this.amount.getText().toString());
        double outOf = Double.parseDouble(this.outOf.getText().toString());
        double weight = Double.parseDouble(this.weight.getText().toString());

        double contribution = (amount / outOf) * weight;
        return contribution;

    }
}
