package com.example.csc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
Button sub;
EditText name,mail;
TextView section;
Spinner dept,sec;
String[] depart=new String[]{"Select department","CSE","IT"};
String[] sect=new String[]{"Select section","A","B","C"};
String nam,spin,secspin="",smail;

SharedPreferences pref;
SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pref=getSharedPreferences("MySharedPref",MODE_PRIVATE);
        editor=pref.edit();


        sub=findViewById(R.id.btsub);
        name=findViewById(R.id.etname);
        mail=findViewById(R.id.etmail);

        section=findViewById(R.id.tvsec);
        dept=findViewById(R.id.spdept);
        sec=findViewById(R.id.spsec);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,depart);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(adapter);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sect);
        //adapter.set(android.R.layout.simple_spinner_dropdown_item);
        sec.setAdapter(adapter1);

        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String dep=dept.getItemAtPosition(position).toString();
               if(dep.equals("CSE"))
               {
                   section.setVisibility(View.VISIBLE);
                   sec.setVisibility(View.VISIBLE);
               }
               else
               {
                   section.setVisibility(View.GONE);
                   sec.setVisibility(View.GONE);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


    sub.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isempty()) {

                String chmail=smail.replace('.','_');



                myRef.child("result").child(chmail).child("mail").setValue(smail);
                myRef=myRef.child("result").child(chmail);
                myRef.child("name").setValue(nam);
                if(secspin.equals("Select section"))
                    secspin="";
                myRef.child("depsec").setValue(spin+" "+secspin);
                Toast.makeText(register.this,"Registration Success",Toast.LENGTH_SHORT).show();

                editor.putString("name",nam);
                editor.putString("mail",chmail);
                Log.e("regmail",chmail);
                editor.putString("depsec",spin+" "+secspin);
                editor.putBoolean("reg",true);
                editor.commit();

                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        }
    });

    }
    public boolean isempty()
    {
        nam=name.getText().toString();
        spin=dept.getSelectedItem().toString();
        secspin=sec.getSelectedItem().toString();
        smail=mail.getText().toString();

        if(nam.isEmpty())
        {
            name.setError("Name can't be empty");
            return false;
        }
        if(smail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(smail).matches())
        {
            mail.setError("Enter a valid mail-id");
            return false;
        }

        if(spin.equals("Select department"))
        {
            Toast.makeText(register.this,"Please select Department",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(spin.equals("CSE"))
        {
            if(secspin.equals("Select section"))
            {
                Toast.makeText(register.this,"Please select Section",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

}
