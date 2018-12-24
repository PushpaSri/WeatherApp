package com.example.doddas.weather_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edit;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit=(EditText)findViewById(R.id.edit_text);
        rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        //rv.setAdapter(new MyAdapter(this));

    }

    public void get(View view) {
        ArrayList<MyData> data=new ArrayList<>();
        ConnectivityManager connMgr=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            String placesearched=edit.getText().toString();
            Yahoo fetchw=new Yahoo(data,this,rv);
            fetchw.execute(placesearched);
        }
        else
        {
            Toast.makeText(this,"no internet",Toast.LENGTH_SHORT);
        }
    }
}

