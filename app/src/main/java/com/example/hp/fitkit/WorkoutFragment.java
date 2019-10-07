package com.example.hp.fitkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WorkoutFragment extends Fragment {
    TextView chesttv,backtv,bicepstv,tricepstv,shoulderstv,legstv,warmuptv;
    Button chestbtn,backbtn,bicepsbtn,tricepsbtn,shouldersbtn,legsbtn;
    CardView chestcv,backcv,bicepscv,tricepscv,shouldercv,legscv,warmupcv;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    int weight,height;
    SQLiteDatabase sql,sql1,sql2,sql3,sql4,sql5;
    double bmi_results;
    String category,warmupexercise;

    public WorkoutFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workout,null);
        sp = getActivity().getSharedPreferences("My Pref",Context.MODE_PRIVATE);

        sql = getActivity().openOrCreateDatabase("Chest",Context.MODE_PRIVATE,null);
        sql1 = getActivity().openOrCreateDatabase("Back",Context.MODE_PRIVATE,null);
       sql2 = getActivity().openOrCreateDatabase("Biceps",Context.MODE_PRIVATE,null);
        sql3 = getActivity().openOrCreateDatabase("Triceps",Context.MODE_PRIVATE,null);
        sql4 = getActivity().openOrCreateDatabase("Shoulders",Context.MODE_PRIVATE,null);
        sql5 = getActivity().openOrCreateDatabase("Legs",Context.MODE_PRIVATE,null);
        chesttv = rootView.findViewById(R.id.chest);
        backtv = rootView.findViewById(R.id.back);
        bicepstv = rootView.findViewById(R.id.biceps);
        tricepstv = rootView.findViewById(R.id.triceps);
        shoulderstv = rootView.findViewById(R.id.shoulders);
        legstv = rootView.findViewById(R.id.legs);
        warmuptv = rootView.findViewById(R.id.warmups);
        chestbtn = rootView.findViewById(R.id.chestbtn);
        backbtn = rootView.findViewById(R.id.backbtn);
        bicepsbtn = rootView.findViewById(R.id.bicepsbtn);
        tricepsbtn = rootView.findViewById(R.id.tricepsbtn);
        shouldersbtn = rootView.findViewById(R.id.shouldersbtn);
        legsbtn = rootView.findViewById(R.id.legsbtn);
        chestcv = rootView.findViewById(R.id.chestcv);
        backcv = rootView.findViewById(R.id.backcv);
        bicepscv = rootView.findViewById(R.id.bicepscv);
        tricepscv = rootView.findViewById(R.id.tricepscv);
        shouldercv = rootView.findViewById(R.id.shoulderscv);
        legscv = rootView.findViewById(R.id.legscv);
        warmupcv= rootView.findViewById(R.id.warmupcv);

        String s1 = sp.getString("weight","");
        String s2 = sp.getString("height", "");

        if(s1.isEmpty() && s2.isEmpty()) {
            Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();
        }
        else {
            weight = Integer.parseInt(s1);
            height = Integer.parseInt(s2);
        }
        bmi_results = (weight*10000)/(height*height);
        int bmiFloor = (int) Math.floor(bmi_results);
     if (bmiFloor < 18 && bmiFloor > 0) {
      category = "underweight";
     } else if (bmiFloor >= 18 && bmiFloor <= 25) {
      category="normal";
     } else if (bmiFloor > 25 && bmiFloor < 30) {
      category="overweight";
     } else {
      category="obese";
     }

     warmupexercise = "3 set PushUps" + "\n" +"3 Set ChinUps" + "\n" + "3 Set Squats";
     warmuptv.setText(warmupexercise);

    chestbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            chestcv.setVisibility(View.VISIBLE);
            backcv.setVisibility(View.GONE);
            bicepscv.setVisibility(View.GONE);
            tricepscv.setVisibility(View.GONE);
            shouldercv.setVisibility(View.GONE);
            legscv.setVisibility(View.GONE);
        }
    });
     backbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             chestcv.setVisibility(View.GONE);
             backcv.setVisibility(View.VISIBLE);
             bicepscv.setVisibility(View.GONE);
             tricepscv.setVisibility(View.GONE);
             shouldercv.setVisibility(View.GONE);
             legscv.setVisibility(View.GONE);
         }
     });
        bicepsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestcv.setVisibility(View.GONE);
                backcv.setVisibility(View.GONE);
                bicepscv.setVisibility(View.VISIBLE);
                tricepscv.setVisibility(View.GONE);
                shouldercv.setVisibility(View.GONE);
                legscv.setVisibility(View.GONE);
            }
        });
        tricepsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestcv.setVisibility(View.GONE);
                backcv.setVisibility(View.GONE);
                bicepscv.setVisibility(View.GONE);
                tricepscv.setVisibility(View.VISIBLE);
                shouldercv.setVisibility(View.GONE);
                legscv.setVisibility(View.GONE);
            }
        });
        shouldersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestcv.setVisibility(View.GONE);
                backcv.setVisibility(View.GONE);
                bicepscv.setVisibility(View.GONE);
                tricepscv.setVisibility(View.GONE);
                shouldercv.setVisibility(View.VISIBLE);
                legscv.setVisibility(View.GONE);
            }
        });
        legsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chestcv.setVisibility(View.GONE);
                backcv.setVisibility(View.GONE);
                bicepscv.setVisibility(View.GONE);
                tricepscv.setVisibility(View.GONE);
                shouldercv.setVisibility(View.GONE);
                legscv.setVisibility(View.VISIBLE);
            }
        });

        try{
            String s = "Create table chest(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql.execSQL(s);
        }catch (Exception e) { }
        String s3 = "Insert into chest values('underweight','Bench Press','Incline Dumbell Press','Incline Press','Dumbell Fly','PullOver','Bar Dips')";
        sql.execSQL(s3);
        String s20 = "Insert into chest values('normal','Bench Press','Incline Dumbell Press','Incline Press','Dumbell Fly','Decline Fly','PullOver')";
        sql.execSQL(s20);
        String s27 = "Insert into chest values('overweight','Bench Press','Incline Dumbell Press','Decline Fly','Dumbell Fly','Incline Hammer Press','PullOver')";
        sql.execSQL(s27);
        String s32 = "Insert into chest values('obese','Bench Press','Incline Dumbell Press','Decline Fly','Dumbell Fly','Incline Hammer Press','PullOver')";
        sql.execSQL(s32);

        try {
            Cursor cs = sql.rawQuery("SELECT * FROM chest WHERE category='" + category + "'", null);
            String chestExercise = "";
            while (cs.moveToNext()) {
                chestExercise = cs.getString(1) + "\n" + cs.getString(2) + "\n" + cs.getString(3) + "\n" + cs.getString(4) + "\n" + cs.getString(5) + "\n" + cs.getString(6);
            }
            chesttv.setText(chestExercise);
        }catch(Exception e){Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();}



        try{
            String x = "Create table back(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql1.execSQL(x);
        }catch (Exception e) { }
        String a1 = "Insert into back values('underweight','Front Rod','Back Rod','Dumbell Rowing','Seated Pulley','Close Grip','Deadlift')";
        sql1.execSQL(a1);
        String a18 = "Insert into back values('normal','Front Rod','Back Rod','Dumbell Rowing','T Bar','Seated Pulley','Deadlift')";
        sql1.execSQL(a18);
        String a25 = "Insert into back values('overweight','Front Rod','Dumbell Rowing','Bent Over Rowing','T Bar','Seated Pulley','Deadlift')";
        sql1.execSQL(a25);
        String a30 = "Insert into back values('obese','Front Rod','Dumbell Rowing','Bent Over Rowing','T Bar','Seated Pulley','Deadlift')";
        sql1.execSQL(a30);

        try{
            Cursor cs1 = sql1.rawQuery("SELECT * FROM back WHERE category='" + category + "'", null);
            String backExercise = "";
            while(cs1.moveToNext()){
                backExercise = cs1.getString(1) + "\n" + cs1.getString(2) + "\n" + cs1.getString(3) + "\n" + cs1.getString(4) + "\n" + cs1.getString(5) + "\n" + cs1.getString(6);
            }
            backtv.setText(backExercise);
        }catch (Exception e) {}


        try{
            String y = "Create table biceps(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql2.execSQL(y);
        }catch (Exception e) { }
        String b1 = "Insert into biceps values('underweight','Dumbell Curls','Barbell Curls','Hammer Curls','Preacher Curls','Zottman Curl','Concentration Curls')";
        sql2.execSQL(b1);
        String b18 = "Insert into biceps values('normal','21 set','Dumbell Curl','Preacher Curls','Rope Hammer Culs','Barbell Curls','Concentration Curls')";
        sql2.execSQL(b18);
        String b25 = "Insert into biceps values('overweight','21 set','Incline Dumbell Curl','Rope Hammer Curl','Preacher Curls','Barbell Curls','Concentration Curls')";
        sql2.execSQL(b25);
        String b30 = "Insert into biceps values('obese','21 set','Incline Dumbell Curl','Rope Hammer Curl','Preacher Curls','Barbell Curls','Concentration Curls')";
        sql2.execSQL(b30);

        try{
            Cursor cs2 = sql2.rawQuery("SELECT * FROM biceps WHERE category='" + category + "'", null);
            String bicepExercise = "";
            while(cs2.moveToNext()){
                bicepExercise = cs2.getString(1) + "\n" + cs2.getString(2) + "\n" + cs2.getString(3) + "\n" + cs2.getString(4) + "\n" + cs2.getString(5) + "\n" + cs2.getString(6);
            }
            bicepstv.setText(bicepExercise);
        }catch (Exception e) {}



        try{
            String z = "Create table triceps(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql3.execSQL(z);
        }catch (Exception e) { }
        String c1 = "Insert into triceps values('underweight','Pulley Pushdown','Single Hand','Double Hand','Rope Extension','Diamond Pushup','Parallel Bar')";
        sql3.execSQL(c1);
        String c18 = "Insert into triceps values('normal','Rope Extension','Single Hand','Skull Crusher','Double Hand','Diamond Pushups','Parallel Bar')";
        sql3.execSQL(c18);
        String c25 = "Insert into triceps values('overweight','Cable Extension','Single Hand','Rope Hammer Curl','Skull Crusher','Double Hand','Parallel Bar')";
        sql3.execSQL(c25);
        String c30 = "Insert into triceps values('obese','Cable Extension','Single Hand','Rope Hammer Curl','Skull Crusher','Double Hand','Parallel Bar')";
        sql3.execSQL(c30);

        try{
            Cursor cs3 = sql3.rawQuery("SELECT * FROM triceps WHERE category='" + category + "'", null);
            String tricepExercise = "";
            while(cs3.moveToNext()){
                tricepExercise = cs3.getString(1) + "\n" + cs3.getString(2) + "\n" + cs3.getString(3) + "\n" + cs3.getString(4) + "\n" + cs3.getString(5) + "\n" + cs3.getString(6);
            }
            tricepstv.setText(tricepExercise);
        }catch (Exception e) {}



        try{
            String w = "Create table shoulders(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql4.execSQL(w);
        }catch (Exception e) { }
        String d1 = "Insert into shoulders values('underweight','','Barbell Standing Press','Seated Dumbell Press','Arnold Press','Front Raise','Shrugs')";
        sql4.execSQL(d1);
        String d18 = "Insert into shoulders values('normal','Military Press','Lateral Raise','Seated Dumbell Press','Arnold Press','Up-Right Row ','Shrugs')";
        sql4.execSQL(d18);
        String d25 = "Insert into shoulders values('overweight','Military Press','Front Raise','Lateral Raise','Arnold Press','Up-Right Row','Shrugs')";
        sql4.execSQL(d25);
        String d30 = "Insert into shoulders values('obese','Military Press','Front Raise','Lateral Raise','Arnold Press','Up-Right Row','Shrugs')";
        sql4.execSQL(d30);

        try{
            Cursor cs4 = sql4.rawQuery("SELECT * FROM shoulders WHERE category='" + category + "'", null);
            String shoulderExercise = "";
            while(cs4.moveToNext()){
                shoulderExercise = cs4.getString(1) + "\n" + cs4.getString(2) + "\n" + cs4.getString(3) + "\n" + cs4.getString(4) + "\n" + cs4.getString(5) + "\n" + cs4.getString(6);
            }
            shoulderstv.setText(shoulderExercise);
        }catch (Exception e) {}



        try{
            String u = "Create table legs(category varchar(20),ex1 varchar(20),ex2 varchar(20),ex3 varchar(20),ex4 varchar(20),ex5 varchar(20),ex6 varchar(20))";
            sql5.execSQL(u);
        }catch (Exception e) { }
        String e1 = "Insert into legs values('underweight','Dumbell Squat','Lunges','Leg Press','Lying Leg Curls','Leg Extension','Standing Calf Raises')";
        sql5.execSQL(e1);
        String e18 = "Insert into legs values('normal','Barbell Squat','Dumbell Lunges','Leg Press','Lying Leg Curls','Leg Extension','Standing Calf Raises')";
        sql5.execSQL(e18);
        String e25 = "Insert into legs values('overweight','Barbell Squat','Dumbell Lunges','Leg Press','Lying Leg Curls','Leg Extension','Standing Calf Raises')";
        sql5.execSQL(e25);
        String e30 = "Insert into legs values('obese','Barbell Squat','Dumbell Lunges','Leg Press','Lying Leg Curls','Leg Extension','Standing Calf Raises')";
        sql5.execSQL(e30);

        try{
            Cursor cs5 = sql5.rawQuery("SELECT * FROM legs WHERE category='" + category + "'", null);
            String legsExercise = "";
            while(cs5.moveToNext()){
                legsExercise = cs5.getString(1) + "\n" + cs5.getString(2) + "\n" + cs5.getString(3) + "\n" + cs5.getString(4) + "\n" + cs5.getString(5) + "\n" + cs5.getString(6);
            }
            legstv.setText(legsExercise);
        }catch (Exception e) {}
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.chatBox){
            Intent intent = new Intent(getContext(),ChatActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
