package com.self_employed.stase.universityhelper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Semester_Gpa extends AppCompatActivity {
    //the minimum and maximum papers that you can have on the screen
    final static private int MAX_PAPERS = 9;
    final static private int MIN_PAPERS = 1;
    final static private int DEFAULT_NUM_PAPERS = 4;

    LinearLayout layout;
    ArrayList<SeekBar> seekBars = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_gpa);

        layout = (LinearLayout) findViewById(R.id.main_layout);

        //populate the default number of sliders
        for(int i = 1; i <= DEFAULT_NUM_PAPERS; i++){
            addSlider(layout);
        }
        //gets all current sliders on the page
        populateSliders();
        addChangeListeners();
    }

    //This method populates the ArrayList with all the current sliders on the page
    private void populateSliders(){

        //object creation is expensive. so limit it where we can
        seekBars = seekBars == null ? new ArrayList<SeekBar>() : seekBars;
        //ensure arraylist is empty
        seekBars.clear();

        double childCount = layout.getChildCount();

        //iterate through all children to get the sliders
        for(int i = 0; i < childCount; i++){
            //we only want to look at the elements that are linearLayouts
            Object item = layout.getChildAt(i);
            if(item instanceof LinearLayout) {
                SeekBar sb = (SeekBar) ((LinearLayout)item).getChildAt(1);
                seekBars.add(sb);
            }
        }
    }

    //This method adds on change listeners to all sliders
    private void addChangeListeners(){
        for(int i = 0; i < seekBars.size(); i++){
            SeekBar e = seekBars.get(i);
            addSeekBarListener(e);
        }
    }

    //This method adds a setOnSeekBarChangeListener to the provided Seekbar
    private void addSeekBarListener(SeekBar e){
        e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //first get the corresponding grade for the seekbar progress
                String grade = getGrade(progress);
                String GPA = getGPA(progress);

                //get the string id of the seekBar
                String id = getResources().getResourceEntryName(seekBar.getId());

                //this gets the number on the end of the id
                String targetIndex = id.substring(id.length() - 1);

                //then gets the id of the corresponding TextView for the SeekBar
                int intId = getResources().getIdentifier("textView" + targetIndex, "id", getPackageName());

                //then it retrieves that SeekBar
                TextView targetTextView = (TextView) findViewById(intId);

                //then, set the new text
                targetTextView.setText("Paper " + targetIndex + ": " + grade + " (" + GPA + ")");

                //Finally, update the overall gpa
                calculate_gpa(seekBar);

            }
            // a method which returns the corresponding grade for the progress value of the SeekBar
            private String getGrade(int progress){

                String[] grades = {"D- D D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};

                try{
                    return grades[progress];
                }catch (Exception e){
                    return "ERROR";
                }
            }

            private String getGPA(int progress){
                String[] grades = {"0.0", "1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0"};

                try{
                    return grades[progress];
                }catch (Exception e){
                    return "ERROR";
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //TODO
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //TODO
            }
        });
    }

    //This method calculates the gpa of the page and updates the result
    private void calculate_gpa(View v){
        double sum = 0;
        for(SeekBar s : seekBars){
            sum += s.getProgress();
        }

        DecimalFormat df = new DecimalFormat("#.000");


        double gpa = sum / seekBars.size();
        String grade = getGrade(gpa);

        TextView output = (TextView) findViewById(R.id.Output);
        output.setText(df.format(gpa) + " (" + grade + ")");
    }

    private String getGrade(double gpa){
        try{
            String[] grades = {"D- D D+", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};
            //the casting of double to int removes all decimal numbers, rounded down
            return grades[(int) gpa];
        }catch (Exception e){
            return "ERROR";
        }
    }

    //This method will add a new slider and label to the page
    public void addSlider(View v){

        //if the collection has not been instantiated, this method will crash.
        if(seekBars == null){
            populateSliders();
            addChangeListeners();
        }

        //first, check that the paper constraints are met
        if(seekBars.size() >= MAX_PAPERS){
            displayConstraintToast("The maximum number of papers is " + MAX_PAPERS);
            return;
        }


        //then, find the index that the insertPoint is at
        int index = 0;
        int childCount = layout.getChildCount();

        for(int i = 0; i < childCount; i++) {
            View child = layout.getChildAt(i);

            //if an element does not have an id (which is bad practice), a try catch will make sure there are no errors
            String id;
            try{
                id = getResources().getResourceEntryName(child.getId());
            }catch(Exception e){
                id = "NONE";
            }

            // this finds the index of the insert point
            if (id.equals("insertPoint")) {
                index = i;
                break;
            }
        }
        //then format the new index for the id selection
        DecimalFormat df = new DecimalFormat("#");
        String newViewIndex = df.format(seekBars.size() + 1);

        //create a new element
        LinearLayout newLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.sem_gpa_paper_element, null);

        //set the text
        ((TextView)newLayout.getChildAt(0)).setText("Paper " + newViewIndex + ": B-" + " (4.0)");

        //set the id
        int newId = IdGetter.getter().getId("textView", Integer.parseInt(newViewIndex));
        newLayout.getChildAt(0).setId(newId);

        //set up the seekBar
        newId = IdGetter.getter().getId("seekBar", Integer.parseInt(newViewIndex));
        newLayout.getChildAt(1).setId(newId);
        addSeekBarListener((SeekBar)newLayout.getChildAt(1));

        //add the element to the view
        layout.addView(newLayout);

        populateSliders();

        //and then update the GPA
        calculate_gpa(new View(this));

    }

    //This method will remove a slider from the page
    public void removeSlider(View v){
        //first, check that the paper constraints are met
        if(seekBars.size() <= MIN_PAPERS){
            displayConstraintToast("The minimum number of papers is " + MIN_PAPERS);
            return;
        }

        //find the number of elements
        int childCount = layout.getChildCount();
        //Then remove the last element
        layout.removeViewAt(childCount-1);
        //then update ui, and the data collections
        populateSliders();
        //update the result
        calculate_gpa(new View(this));

    }

    public void displayConstraintToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

}
