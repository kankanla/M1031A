package com.kankanla.m1031a;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView SHORT, SECOND, TDATE, TEMP;
    private Calendar calendar;
    private Handler handler;
    private WindowManager.LayoutParams lp;
    private DateFormat dateFormat, dateFormat2;
    private SimpleDateFormat simpleDateFormat, simpleDateFormat2, simpleDateFormat3;
    private String start_time;
    private String end_time;
    private TimerTask timerTask;
    private Timer timer;
    private TimerTask timerTask2;
    private Timer timer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_time2);
        lp = getWindow().getAttributes();
        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
        dateFormat2 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        simpleDateFormat = new SimpleDateFormat("ss");
        simpleDateFormat2 = new SimpleDateFormat("HHmmss");
        simpleDateFormat3 = new SimpleDateFormat("D");

        handler = new Handler();
        timer = new Timer();
        timer2 = new Timer();
        TDATE = findViewById(R.id.tdate);
        SHORT = findViewById(R.id.SHORT);
        SECOND = findViewById(R.id.SECOND);
        TEMP = findViewById(R.id.TEMP);
        TEMP.setTextSize(200);
        TDATE.setTextSize(200);
        App_set();
        get_MAC_IP();

        setStart_time("092000");
        setEnd_time("180500");

//        SHORT.setBackgroundColor(Color.YELLOW);
//        SHORT.setTextColor(Color.RED);

//        http://www.fonts4free.net/
//        http://www.fonts4free.net/oreos-font.html
//        SHORT.setTypeface(Typeface.createFromAsset(getAssets(),"PLOK____.TTF"));
//        SHORT.setTypeface(Typeface.createFromAsset(getAssets(),"Royalacid_o.ttf"));
//        SHORT.setTypeface(Typeface.createFromAsset(getAssets(),"PWFlymetothemoon.ttf"));
//        SHORT.setTypeface(Typeface.createFromAsset(getAssets(), "hassle.ttf"));
        SHORT.setTypeface(Typeface.createFromAsset(getAssets(), "hassle.ttf"));

//        SECOND.setTypeface(Typeface.createFromAsset(getAssets(),"PLOK____.TTF"));
//        SECOND.setTypeface(Typeface.createFromAsset(getAssets(),"PWFlymetothemoon.ttf"));
//        SECOND.setTypeface(Typeface.createFromAsset(getAssets(),"Royalacid_o.ttf"));
//        SECOND.setTypeface(Typeface.createFromAsset(getAssets(), "hassle.ttf"));
//        SECOND.setTypeface(Typeface.createFromAsset(getAssets(), "heroesassembledings.ttf"));
        SECOND.setTypeface(Typeface.createFromAsset(getAssets(), "NINJAS.TTF"));
        TDATE.setTypeface(Typeface.createFromAsset(getAssets(), "Royalacid_o.ttf"));
        TEMP.setTypeface(Typeface.createFromAsset(getAssets(), "Royalacid_o.ttf"));


//        練習のテスト項目
//        BT_get();
//        find_db();
//        TempSensors tempSensors = new TempSensors(this);
//        tempSensors.getSen();
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                test_temp(TEMP);

            }
        };

        timer2.schedule(timerTask2, 0, 1000 * 60 * 5);

    }


    public void test_temp(final TextView textView) {
        OpenWeatherMap openWeatherMap = new OpenWeatherMap(this, new OpenWeatherMap.Temp_CallBack() {
            @Override
            public void show_temp(String json) {
                System.out.println("9999999999999999999999999999999999999999");
                System.out.println(json);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    JSONObject jsonObject1 = (JSONObject) jsonObject.get("main");
                    System.out.println(jsonObject1.getString("temp"));
                    textView.setText(jsonObject1.getString("temp"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("9999999999999999999999999999999999999999");
            }
        });
        openWeatherMap.getTemp();
    }


    @Override
    protected void onResume() {
        super.onResume();
        time_show_2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.FontSize));
        menu.add(getString(R.string.Second_Font_Size));
        menu.add(1, 2, 3, getString(R.string.FontSize));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
                        SECOND.setText(simpleDateFormat.format(date));
                        TDATE.setText(simpleDateFormat3.format(date));
                        setTitle(String.valueOf(dateFormat2.format(date)) + "     " + get_MAC_IP());
                        String temp = simpleDateFormat2.format(date);
                        if (temp.compareTo(start_time) > 0 && temp.compareTo(end_time) < 0) {
                            set_HIGH();
                        } else {
                            set_LOW();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 500);
    }

    protected void set_LOW() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        lp.screenBrightness = 0.01F;
        SHORT.setBackgroundColor(Color.BLACK);
        SECOND.setBackgroundColor(Color.BLACK);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setAttributes(lp);
    }

    protected void set_HIGH() {
        if (calendar.get(Calendar.DAY_OF_WEEK) > Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            lp.screenBrightness = 1.0F;
            SHORT.setBackgroundColor(Color.WHITE);
            SECOND.setBackgroundColor(Color.TRANSPARENT);
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

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    protected String get_MAC_IP() {
        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        System.out.println("-------------------------------------------");
//        System.out.println(wifiInfo.getMacAddress());
//        System.out.println(wifiInfo);
//        System.out.println("-------------------------------------------");
        int ipAddress = wifiInfo.getIpAddress();
        String strIPAddess =
                ((ipAddress >> 0) & 0xFF) + "." +
                        ((ipAddress >> 8) & 0xFF) + "." +
                        ((ipAddress >> 16) & 0xFF) + "." +
                        ((ipAddress >> 24) & 0xFF);
        return strIPAddess + " (" + wifiInfo.getSSID() + ")";
    }

    //練習のテスト項目
    protected void BT_get() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println("-------------------------------------------------------1----");
        System.out.println(bluetoothAdapter.enable());
        System.out.println(bluetoothAdapter.getAddress());
        System.out.println(bluetoothAdapter.getName());
        System.out.println("-------------------------------------------------------2----");

        Set<BluetoothDevice> bluetoothDevice = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice bluetoothDevice1 : bluetoothDevice) {
            System.out.println("-------------------------------------------------------3----");
            System.out.println(bluetoothDevice1.getAddress());
            System.out.println(bluetoothDevice1.getName());
            System.out.println("-------------------------------------------------------4----");
        }

    }

    //練習のテスト項目
    protected void find_db() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice bluetoothDevice1 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    bluetoothDevice1.getAddress();
                    System.out.println("-----------------------------------------------5------------");
                    System.out.println(bluetoothDevice1.getName());
                    System.out.println(bluetoothDevice1.getAddress());
                    Toast.makeText(context, bluetoothDevice1.getName(), Toast.LENGTH_SHORT).show();
                    System.out.println("-----------------------------------------------6------------");
                }
            }
        };


        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(broadcastReceiver, intentFilter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!bluetoothAdapter.isDiscovering()) {
                        bluetoothAdapter.startDiscovery();
                    }
                }
            }
        }).start();

    }
}


