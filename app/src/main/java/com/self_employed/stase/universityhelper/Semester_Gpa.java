package com.self_employed.stase.universityhelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Semester_Gpa extends AppCompatActivity {
    LinearLayout layout;
    ArrayList<SeekBar> seekBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester__gpa);

        //gets all current sliders on the page
        populateSliders();
        addChangeListeners();
    }

    //This method populates the ArrayList with all the current sliders on the page
    private void populateSliders(){
        layout = (LinearLayout) findViewById(R.id.main_layout);
        seekBars = new ArrayList<>();

        double childCount = layout.getChildCount();

        //iterate through all children to get the sliders
        //This is just a brute force method, anything that isnt a seek bar will fail and then wont be added to the ArrayList
        for(int i = 0; i < childCount; i++){
            Object child = layout.getChildAt(i);
            try {
                seekBars.add((SeekBar) child);
            }catch(Exception e) {}
        }
    }

    //This method adds on change listeners to all sliders
    private void addChangeListeners(){
        for(int i = 0; i < seekBars.size(); i++){
            SeekBar e = seekBars.get(i);
            e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //first get the corresponding grade for the seekbar progress
                    String grade = getGrade(progress);

                    //get the string id of the seekBar
                    String id = getResources().getResourceEntryName(seekBar.getId());

                    //this gets the number on the end of the id
                    String targetIndex = id.substring(id.length() - 1);

                    //then gets the id of the corresponding TextView for the SeekBar
                    int intId = getResources().getIdentifier("textView" + targetIndex, "id", getPackageName());

                    //then it retrieves that seekbar
                    TextView targetTextView = (TextView) findViewById(intId);

                    //then, set the new text
                    targetTextView.setText("Paper " + targetIndex + ": " + grade);

                    //Finally, update the overall gpa
                    calculate_gpa(seekBar);

                }
                // a method which returns the corresponding grade for the progress value of the SeekBar
                private String getGrade(int progress){

                    String[] grades = {"C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};

                    try{
                        //Progress starts at 1, whereas array starts at 0
                        return grades[progress - 1];
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
    }


    private void calculate_gpa(View v){
        double sum = 0;
        for(SeekBar s : seekBars){
            sum += s.getProgress();
        }

        DecimalFormat df = new DecimalFormat("#.000");

        TextView output = (TextView) findViewById(R.id.Output);
        output.setText("Your Gpa for this semester is: " + df.format(sum / seekBars.size()));
    }


    //This method will add a new slider and label to the page
    public void addSlider(View v){

        //first, find the index that the insertPoint is at
        int index = 0;
        double childCount = layout.getChildCount();

        for(int i = 0; i < childCount; i++){
            View child = layout.getChildAt(i);
            String id = getResources().getResourceEntryName(child.getId());
            // this finds the index of the insert point
            if(id.equals("insertPoint")){
                index = i;
                i = 10000;
                //TODO
            }
        }

    }

}
