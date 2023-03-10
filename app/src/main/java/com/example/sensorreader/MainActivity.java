package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorGravity;
    private Sensor mSensorGyroscope;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorAccelerometer;
    private TextView mTextSensorGravity;
    private TextView mTextSensorGyroscope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorLight = findViewById(R.id.label_Light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorAccelerometer = findViewById(R.id.label_accelerometer);
        mTextSensorGravity = findViewById(R.id.label_gravity);
        mTextSensorGyroscope = findViewById(R.id.label_gyroscope);

        mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        String sensor_error = "No Sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorAccelerometer == null){
            mTextSensorAccelerometer.setText(sensor_error);
        }
        if (mSensorGravity == null){
            mTextSensorGravity.setText(sensor_error);
        }
        if (mSensorGyroscope == null){
            mTextSensorGyroscope.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mSensorProximity != null){
            sensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorLight != null){
            sensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorAccelerometer != null){
            sensorManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorGravity != null){
            sensorManager.registerListener(this, mSensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorGyroscope != null){
            sensorManager.registerListener(this, mSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light Sensor : %1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_ACCELEROMETER:
                mTextSensorAccelerometer.setText(String.format("Accelerometer Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_GRAVITY:
                mTextSensorGravity.setText(String.format("Gravity Sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_GYROSCOPE:
                mTextSensorGyroscope.setText(String.format("Gyroscope Sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    private void changeBackgroundColor(float currentValue) {
        ConstraintLayout layout = findViewById(R.id.layout_contrain);
        if (currentValue >= 2000 && currentValue <= 40000) {
            layout.setBackgroundColor(Color.RED);
        } else {
            layout.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}