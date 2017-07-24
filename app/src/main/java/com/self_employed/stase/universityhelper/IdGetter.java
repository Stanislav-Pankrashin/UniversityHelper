package com.self_employed.stase.universityhelper;


//This class manages idGetting for generated elements in the document
//all ids refer to the ids.xml file. any new ids should be added there
public final class IdGetter {

    private static IdGetter getter = null;

    //i've made it so this class can only have one instance in the entire program.
    public static IdGetter getter(){
        if (getter == null){
            getter = new IdGetter();
            return getter;
        }else{
            return getter;
        }
    }

    public int getId(String type, int index){

        switch (type){
            case "textView":
                return getTextViewId(index);

            case"seekBar":
                return getSeekBarId(index);

            case "labElement":
                return getLabElementId(index);

            case "assignmentElement":
                return getAssignmentElementId(index);

            case "testElement":
                return getTestElementId(index);

            case "examElement":
                return getExamElementId(index);
        }
        return 0;

    }

    private int getTextViewId(int index){
        String id = "textView" + index;

        switch (id){
            case "textView1":
                return R.id.textView1;
            case "textView2":
                return R.id.textView2;
            case "textView3":
                return R.id.textView3;
            case "textView4":
                return R.id.textView4;
            case "textView5":
                return R.id.textView5;
            case "textView6":
                return R.id.textView6;
            case "textView7":
                return R.id.textView7;
            case "textView8":
                return R.id.textView8;
            case "textView9":
                return R.id.textView9;
            case "textView10":
                return R.id.textView10;
            case "textView11":
                return R.id.textView11;
            case "textView12":
                return R.id.textView12;
            case "textView13":
                return R.id.textView13;
            case "textView14":
                return R.id.textView14;
            case "textView15":
                return R.id.textView15;
            case "textView16":
                return R.id.textView16;
            case "textView17":
                return R.id.textView17;
            case "textView18":
                return R.id.textView18;
            case "textView19":
                return R.id.textView19;
            case "textView20":
                return R.id.textView20;
            case "textView21":
                return R.id.textView21;
            case "textView22":
                return R.id.textView22;
            case "textView23":
                return R.id.textView23;
            case "textView24":
                return R.id.textView24;
        }
        return 0;
    }

    private int getSeekBarId(int index){
        String id = "seekBar" + index;

        switch (id){
            case "seekBar1":
                return R.id.seekBar1;
            case "seekBar2":
                return R.id.seekBar2;
            case "seekBar3":
                return R.id.seekBar3;
            case "seekBar4":
                return R.id.seekBar4;
            case "seekBar5":
                return R.id.seekBar5;
            case "seekBar6":
                return R.id.seekBar6;
            case "seekBar7":
                return R.id.seekBar7;
            case "seekBar8":
                return R.id.seekBar8;
            case "seekBar9":
                return R.id.seekBar9;
            case "seekBar10":
                return R.id.seekBar10;
            case "seekBar11":
                return R.id.seekBar11;
            case "seekBar12":
                return R.id.seekBar12;
            case "seekBar13":
                return R.id.seekBar13;
            case "seekBar14":
                return R.id.seekBar14;
            case "seekBar15":
                return R.id.seekBar15;
            case "seekBar16":
                return R.id.seekBar16;
            case "seekBar17":
                return R.id.seekBar17;
            case "seekBar18":
                return R.id.seekBar18;
            case "seekBar19":
                return R.id.seekBar19;
            case "seekBar20":
                return R.id.seekBar20;
            case "seekBar21":
                return R.id.seekBar21;
            case "seekBar22":
                return R.id.seekBar22;
            case "seekBar23":
                return R.id.seekBar23;
            case "seekBar24":
                return R.id.seekBar24;
        }
        return 0;
    }

    private int getLabElementId(int index){
        String id = "labElement" + index;

        switch (id){
            case "labElement1":
                return R.id.labElement1;
            case "labElement2":
                return R.id.labElement2;
            case "labElement3":
                return R.id.labElement3;
            case "labElement4":
                return R.id.labElement4;
            case "labElement5":
                return R.id.labElement5;
            case "labElement6":
                return R.id.labElement6;
            case "labElement7":
                return R.id.labElement7;
            case "labElement8":
                return R.id.labElement8;
            case "labElement9":
                return R.id.labElement9;
            case "labElement10":
                return R.id.labElement10;
            case "labElement11":
                return R.id.labElement11;
            case "labElement12":
                return R.id.labElement12;
            case "labElement13":
                return R.id.labElement13;
            case "labElement14":
                return R.id.labElement14;
            case "labElement15":
                return R.id.labElement15;
            case "labElement16":
                return R.id.labElement16;
            case "labElement17":
                return R.id.labElement17;
            case "labElement18":
                return R.id.labElement18;
            case "labElement19":
                return R.id.labElement19;
            case "labElement20":
                return R.id.labElement20;
            case "labElement21":
                return R.id.labElement21;
            case "labElement22":
                return R.id.labElement22;
            case "labElement23":
                return R.id.labElement23;
            case "labElement24":
                return R.id.labElement24;
        }
        return 0;
    }

