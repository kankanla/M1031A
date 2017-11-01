package com.kankanla.m1031a;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView SHORT, SECOND;
    private Calendar calendar;
    private Handler handler;
    private Timer timer;
    private TimerTask timerTask;
    private WindowManager.LayoutParams lp;
    private int set_back = 1;
    private DateFormat dateFormat, dateFormat1, dateFormat2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time2);
        lp = getWindow().getAttributes();
        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        dateFormat1 = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        dateFormat2 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        App_set();
        SHORT = findViewById(R.id.SHORT);
        SECOND = findViewById(R.id.SECOND);
        handler = new Handler();
        timer = new Timer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        time_show_2();
    }

    protected void time_show_2() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        SHORT.setText(String.valueOf(dateFormat.format(date)));
                        SECOND.setText(String.valueOf(calendar.get(Calendar.SECOND)));
                        setTitle(String.valueOf(dateFormat2.format(date)));
                        String temp = dateFormat1.format(date);
                        if (temp.compareTo("9:16:00") > 0 && temp.compareTo("18:06:00") < 0) {
                            set_HIGH();
                            set_back = 0;
                        } else {
                            set_LOW();
                            set_back = 1;
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    protected void set_LOW() {
        if (set_back == 0) {
            lp.screenBrightness = 0.1F;
            System.out.println("0.001f");
            SHORT.setBackgroundColor(Color.BLACK);
            SECOND.setBackgroundColor(Color.BLACK);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            getWindow().setAttributes(lp);
        }
    }

    protected void set_HIGH() {
        if (set_back == 1) {
            lp.screenBrightness = 1.0F;
            System.out.println("1.0f");
            SHORT.setBackgroundColor(Color.WHITE);
            SECOND.setBackgroundColor(Color.WHITE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            getWindow().setAttributes(lp);
        }
    }

    protected void App_set() {
        //画面をスリープ状態にさせないためには
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //横画面は以下を指定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
