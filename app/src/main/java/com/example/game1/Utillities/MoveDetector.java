

package com.example.game1.Utillities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MoveDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private MoveListener moveListener;

    private long timestamp = 0L;


    public MoveDetector(Context context, MoveListener moveListener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveListener = moveListener;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                calculateMove(x);
            }

            private void calculateMove(float x) {
                if (System.currentTimeMillis() - timestamp > 500) {
                    timestamp = System.currentTimeMillis();
                    if (x > 3.0) {
                        if (moveListener != null) {
                            moveListener.onMoveLeft();
                        }
                    } else if (x < -3.0) {
                        if (moveListener != null) {
                            moveListener.onMoveRight();
                        }
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    public void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}

