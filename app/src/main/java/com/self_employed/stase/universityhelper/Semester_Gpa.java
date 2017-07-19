package com.self_employed.stase.universityhelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

    LinearLayout layout;
    ArrayList<SeekBar> seekBars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester__gpa);

        layout = (LinearLayout) findViewById(R.id.main_layout);

        //add a back button TODO
        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (Exception e){}

        //gets all current sliders on the page
        populateSliders();
        addChangeListeners();
    }

    //This method populates the ArrayList with all the current sliders on the page
    private void populateSliders(){

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

                //get the string id of the seekBar
                String id = getResources().getResourceEntryName(seekBar.getId());

                //this gets the number on the end of the id
                String targetIndex = id.substring(id.length() - 1);

                //then gets the id of the corresponding TextView for the SeekBar
                int intId = getResources().getIdentifier("textView" + targetIndex, "id", getPackageName());

                //then it retrieves that SeekBar
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
            //GPA starts at 1, instead of 0 like the SeekBar, so 1 must be added for every seekbar
            sum += s.getProgress() + 1;
        }

        DecimalFormat df = new DecimalFormat("#.000");

        TextView output = (TextView) findViewById(R.id.Output);
        output.setText("Your Gpa for this semester is: " + df.format(sum / seekBars.size()));
    }

    //This method will add a new slider and label to the page
    public void addSlider(View v){
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
        //Then create the new textView
        //the textView is inflated from a texView resource
        TextView newView = (TextView) LayoutInflater.from(this).inflate(R.layout.sem_gpa_textview_label, null);

        newView.setText("Paper " + newViewIndex + ": B-");

        setTextViewId(newView, "textView" + newViewIndex);

        layout.addView(newView, index);

        //finally we increment the index for the new SeekBar
        index++;

        //Now we create the new SeekBar
        //the seekbar is inflated from a SeekBar resource
        SeekBar newBar = (SeekBar) LayoutInflater.from(this).inflate(R.layout.sem_gpa_seekbar, null);

        setSeekBarId(newBar, "seekBar" + newViewIndex);

        //add the listener
        addSeekBarListener(newBar);

        layout.addView(newBar, index);

        //Finally we update the sliders arrayList
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
        //Then remove the last textView label and Seekbar
        layout.removeViewAt(index-1);
        layout.removeViewAt(index - 2);
        //then update ui
        populateSliders();
        calculate_gpa(new View(this));


    }

    public void displayConstraintToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void setTextViewId(TextView tv, String id){
        switch(id){
            case "textView1":
                tv.setId(R.id.textView1);
                return;
            case "textView2":
                tv.setId(R.id.textView2);
                return;
            case "textView3":
                tv.setId(R.id.textView3);
                return;
            case "textView4":
                tv.setId(R.id.textView4);
                return;
            case "textView5":
                tv.setId(R.id.textView5);
                return;
            case "textView6":
                tv.setId(R.id.textView6);
                return;
            case "textView7":
                tv.setId(R.id.textView7);
                return;
            case "textView8":
                tv.setId(R.id.textView8);
                return;
            case "textView9":
                tv.setId(R.id.textView9);
                return;
            case "textView10":
                tv.setId(R.id.textView10);
                return;
            case "textView11":
                tv.setId(R.id.textView11);
                return;
            case "textView12":
                tv.setId(R.id.textView12);
                return;
            case "textView13":
                tv.setId(R.id.textView13);
                return;
            case "textView14":
                tv.setId(R.id.textView14);
                return;
            case "textView15":
                tv.setId(R.id.textView15);
                return;
            case "textView16":
                tv.setId(R.id.textView16);
                return;
            case "textView17":
                tv.setId(R.id.textView17);
                return;
            case "textView18":
                tv.setId(R.id.textView18);
                return;
            case "textView19":
                tv.setId(R.id.textView19);
                return;
            case "textView20":
                tv.setId(R.id.textView20);
                return;
            case "textView21":
                tv.setId(R.id.textView21);
                return;
            case "textView22":
                tv.setId(R.id.textView22);
                return;
            case "textView23":
                tv.setId(R.id.textView23);
                return;
            case "textView24":
                tv.setId(R.id.textView24);
                return;
        }

    }

    public void setSeekBarId(SeekBar sb, String id){
        switch (id){
            case "seekBar1":
                sb.setId(R.id.seekBar1);
                return;
            case "seekBar2":
                sb.setId(R.id.seekBar2);
                return;
            case "seekBar3":
                sb.setId(R.id.seekBar3);
                return;
            case "seekBar4":
                sb.setId(R.id.seekBar4);
                return;
            case "seekBar5":
                sb.setId(R.id.seekBar5);
                return;
            case "seekBar6":
                sb.setId(R.id.seekBar6);
                return;
            case "seekBar7":
                sb.setId(R.id.seekBar7);
                return;
            case "seekBar8":
                sb.setId(R.id.seekBar8);
                return;
            case "seekBar9":
                sb.setId(R.id.seekBar9);
                return;
            case "seekBar10":
                sb.setId(R.id.seekBar10);
                return;
            case "seekBar11":
                sb.setId(R.id.seekBar11);
                return;
            case "seekBar12":
                sb.setId(R.id.seekBar12);
                return;
            case "seekBar13":
                sb.setId(R.id.seekBar13);
                return;
            case "seekBar14":
                sb.setId(R.id.seekBar14);
                return;
            case "seekBar15":
                sb.setId(R.id.seekBar15);
                return;
            case "seekBar16":
                sb.setId(R.id.seekBar16);
                return;
            case "seekBar17":
                sb.setId(R.id.seekBar17);
                return;
            case "seekBar18":
                sb.setId(R.id.seekBar18);
                return;
            case "seekBar19":
                sb.setId(R.id.seekBar19);
                return;
            case "seekBar20":
                sb.setId(R.id.seekBar20);
                return;
            case "seekBar21":
                sb.setId(R.id.seekBar21);
                return;
            case "seekBar22":
                sb.setId(R.id.seekBar22);
                return;
            case "seekBar23":
                sb.setId(R.id.seekBar23);
                return;
            case "seekBar24":
                sb.setId(R.id.seekBar24);
                return;
        }
    }

}
