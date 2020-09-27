package com.kolian.ballmove;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensMove;
    private Drawable drawBall;
    private GradientDrawable gradBall;
    private FrameLayout maxBounds;
    private ImageView ball;
    private float MOVEMENT_FOR_THE_BALL = 2.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensMove = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensMove, SensorManager.SENSOR_DELAY_FASTEST);

        ball = findViewById(R.id.ball);
        maxBounds = findViewById(R.id.maxBounds);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawBall = getDrawable(R.drawable.balll);
            gradBall = (GradientDrawable) drawBall;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensMove, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float xChange = -event.values[0];
        float yChange = -event.values[1];

        float ballMoveX = (ball.getX()+ball.getWidth()/2)/maxBounds.getWidth();
        float ballMoveY = (ball.getY()+ball.getHeight()/2)/maxBounds.getHeight();

        //LEFT
        if (xChange < -0.5f && ball.getX() >= 0 && ball.getX() <= maxBounds.getWidth()) {
            if (ball.getX() - MOVEMENT_FOR_THE_BALL >= 0) {
                ball.setX(ball.getX() - MOVEMENT_FOR_THE_BALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gradBall.setGradientCenter(ballMoveX,ballMoveY);
                    ball.setBackground(drawBall);
                }
            } else
                ball.setX(0);

        } else if (xChange > 0.5f && ball.getX() >= 0 && ball.getX() + ball.getWidth() <= maxBounds.getWidth()) {
            if (ball.getX() + MOVEMENT_FOR_THE_BALL + ball.getWidth() <= maxBounds.getWidth()) {
                ball.setX(ball.getX() + MOVEMENT_FOR_THE_BALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gradBall.setGradientCenter(ballMoveX,ballMoveY);
                    ball.setBackground(drawBall);
                }
            } else
                ball.setX(maxBounds.getWidth() - ball.getWidth());
        }

        //DOWN
        if (yChange < -0.5f && ball.getY() >= 0 && ball.getY()  + ball.getHeight()  <= maxBounds.getHeight()) {
            if (ball.getY() + MOVEMENT_FOR_THE_BALL >= 0) {
                ball.setY(ball.getY() + MOVEMENT_FOR_THE_BALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gradBall.setGradientCenter(ballMoveX, ballMoveY);
                    ball.setBackground(drawBall);
                }
            } else
                ball.setY(0);
        } else if (yChange > 0.5f && ball.getY() >= 0 && ball.getY()<= maxBounds.getHeight()) {
            if (ball.getY() - MOVEMENT_FOR_THE_BALL + ball.getHeight() <= maxBounds.getHeight()) {
                ball.setY(ball.getY() - MOVEMENT_FOR_THE_BALL);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gradBall.setGradientCenter(ballMoveX, ballMoveY);
                    ball.setBackground(drawBall);
                }
            } else
                ball.setY(maxBounds.getHeight() - ball.getHeight());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //
    }
}
