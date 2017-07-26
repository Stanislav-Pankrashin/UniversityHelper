package com.self_employed.stase.universityhelper;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

//This class is essentially a data-structure for managing elements in the page
public class PaperGpaElement {

    public LinearLayout layout;
    private int layoutId;
    private TextView label;
    private RadioButton check;
    private EditText amount;
    private EditText outOf;
    private EditText weight;

    public PaperGpaElement(LinearLayout layout){
        this.layout = layout;
        this.layoutId = layout.getId();
        this.label = (TextView) layout.getChildAt(0);
        this.check = (RadioButton) layout.getChildAt(1);
        this.amount = (EditText) layout.getChildAt(3);
        this.outOf = (EditText) layout.getChildAt(5);
        this.weight = (EditText) layout.getChildAt(7);

        //set the listeners to check for constraints
        this.setOnFocusChangeListener();
    }

    //Negative numbers should be disallowed, along with fractions > 1. i.e 5 out of 3. as 3 is the maximum number of marks
    //for the time being these will be disabled. until a way of displaying toasts in the window is found
    private void setOnFocusChangeListener(){
        this.amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //PaperGpaElement.this.checkFraction();
                }
            }
        });

        this.outOf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    //PaperGpaElement.this.checkFraction();
                }
            }
        });

    }

    //this method checks the amount and outOf EditText fields such that amount > outOf does not occur
    //it returns true if the fraction is fine, otherwise it returns false
    //TODO NOT COMPLETED
    //this method should also display some sort of toast, or error message to signify that the constraint has been violated.
    public boolean checkFraction(){
        double amount = -1;
        double outOf = -1;

        try {
            amount = Double.parseDouble(this.amount.getText().toString());
        }catch (Exception e){
            return true;
        }

        try {
            outOf = Double.parseDouble(this.outOf.getText().toString());
        }catch (Exception e){
            return true;
        }

        if(amount == -1 || outOf == -1){
            return false;
        }

        double fraction = amount / outOf;

        //this should also trigger a toast to appear, or some sort of warning.
        if(fraction > 1){
            this.amount.setText("");
            return false;
        }

        return true;
    }

    //This method returns the total number of percentage points (out of 100) that a particular element contributes to the total.
    //if any of the fields are not filled out, the method will just return 0 as default
    public double getContributionToTotal(){
        try {
            //first check that the fraction is valid
            boolean isValid = checkFraction();
            if(!isValid){
                //returning -1 signifies that there were improper values in the fraction.
                return -1;
            }


            double amount = Double.parseDouble(this.amount.getText().toString());
            double outOf = Double.parseDouble(this.outOf.getText().toString());
            double weight = getWeight();

            double contribution = (amount / outOf) * weight;
            return contribution;
        }catch (Exception e){
            return -2;
        }
    }

    //returns the weight, if it is not filled in, it returns 0 as default
    public double getWeight(){
        try{
            double weight = Double.parseDouble(this.weight.getText().toString());

            return weight;
        }catch (Exception e){
            return 0;
        }
    }


}
