package com.example.hp.fitkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class StepsCount extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    ImageButton play,pause;
    Button reset;

    SensorManager sensorManager;
    float stepsSensor=0.0f;
    float stepsReset;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_steps_count);

        tv_steps = findViewById(R.id.steps_view);
        play = findViewById(R.id.playBtn);
        pause = findViewById(R.id.pauseBtn);
        reset = findViewById(R.id.reset);

        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
                running = true;
                Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                if(countSensor != null){
                    sensorManager.registerListener(StepsCount.this,countSensor,SensorManager.SENSOR_DELAY_UI);
                } else{
                    Toast.makeText(StepsCount.this, "Sensor not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                running = false;
                sensorManager.unregisterListener(StepsCount.this);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepsReset = stepsSensor;

                SharedPreferences.Editor editor =
                        getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit();
                editor.putFloat("stepsAtReset", stepsReset);
                editor.commit();

                // you can now display 0:
                tv_steps.setText(String.valueOf(0));
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        running=true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(StepsCount.this,countSensor,SensorManager.SENSOR_DELAY_UI);
        } else{
            Toast.makeText(StepsCount.this, "Sensor not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            stepsSensor = Float.parseFloat(String.valueOf(event.values[0]));
            float stepsSinceReset = stepsSensor - stepsReset;
            tv_steps.setText(String.valueOf(stepsSinceReset));
        }else{
            event.values[0] = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

