package com.example.csc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class contest extends AppCompatActivity {

    EditText etsoln;
    Button submit;
    TextView contestname;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String strsoln;
    String mail;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    DatabaseReference newRef = database.getReference();

    float cint;
    String ids,cname;
    float count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest);

        contestname=findViewById(R.id.contestname);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=pref.edit();

        mail=pref.getString("mail","");
        etsoln=findViewById(R.id.etsoln);
        submit=findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nonempty())
                {
                    cname=contestname.getText().toString();
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss", Locale.getDefault());
                    Date date = new Date();
                    String strDate = dateFormat.format(date).toString();


                    Log.e("cname",cname);

                    //myRef=myRef.child("contest").child(cname);


                    // addcont();

                    myRef = database.getReference();
                    myRef.child("contest").child(cname).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            float count1=dataSnapshot.child("count").getValue(Float.class);
                            Log.e("c",count1+"no");
                            ids=dataSnapshot.child("mailid").getValue(String.class);
                            Log.e("caught",ids);
                            cint=count1;
                            editor.putFloat("cou",count1);
                            editor.putString("ids",ids);
                            editor.apply();

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            //Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });


                    cint=pref.getFloat("cou",0);
                    ids=pref.getString("ids","");
                    Log.e("hai",cint+"op");
                    cint=cint+1;
                    myRef = database.getReference();
                    myRef.child("contest").child(cname).child("count").setValue(cint);

                    mail=mail.replace("_",".");
                    ids=ids+","+mail;
                    myRef.child("contest").child(cname).child("mailid").setValue(ids);

//                    Log.e("count", cint+"yes");
//                    Log.e("mail", ids);



                    mail=mail.replace(".","_");
                    myRef=database.getReference();
                    myRef=myRef.child("result").child(mail).child(cname);
                    myRef.child("date").setValue(strDate);
                    myRef.child("marks").setValue(0);
                    myRef.child("solution").setValue(strsoln);

                    Toast.makeText(contest.this,"Thanks for your participation",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(contest.this,MainActivity.class);
                    startActivity(intent);
                    //Log.e("mail",mail);

                }
            }
        });



    }

    public Boolean nonempty()
    {
        strsoln=etsoln.getText().toString();
        if(strsoln.isEmpty())
        {
            etsoln.setError("This field can't be empty");
            return false;
        }
        return true;
    }

//    public void addcont()
//    {
//
//
//
//
//
//        //myRef=myRef.child("contest").child(cname);
//
////        myRef.child("contest").child(cname).child("mailid").setValue(ids);
//    }
}
