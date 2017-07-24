package com.self_employed.stase.universityhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class PaperGpaCalc extends AppCompatActivity {
    //private RelativeLayout layout;
    //I'm not super happy about hardcoding these names.
    static final String[] SPINNER_NAMES = {"labSpinner", "assignmentSpinner", "testSpinner", "examSpinner"};
    //collections for data calculations
    private ArrayList<Spinner> spinners;
    private ArrayList<LinearLayout> labElements;
    private ArrayList<LinearLayout> assignmentElements;
    private ArrayList<LinearLayout> testElements;
    private ArrayList<LinearLayout> examElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_gpa_calc);

        //layout = (RelativeLayout) findViewById(R.id.main_layout);

        setUpSpinners();
    }

    //this method populates the spinners array with all of the spinners on the page
    private void getSpinners(){
        spinners = new ArrayList<>();

        //this is hardCoded, and I do not like it and it shouldn't work like this but seeing as it wont be generated it is fine
        for(String e : SPINNER_NAMES){
            int id = getResources().getIdentifier(e, "id", getPackageName());
            spinners.add( (Spinner) findViewById(id));
        }
    }

    //this method sets the spinner choices for all spinners
    private void setSpinnerChoices(){

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for(Spinner e: spinners){
            // Apply the adapter to the spinner
            e.setAdapter(adapter);

        }

    }

    private void setSpinnerListeners(){
        for(Spinner e: spinners){
            addOnSelectListener(e);
        }
    }

    private void addOnSelectListener(Spinner e){
        e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str_id = getResources().getResourceEntryName(parent.getId());

                if(str_id.contains("lab")){
                    LinearLayout parentLayout = (LinearLayout) parent.getParent().getParent();
                    //get the linearLayout that you will insert the elements into
                    parentLayout = (LinearLayout) parentLayout.getChildAt(1);
                    PaperGpaCalc.this.adjustGroup(parentLayout, position, "lab");


                }else if (str_id.contains("assignment")){
                    LinearLayout parentLayout = (LinearLayout) parent.getParent().getParent();
                    //get the linearLayout that you will insert the elements into
                    parentLayout = (LinearLayout) parentLayout.getChildAt(1);
                    PaperGpaCalc.this.adjustGroup(parentLayout, position, "assignment");

                }else if (str_id.contains("test")){
                    LinearLayout parentLayout = (LinearLayout) parent.getParent().getParent();
                    //get the linearLayout that you will insert the elements into
                    parentLayout = (LinearLayout) parentLayout.getChildAt(1);
                    PaperGpaCalc.this.adjustGroup(parentLayout, position, "test");

                }else if (str_id.contains("exam")){
                    LinearLayout parentLayout = (LinearLayout) parent.getParent().getParent();
                    //get the linearLayout that you will insert the elements into
                    parentLayout = (LinearLayout) parentLayout.getChildAt(1);
                    PaperGpaCalc.this.adjustGroup(parentLayout, position, "exam");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //this method adjusts the number of elements in a segment
    private void adjustGroup(LinearLayout parent, int count, String groupName){
        //get the current number of elements
        int currentCount = parent.getChildCount();
        //if the current number of elements is greater than the desired amount, then remove the necessary amount
        if (currentCount > count){
            int toRemove = currentCount - count;
            for(; toRemove != 0; toRemove--){
                parent.removeViewAt(parent.getChildCount() - 1);
            }
        }else{ // else, add the required amount
            int toAdd = count - currentCount;
            for(; toAdd != 0; toAdd--) {
                int insertIndex = parent.getChildCount();
                inflateElement(parent, insertIndex, groupName);
            }
        }
    }

    //this method inflates a new element in one of the tabs
    public void inflateElement(LinearLayout parent, int index, String groupName){
        LinearLayout newView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.paper_element, null);

        TextView tv = (TextView) newView.getChildAt(0);
        //set the correct text
        tv.setText(groupName + index + 1 + ":");
        //set the id for the entire element
        newView.setId(IdGetter.getter().getId(groupName + "Element", index + 1));

        parent.addView(newView, index);
    }


    //this method sets up the number spinners for the page
    private void setUpSpinners(){
        getSpinners();
        setSpinnerChoices();
        setSpinnerListeners();
    }
}
