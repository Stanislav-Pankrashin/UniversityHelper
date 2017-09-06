package com.self_employed.stase.universityhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class PaperGpaCalc extends AppCompatActivity {
    //private RelativeLayout layout;
    //I'm not super happy about hardcoding these names.
    static final String[] SPINNER_NAMES = {"labSpinner", "assignmentSpinner", "testSpinner", "examSpinner"};
    //collections for data calculations
    private ArrayList<Spinner> spinners;
    private ArrayList<PaperGpaElement> labElements;
    private ArrayList<PaperGpaElement> assignmentElements;
    private ArrayList<PaperGpaElement> testElements;
    private ArrayList<PaperGpaElement> examElements;
    private ArrayList<ArrayList<PaperGpaElement>> masterArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_gpa_calc);

        //layout = (RelativeLayout) findViewById(R.id.main_layout);
        setUpSpinners();

        //add functionality to the calculate button
        setUpCalculate();

        //add functionality to the exam button
        setUpExamCalculate();

        //add functionality to the seekBar
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        addSeekBarListener(sb);

        //set up the data collection arrays
        initialiseDataArrays();
    }
    //This method adds a setOnSeekBarChangeListener to the provided Seekbar
    private void addSeekBarListener(SeekBar e){
        e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //first get the corresponding grade for the seekbar progress
                String grade = getGrade(progress);
                String GPA = getGPA(progress);

                //then it retrieves that SeekBar
                TextView targetTextView = (TextView) findViewById(R.id.targetGradeText);

                //then, set the new text
                targetTextView.setText("Target Grade" + ": " + grade + " (" + GPA + ")");

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

    //this method initialises the data collection arrays
    private void initialiseDataArrays(){
        //set up the data collection arrays
        labElements = new ArrayList<>();
        assignmentElements = new ArrayList<>();
        testElements = new ArrayList<>();
        examElements = new ArrayList<>();
        masterArray = new ArrayList<>();

        masterArray.addAll(Arrays.asList(labElements, assignmentElements, testElements, examElements));
    }

    //this method adds the onClick listener to the calculate Unknowns button
    private void setUpCalculate(){
        Button b = (Button) findViewById(R.id.calculateButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaperGpaCalc.this.calculateTotal();
            }

        });
    }

    private void setUpExamCalculate(){
        Button b = (Button) findViewById(R.id.examButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaperGpaCalc.this.calculateReqExam();
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
        String capitalisedName = groupName.substring(0,1).toUpperCase() + groupName.substring(1);
        tv.setText(capitalisedName + " " + (index + 1) + ":");

        //set the id for the entire element

        newView.setId(IdGetter.getter().getId(groupName + "Element", index + 1));

        parent.addView(newView, index);

        //Then we add the element to the relevant arraylist for logic purposes
        addElementToArray(newView, groupName);
    }

    //this adds an element to an array. first by creating a new dataStructure object, then storing that in the correct array
    private void addElementToArray(View v, String groupName){
        PaperGpaElement struct = new PaperGpaElement((LinearLayout) v);

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
            case "lab":
                labElements.remove(labElements.size() - 1);
                break;
            case "assignment":
                assignmentElements.remove(assignmentElements.size() - 1);
                break;
            case "test":
                testElements.remove(testElements.size() - 1);
                break;
            case "exam":
                examElements.remove(examElements.size() - 1);
                break;
        }
    }

    //this method calculates the total grade for all elements on the page
    //due to the nature of the getContributionTotal() method of PaperGpaElement, any elements which are not fully filled out will be ignored
    private void calculateTotal(){
        TextView output = (TextView) findViewById(R.id.outputText);
        double marks = 0;
        double totalWorth = 0;

        //iterate through each collection
        for(ArrayList<PaperGpaElement> e: masterArray){
            for(PaperGpaElement i: e) {
                double toAdd = i.getContributionToTotal();
                //if the method returns -1, it signifies that an improper fraction has been put into the interface.
                //and that is has not been caught earlier
                //if the method returns -2, that means that one of the boxes has not been filled out
                if (toAdd == -1) {
                    displayToast("There was an improper fraction/s, these have been cleared.");
                    i.layout.setBackground(getDrawable(R.drawable.elementbackgrounderror));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackgrounderror));
                }else if(toAdd == -2){
                    i.layout.setBackground(getDrawable(R.drawable.elementbackground));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackground));
                }else{
                    marks += toAdd;
                    totalWorth += i.getWeight();
                    i.layout.setBackground(getDrawable(R.drawable.elementbackground));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackground));
                }
            }
        }

        if(totalWorth > 100){
            displayToast("The total weight cannot be greater than 100");
            return;
        }

        double percentage = (marks / totalWorth) * 100;

        //then do some formatting with the answer
        DecimalFormat df = new DecimalFormat("#.00");
        String rawPercentage = df.format(marks);
        String currentPercentage = df.format(percentage);

        String outputText = String.format("Currently you are at %s%% (%s)\n" +
                                            "Total marks to total %s", currentPercentage,getGrade(percentage) , rawPercentage);

        output.setText(outputText);

    }

    //This method gets and displays the amount you need in the final exam/ what is left for the target grade
    private void calculateReqExam(){
        double marks = 0;
        double totalWorth = 0;

        //iterate through each collection
        for(ArrayList<PaperGpaElement> e: masterArray){
            for(PaperGpaElement i: e) {
                double toAdd = i.getContributionToTotal();
                //if the method returns -1, it signifies that an improper fraction has been put into the interface.
                //and that is has not been caught earlier
                //if the method returns -2, that means that one of the boxes has not been filled out
                if (toAdd == -1) {
                    displayToast("There was an improper fraction/s, these have been cleared.");
                    i.layout.setBackground(getDrawable(R.drawable.elementbackgrounderror));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackgrounderror));
                }else if(toAdd == -2){
                    i.layout.setBackground(getDrawable(R.drawable.elementbackground));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackground));
                }else{
                    marks += toAdd;
                    totalWorth += i.getWeight();
                    i.layout.setBackground(getDrawable(R.drawable.elementbackground));
                    LinearLayout l =(LinearLayout) i.layout.getParent();
                    l = (LinearLayout) l.getParent();
                    l.setBackground(getDrawable(R.drawable.elementbackground));
                }
            }
        }

        if(totalWorth > 100){
            displayModal("Weight Limit Exceeded", "There is a total weight greater than 100\n" +
                    "please check and fix this.");
            return;
        }

        if(totalWorth == 100){
            displayModal("No calculation needed", "there is already a total weight of 100\n" +
                    "There is no additional calculation needed");
            return;
        }

        double currentPercentage = (marks / totalWorth) * 100;

        double targetPercentage = getGpaPercentage(getSeekBarValue());

        double requiredMarks = targetPercentage - marks;

        double marksLeft = 100 - totalWorth;

        double markNeededOnExam = (requiredMarks / marksLeft) * 100;

        DecimalFormat df = new DecimalFormat("#.00");

        String output = "You are at: " + df.format(currentPercentage) + "%" + " (" + getGrade(currentPercentage) + ")\n" +
                        "Current total weight entered: " + df.format(totalWorth) + "%\n" +
                        "Weight to be calculated: " + df.format(100 - totalWorth) + "%\n\n" +
                        "Desired Grade: " + df.format(targetPercentage) + "%" + " (" + getGrade(targetPercentage) + ")\n" +
                        "Grade needed on final exam: " + df.format(markNeededOnExam) + "%" + " (" + getGrade(markNeededOnExam) + ")\n";
        if(markNeededOnExam > 100){
            output += "\nUnfortunately you cannot achieve this grade";
        }

        displayModal("Grade Breakdown", output);


    }

    //this method returns the value of the seek bar
    private double getSeekBarValue(){
        SeekBar sb = (SeekBar) findViewById(R.id.seekBar);
        double value = sb.getProgress();
        return value;

    }

    private void displayModal(String title, String message){
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private double getGpaPercentage(double seekBarValue){
        try{
            double[] grades = {49, 50, 55, 60, 65, 70, 75, 80, 85, 90};
            //the casting of double to int removes all decimal numbers, rounded down
            return grades[(int) seekBarValue];
        }catch (Exception e){
            return 0;
        }
    }

    //this method returns the grade symbol out of a number out of 100
    private String getGrade(double number){
        if(isBetween(number, 0, 50)){
            return "D- D D+";
        }else if (isBetween(number, 50, 55)){
            return "C-";
        }else if (isBetween(number, 55, 60)){
            return "C";
        }else if (isBetween(number, 60, 65)){
            return "C+";
        }else if (isBetween(number, 65, 70)){
            return "B-";
        }else if (isBetween(number, 70, 75)){
            return "B";
        }else if (isBetween(number, 75, 80)){
            return "B+";
        }else if (isBetween(number, 80, 85)){
            return "A-";
        }else if (isBetween(number, 85, 90)){
            return "A";
        }else{
            return "A+";
        }
    }

    //this method simply returns true if num is between x(inclusive) and y(exclusive)
    private boolean isBetween(double num, double x, double y){
        return x <= num && num <y;

    }

    //a method that toggles the visibility of the elements of a particular category
    //For this to work at the moment, a rigid structure must be maintained
    public void toggleElementsVisibility(View v){
        LinearLayout parent = (LinearLayout) v.getParent().getParent();
        LinearLayout toHide = (LinearLayout) parent.getChildAt(parent.getChildCount() - 1);

        int visibility = toHide.getVisibility();

        if(visibility != 0){
            toHide.setVisibility(View.VISIBLE);
        }else {
            toHide.setVisibility(View.GONE);
        }
    }


    //this method sets up the number spinners for the page
    private void setUpSpinners(){
        getSpinners();
        setSpinnerChoices();
        setSpinnerListeners();
    }

    public void displayToast(String message){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
