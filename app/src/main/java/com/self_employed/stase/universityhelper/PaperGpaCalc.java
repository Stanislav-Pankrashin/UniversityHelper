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
    private ArrayList<Spinner> spinners;
    //private RelativeLayout layout;
    static final String[] SPINNER_NAMES = {"labSpinner", "assignmentSpinner", "testSpinner", "examSpinner"};


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

                //return if the position is 0, because this is the default instantiated position
                if(position == 0){
                    return;
                }

                if(str_id.contains("lab")){
                    LinearLayout parentLayout = (LinearLayout) parent.getParent().getParent();
                    int insertIndex = parentLayout.getChildCount();

                    for(int i = 1; i <= position; i++) {
                        PaperGpaCalc.this.inflateLayout(parentLayout, insertIndex);
                        insertIndex++;
                    }


                }else if (str_id.contains("assignment")){

                }else if (str_id.contains("test")){

                }else if (str_id.contains("exam")){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void inflateLayout(LinearLayout parent, int index){
        LinearLayout newView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.paper_element, null);
        parent.addView(newView, index);
    }

    private void setUpSpinners(){
        getSpinners();
        setSpinnerChoices();
        setSpinnerListeners();
    }
}
