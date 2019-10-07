package com.example.hp.fitkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    EditText weightEdit;
    int weight;
    int height;
    ProgressBar progressBar;
    EditText heightEdit;
    SharedPreferences sp ;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("My Pref",MODE_PRIVATE);
        edit = sp.edit();
        weightEdit = findViewById(R.id.edit_weight);
        heightEdit =  findViewById(R.id.edit_height);
        Button submitButton =  findViewById(R.id.submit);

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = weightEdit.getText().toString();
                String s2 = heightEdit.getText().toString();
                sp = getSharedPreferences("pref",MODE_PRIVATE);
                edit.putString("weight",s1);
                edit.putString("height",s2);
                edit.commit();
                Intent intent = new Intent(MainActivity.this,Results.class);
                startActivity(intent);
            }
        });

    }
}
