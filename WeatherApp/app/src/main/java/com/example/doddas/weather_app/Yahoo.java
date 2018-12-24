package com.example.doddas.weather_app;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Yahoo extends AsyncTask<String,Void,String> {
    ArrayList<MyData> myData;
    Context ct;
    RecyclerView rv;
    public Yahoo(ArrayList<MyData> data, MainActivity mainActivity, RecyclerView rv)
    {
        this.rv=rv;
        myData=data;
        this.ct=mainActivity;
    }
    @Override
    protected String doInBackground(String... strings) {
        String location=strings[0];
        String urlString="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+location+"%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        StringBuffer sb=new StringBuffer();
        Uri builtUri=Uri.parse(urlString).buildUpon().build();
        //open a connection to internet

        try {
            URL url=new URL(builtUri.toString());
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            //now read the response
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while((line=bufferedReader.readLine())!=null){
                sb.append(line+"\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String Jsonresponse=sb.toString();
        return Jsonresponse;
    }

    @Override
    protected void onPostExecute(String s) {

        try {

            JSONObject rootObject=new JSONObject(s);
            JSONObject itemArray=rootObject.getJSONObject("query");
            JSONObject itemobject=itemArray.getJSONObject("results");
            JSONObject volObj=itemobject.getJSONObject("channel");
            JSONObject channel=volObj.getJSONObject("item");
            JSONObject itemObject2=channel.getJSONObject("condition");
            JSONArray forecast=channel.getJSONArray("forecast");
            String date=itemObject2.getString("date");
            String tem=itemObject2.getString("temp");
            String text=itemObject2.getString("text");
            Integer t=Integer.parseInt(tem);
            t=(t-32)*5/9;

            for(int i=0;i<forecast.length();i++)
            {
                MyData currentData=new MyData();
                JSONObject curerntItem=forecast.getJSONObject(i);
                String dat=curerntItem.getString("date");
                String high=curerntItem.getString("high");
                Integer h=Integer.parseInt(high);
                h=(h-32)*5/9;
                String low=curerntItem.getString("low");
                Integer l=Integer.parseInt(low);
                l=(l-32)*5/9;
                currentData.setDate(dat);
                currentData.setHigh(high);
                currentData.setLow(low);
                myData.add(currentData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rv.setAdapter(new MyAdapter(ct,myData));
    }
}
