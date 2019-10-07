package com.example.hp.fitkit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BmiFragment extends Fragment {
    TextView tv, category,ht,wt;
    FloatingActionButton steps;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    int weightL, heightL;
    double bmi_results ;

    public BmiFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bmi, null);

        sp = getActivity().getSharedPreferences("My Pref", Context.MODE_PRIVATE);
        tv = rootView.findViewById(R.id.bmi_tv);
        category = rootView.findViewById(R.id.category_tv);
        ht = rootView.findViewById(R.id.height_tv);
        wt = rootView.findViewById(R.id.weight_tv);
        steps = rootView.findViewById(R.id.stepsfab);

        String weight = sp.getString("weight", "");
        wt.setText(weight);
        String height = sp.getString("height", "");
        ht.setText(height);
        if (weight.isEmpty() && height.isEmpty()) {
            Toast.makeText(getActivity(), "enter valid", Toast.LENGTH_SHORT).show();
        } else {
            weightL = Integer.parseInt(weight);
            heightL = Integer.parseInt(height);
        }
        bmi_results = (weightL * 10000) / (heightL * heightL);
        String sol = Double.toString(bmi_results);
          tv.setText(""+sol);

       int bmiIndex = (int) Math.floor(bmi_results);
        if (bmiIndex < 18 && bmiIndex > 0) {
            category.setText("UNDER WEIGHT");
        } else if (bmiIndex >= 18 && bmiIndex <= 25) {
            category.setText("NORMAL");
        } else if (bmiIndex > 25 && bmiIndex < 30) {
            category.setText("OVERWEIGHT");
        } else {
            category.setText("OBESE");
        }

        GradientDrawable bmiColor = (GradientDrawable) category.getBackground();
        int magnitudeColor = getMagnitudeColor(bmi_results);
        bmiColor.setColor(magnitudeColor);

        steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),StepsCount.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                magnitudeColorResourceId = R.color.bmi17;
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24: magnitudeColorResourceId = R.color.bmi19;
                break;
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                magnitudeColorResourceId = R.color.bmi29;
                break;
            default:
                magnitudeColorResourceId = R.color.bmi30plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
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
