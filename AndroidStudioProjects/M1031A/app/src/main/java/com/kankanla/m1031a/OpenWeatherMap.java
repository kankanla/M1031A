package com.kankanla.m1031a;

import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kankanla on 2018/01/23.
 */

public class OpenWeatherMap {
    protected Context context;
    protected Temp_CallBack temp_callBack;

    public OpenWeatherMap(Context context, Temp_CallBack temp_callBack) {
        this.context = context;
        this.temp_callBack = temp_callBack;
    }

    protected void getTemp() {
        GetTemp getTemp = new GetTemp();
        getTemp.execute("OpenWeatherMap");
    }

    public interface Temp_CallBack {
        public void show_temp(String json);
    }

    public class GetTemp extends AsyncTask<String, Integer, String> {
        //By ZIP code
        //http://samples.openweathermap.org/data/2.5/weather?zip=94040,us&appid=b6907d289e10d714a6e88b30761fae22
        //By city name
        //http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22

        private String APPID = "fdac44bd9dab8aff16563641cc0ef97e";
        private String API_URL = "http://api.openweathermap.org/data/2.5/weather?";
        private StringBuffer REQ_URL;
        protected HttpURLConnection httpURLConnection;
        protected String Url;
        protected URL HTTP_REQ_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            REQ_URL = new StringBuffer();
            REQ_URL.append(API_URL);
//            REQ_URL.append("q=tokyo");
            REQ_URL.append("zip=275-0024,jp");

            REQ_URL.append("&units=metric");
//            REQ_URL.append("&lang=zh_cn");
            REQ_URL.append("&appid=");
            REQ_URL.append(APPID);
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            String json = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                HTTP_REQ_url = new URL(REQ_URL.toString());
                httpURLConnection = (HttpURLConnection) HTTP_REQ_url.openConnection();
                httpURLConnection.setConnectTimeout(1000 * 3);
                httpURLConnection.setReadTimeout(1000 * 3);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                    int len = 0;
                    byte[] bufr = new byte[1024];
                    while ((len = inputStream.read(bufr)) != -1) {
                        byteArrayOutputStream.write(bufr, 0, len);
                    }
                    inputStream.close();
                }

            } catch (MalformedURLException e) {
//                URL
                e.printStackTrace();
            } catch (IOException e) {
//                httpURLConnection
                e.printStackTrace();
            }

            json = byteArrayOutputStream.toString();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
//            System.out.println(REQ_URL);    //

//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                JSONObject jsonObject1 = (JSONObject) jsonObject.get("main");
//                System.out.println(jsonObject1.getString("temp"));
//                System.out.println(jsonObject1.getString("humidity"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            temp_callBack.show_temp(json);

        }
    }

}
