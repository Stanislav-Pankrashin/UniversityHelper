package com.self_employed.stase.universityhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class PaperGpaCalc extends AppCompatActivity {
    //private RelativeLayout layout;
    //I'm not super happy about hardcoding these names.
    static final String[] SPINNER_NAMES = {"labSpinner", "assignmentSpinner", "testSpinner", "examSpinner"};
    //collections for data calculations
    private ArrayList<Spinner> spinners;
    private ArrayList<SemGpaElement> labElements;
    private ArrayList<SemGpaElement> assignmentElements;
    private ArrayList<SemGpaElement> testElements;
    private ArrayList<SemGpaElement> examElements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_gpa_calc);

        //layout = (RelativeLayout) findViewById(R.id.main_layout);
        setUpSpinners();

        //add functionality to the calculate button
        setUpCalculate();

        //set up the data collection arrays
        labElements = new ArrayList<>();
        assignmentElements = new ArrayList<>();
        testElements = new ArrayList<>();
        examElements = new ArrayList<>();
    }

    //this method adds the onClick listener to the calculate Unknowns button
    private void setUpCalculate(){
        Button b = (Button) findViewById(R.id.calculateButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.outputText);
                double total = 0;

                //iterate through each elementGroup
                for(SemGpaElement e:labElements){
                    //TODO
                }


            }
        });
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
                //make sure to remove the element from data collection array
                popElementFromArray(groupName);
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

        //Then we add the element to the relevant arraylist for logic purposes
        addElementToArray(newView, groupName);
    }

    //this adds an element to an array. first by creating a new dataStructure object, then storing that in the correct array
    private void addElementToArray(View v, String groupName){
        SemGpaElement struct = new SemGpaElement((LinearLayout) v);

        switch (groupName){
            case "lab":
                labElements.add(struct);
                break;
            case "assignment":
                assignmentElements.add(struct);
                break;
            case "test":
                testElements.add(struct);
                break;
            case "exam":
                examElements.add(struct);
                break;
        }

    }

    //this method removes the last element from one of these arrays
    private void popElementFromArray(String groupName){
        switch (groupName){
            case "labElement":
                labElements.remove(labElements.size() - 1);
                break;
            case "assignmentElement":
                assignmentElements.remove(assignmentElements.size() - 1);
                break;
            case "testElement":
                testElements.remove(testElements.size() - 1);
                break;
            case "examElement":
                examElements.remove(examElements.size() - 1);
                break;
        }
    }


    //this method sets up the number spinners for the page
    private void setUpSpinners(){
        getSpinners();
        setSpinnerChoices();
        setSpinnerListeners();
    }
}
