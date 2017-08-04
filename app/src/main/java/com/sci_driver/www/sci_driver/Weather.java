package com.sci_driver.www.sci_driver;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
// by making HTTP POST or GET method
    public JSONObject makeHttpRequest(String url, String method,
                                      List params) throws IOException {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
public void locchange(){

        double NewLAT = DecimalConverter(location.getLatitude());
        double NewLON = DecimalConverter(location.getLongitude());
        if(PreLAT != NewLAT | PreLON != NewLON){
            PreLAT = DecimalConverter(location.getLatitude());
            PreLON = DecimalConverter(location.getLongitude());
            String LocString = "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=33bdd961aa314ce298241541170308&";
            LocString = LocString + "q=" + Double.toString(PreLAT) + "," + Double.toString(PreLON) +"&";
            LocString = LocString  + "num_of_days=1&" + "format=json";
            InputStream myStream=getStream(LocString);
            WeatherJSON = myStream.toString();

        }}
    public double DecimalConverter(double number){
        double res = (long)Math.floor(number*10000);
        return res/10000;
    };