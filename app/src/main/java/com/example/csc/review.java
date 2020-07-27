package com.example.csc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class review extends AppCompatActivity {

    Spinner spcont,sppart;
    Button btreview;
    TextView tvans,partno;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ArrayList<String> contlist=new ArrayList<>();
    ArrayList<String> partlist=new ArrayList<>();

    ArrayAdapter<String> contadap,partadap;

    String selcont;
    LinearLayout vis;
    int no=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        spcont=findViewById(R.id.spcont);
        sppart=findViewById(R.id.sppart);
        btreview=findViewById(R.id.btreview);
        tvans=findViewById(R.id.tvans);
        partno=findViewById(R.id.partno);

        vis=findViewById(R.id.vis);

        myRef.child("contest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot d1:dataSnapshot.getChildren())
               {
                   String cont=d1.getKey();
                   Log.e("cont is",cont);
                   contlist.add(cont);
               }
               contadap=new ArrayAdapter<String>(review.this,android.R.layout.simple_spinner_dropdown_item,contlist);
               spcont.setAdapter(contadap);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("hai", "Failed to read value.", error.toException());
            }
        });


        btreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vis.setVisibility(View.VISIBLE);
                selcont=spcont.getSelectedItem().toString();
                getpart(selcont);

            }
        });

        sppart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selpart=sppart.getItemAtPosition(position).toString();
                selpart=selpart.replace(".","_");

                myRef.child("result").child(selpart).child(selcont).child("solution").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            String ans=dataSnapshot.getValue(String.class);
                            Log.e("ans is",ans);
                            tvans.setText(ans);

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.e("hai", "Failed to read value.", error.toException());
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void getpart(final String selcont)
    {
        no=0;
        partlist.clear();
        myRef.child("result").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d1:dataSnapshot.getChildren())
                {

//                    String findpart=d1.child("depsec").getValue(String.class);
//                    Log.e("find",findpart+"yes");
                    if(d1.child(selcont).child("solution").getValue(String.class)!=null)
                    {
                        String part=d1.getKey();
                        part=part.replace("_",".");
                        Log.e("part is",part);
                        partlist.add(part);
                        no++;
                    }
                }
                partadap=new ArrayAdapter<String>(review.this,android.R.layout.simple_spinner_dropdown_item,partlist);
                sppart.setAdapter(partadap);
                partno.setText("Number of Submissions: "+no);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("hai", "Failed to read value.", error.toException());
            }
        });

    }
}
