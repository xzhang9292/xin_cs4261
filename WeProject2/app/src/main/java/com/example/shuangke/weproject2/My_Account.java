package com.example.shuangke.weproject2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class My_Account extends Fragment {


    public My_Account() {
        // Required empty public constructor
    }
    private FirebaseAuth fauth;
    private TextView emailadd;
    private TextView name;
    private String outname;
    private DatabaseReference ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fauth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_my__account, container, false);
        Button logOut = (Button)view.findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                fauth.signOut();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
        emailadd = (TextView) view.findViewById(R.id.email);
        name = (TextView) view.findViewById(R.id.username);
        String email = fauth.getCurrentUser().getEmail().toString();
        emailadd.setText(email);
        String uid = email.replace("@","");
        uid = uid.replace(".","");
        ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("first");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.getValue(String.class);
                name.setText("Hello! " + firstname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // new GetDataTask().execute("https://testfirebase-1fb45.firebaseio.com/user/" + uid + ".json" );
//        name.setText(outname);
        return view;
    }


}