    private int getAssignmentElementId(int index){
        String id = "assignmentElement" + index;

        switch (id){
            case "assignmentElement1":
                return R.id.assignmentElement1;
            case "assignmentElement2":
                return R.id.assignmentElement2;
            case "assignmentElement3":
                return R.id.assignmentElement3;
            case "assignmentElement4":
                return R.id.assignmentElement4;
            case "assignmentElement5":
                return R.id.assignmentElement5;
            case "assignmentElement6":
                return R.id.assignmentElement6;
            case "assignmentElement7":
                return R.id.assignmentElement7;
            case "assignmentElement8":
                return R.id.assignmentElement8;
            case "assignmentElement9":
                return R.id.assignmentElement9;
            case "assignmentElement10":
                return R.id.assignmentElement10;
            case "assignmentElement11":
                return R.id.assignmentElement11;
            case "assignmentElement12":
                return R.id.assignmentElement12;
            case "assignmentElement13":
                return R.id.assignmentElement13;
            case "assignmentElement14":
                return R.id.assignmentElement14;
            case "assignmentElement15":
                return R.id.assignmentElement15;
            case "assignmentElement16":
                return R.id.assignmentElement16;
            case "assignmentElement17":
                return R.id.assignmentElement17;
            case "assignmentElement18":
                return R.id.assignmentElement18;
            case "assignmentElement19":
                return R.id.assignmentElement19;
            case "assignmentElement20":
                return R.id.assignmentElement20;
            case "assignmentElement21":
                return R.id.assignmentElement21;
            case "assignmentElement22":
                return R.id.assignmentElement22;
            case "assignmentElement23":
                return R.id.assignmentElement23;
            case "assignmentElement24":
                return R.id.assignmentElement24;
        }
        return 0;
    }

    private int getTestElementId(int index){
        String id = "testElement" + index;

        switch (id){
            case "testElement1":
                return R.id.testElement1;
            case "testElement2":
                return R.id.testElement2;
            case "testElement3":
                return R.id.testElement3;
            case "testElement4":
                return R.id.testElement4;
            case "testElement5":
                return R.id.testElement5;
            case "testElement6":
                return R.id.testElement6;
            case "testElement7":
                return R.id.testElement7;
            case "testElement8":
                return R.id.testElement8;
            case "testElement9":
                return R.id.testElement9;
            case "testElement10":
                return R.id.testElement10;
            case "testElement11":
                return R.id.testElement11;
            case "testElement12":
                return R.id.testElement12;
            case "testElement13":
                return R.id.testElement13;
            case "testElement14":
                return R.id.testElement14;
            case "testElement15":
                return R.id.testElement15;
            case "testElement16":
                return R.id.testElement16;
            case "testElement17":
                return R.id.testElement17;
            case "testElement18":
                return R.id.testElement18;
            case "testElement19":
                return R.id.testElement19;
            case "testElement20":
                return R.id.testElement20;
            case "testElement21":
                return R.id.testElement21;
            case "testElement22":
                return R.id.testElement22;
            case "testElement23":
                return R.id.testElement23;
            case "testElement24":
                return R.id.testElement24;
        }
        return 0;
    }

    private int getExamElementId(int index){
        String id = "examElement" + index;

        switch (id){
            case "examElement1":
                return R.id.examElement1;
            case "examElement2":
                return R.id.examElement2;
            case "examElement3":
                return R.id.examElement3;
            case "examElement4":
                return R.id.examElement4;
            case "examElement5":
                return R.id.examElement5;
            case "examElement6":
                return R.id.examElement6;
            case "examElement7":
                return R.id.examElement7;
            case "examElement8":
                return R.id.examElement8;
            case "examElement9":
                return R.id.examElement9;
            case "examElement10":
                return R.id.examElement10;
            case "examElement11":
                return R.id.examElement11;
            case "examElement12":
                return R.id.examElement12;
            case "examElement13":
                return R.id.examElement13;
            case "examElement14":
                return R.id.examElement14;
            case "examElement15":
                return R.id.examElement15;
            case "examElement16":
                return R.id.examElement16;
            case "examElement17":
                return R.id.examElement17;
            case "examElement18":
                return R.id.examElement18;
            case "examElement19":
                return R.id.examElement19;
            case "examElement20":
                return R.id.examElement20;
            case "examElement21":
                return R.id.examElement21;
            case "examElement22":
                return R.id.examElement22;
            case "examElement23":
                return R.id.examElement23;
            case "examElement24":
                return R.id.examElement24;
        }
        return 0;
    }


}
