package com.example.csc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   CardView con,web,login,devo;

   SharedPreferences pref;
   SharedPreferences.Editor editor;

   Boolean regstat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con=findViewById(R.id.cvcon);
        web=findViewById(R.id.cvweb);
        devo=findViewById(R.id.cvdev);
        login=findViewById(R.id.cvreg);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);

        regstat=pref.getBoolean("reg",false);
    }
    public void registergo(View v)
    {
        if(regstat) {
            Toast.makeText(MainActivity.this,"You have already registered",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent1 = new Intent(MainActivity.this, register.class);
            startActivity(intent1);
        }
    }
    public void contestgo(View v)
    {
        if(regstat) {
            Intent intent2 = new Intent(MainActivity.this, contest.class);
            startActivity(intent2);
        }
        else
        {
            Toast.makeText(MainActivity.this,"Register to participate",Toast.LENGTH_SHORT).show();
        }
    }
    public void webgo(View v)
    {
        Intent intent3=new Intent(MainActivity.this,website.class);
        startActivity(intent3);
    }
    public void devgo(View v)
    {
        Intent intent4=new Intent(MainActivity.this,developer.class);
        startActivity(intent4);
    }

    public void revgo(View v)
    {
        Intent intent4=new Intent(MainActivity.this,review.class);
        startActivity(intent4);
    }


}
