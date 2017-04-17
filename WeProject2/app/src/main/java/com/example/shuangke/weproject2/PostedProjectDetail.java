package com.example.shuangke.weproject2;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class PostedProjectDetail extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userplist;
    private String decriptionData;
    private String rewardData;
    private String requirementData;
    private String uid;
    private DatabaseReference drf;
    private TextView description;
    private TextView reward;
    private TextView requirement;
    private String pid;
    private String emailadd;
    private String ptitle;
    private String ps;
    private String members;
    private TextView mem;
    private String joinlist;
    private DatabaseReference ref;

    EditText input;
    TextView addMember;
    String addUserList;// 这个是post project 的人输入的想要加的的用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_project_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Posted Project Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new team member");
        builder.setIcon(R.drawable.addmember);
        builder.setMessage("Please enter the user's email address.");
        input = new EditText(this);
        builder.setView(input);

        //set postitive Button
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addUserList = input.getText().toString();
                drf = FirebaseDatabase.getInstance().getReference().child("projects").child(pid).child("members");
                drf.setValue(members + addUserList + ";");
                Toast.makeText(getApplicationContext(),"add User: " + addUserList,Toast.LENGTH_LONG).show();
                mem.setText(members + addUserList + ";");
                String newmmem = addUserList + ";";
                //ref.setValue(joinlist + pid + ";");



            }
        });

        //set Nagative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog ad = builder.create();

        //click to invoke the dialog

        addMember = (TextView)findViewById(R.id.addMember);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.show();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        decriptionData = "";
        requirementData = "";
        int position = getIntent().getIntExtra("position",0);
        final ArrayList<String> plist = getIntent().getStringArrayListExtra("plist");
        ptitle = getIntent().getStringExtra("ptitle");
        pid = plist.get(position);
        description = (TextView)findViewById(R.id.description);
        reward = (TextView) findViewById(R.id.reward);
        requirement = (TextView) findViewById(R.id.requirement);
        mem = (TextView) findViewById(R.id.team_members);
        uid = mAuth.getCurrentUser().getEmail().toString();
        uid = uid.replace("@","");
        uid = uid.replace(".","");


        new GetDataTask().execute("https://testfirebase-1fb45.firebaseio.com/projects/"+pid+".json");

//        wrong user
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("joinedproject");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                joinlist = dataSnapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });




    }



    class GetDataTask extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(PostedProjectDetail.this);
            progressDialog.setMessage("loading data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                return getData(params[0]);
            } catch (IOException ex) {
                return "network error!";
            } catch (JSONException ex) {
                return "Data Invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //mResult.setText(result);
            if(progressDialog != null) {
                progressDialog.dismiss();
            }
        }


        private String getData(String urlPath) throws IOException, JSONException{
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            String out;
            try{
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(1000);
                urlConnection.setConnectTimeout(1000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    result.append(line);

                }
                out = result.toString();

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            int dindex = out.indexOf("description");
            int dindex_end = out.indexOf("endDate");
            decriptionData = decriptionData + out.substring(dindex + 14,dindex_end-3);
            description.setText(decriptionData);
            int rindex = out.indexOf("award");
            int rindex_end = out.indexOf("beginDate");
            String sub = out.substring(rindex,rindex_end);
            int num = Integer.parseInt(sub.replaceAll("[\\D]", ""));
            reward.setText(" $" + Integer.toString(num));
            int reqindex = out.indexOf("requirement");
            int reqindex_end = out.indexOf("title");
            requirement.setText(out.substring(reqindex+14,reqindex_end-3));
            int indexo = out.indexOf("owner");
            int indexoend = out.indexOf("requirement");
            emailadd = out.substring(indexo + 7, indexoend - 3);
            System.out.println(emailadd+"------------------------");
            int mindex = out.indexOf("members");
            int mendindex = out.indexOf("owner");
            members = out.substring(mindex+10,mendindex - 3);
            mem.setText(members);
            //System.out.println(members + "++++++++++++++");

            return out;
        }
    }



}
