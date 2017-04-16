package com.example.shuangke.weproject2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class PostAProject extends AppCompatActivity {
    TextView from;
    TextView to;
    int year_y,month_y,day_y;
    int year_x,month_x,day_x;
    static final int DILOG_ID = 0;
    static final int DILOG_ID2 = 1;
    private FirebaseAuth mAuth;
    public  View view;
    private DatabaseReference dbf;
    private DatabaseReference userplist;
    private String num;
    private String uid;
    private String plist;

    private String categories = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_aproject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post My Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Calendar cal = Calendar.getInstance();
        year_x =cal.get(Calendar.YEAR);
        month_x =cal.get(Calendar.MONTH);
        day_x =cal.get(Calendar.DAY_OF_MONTH);

        year_y =cal.get(Calendar.YEAR);
        month_y =cal.get(Calendar.MONTH);
        day_y =cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();

        //add on
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getEmail().toString();
        uid = uid.replace("@","");
        uid = uid.replace(".","");
        dbf = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("numcounts");
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                num = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        userplist = FirebaseDatabase.getInstance().getReference().child("user").child(uid).child("projectlist");
        //get projectlist
        userplist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plist = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });








    }

    public void showDialogOnButtonClick(){
        from =(TextView) findViewById(R.id.fromDate);

        from.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        showDialog(DILOG_ID);
                    }
                }
        );

        to=(TextView) findViewById(R.id.toDate);
        to.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        showDialog(DILOG_ID2);
                    }
                }
        );
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == DILOG_ID){
            return new DatePickerDialog(this,dpickerListenerFrom,year_x,month_x,day_x);
        }
        if(id == DILOG_ID2){
            return new DatePickerDialog(this,dpickerListenerTo,year_y,month_y,day_y);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dpickerListenerFrom = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x = year;
            month_x = month + 1;
            day_x = dayOfMonth;
            String formattedDate = year_x +"/"+month_x + "/"+day_x;
            from.setText(formattedDate);
        }
    };

    private DatePickerDialog.OnDateSetListener dpickerListenerTo = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_y = year;
            month_y = month + 1;
            day_y = dayOfMonth;
            String formattedDate = year_y +"/"+month_y + "/"+day_y;
            to.setText(formattedDate);
        }
    };

    public void gotoHome(View view) {
        String pname = "&"+uid + num;
        System.out.println(pname + "=====================================");
        try {
            String clist = (new PostDataTask().execute("https://testfirebase-1fb45.firebaseio.com/projects/" + pname + ".json")).get();
            System.out.println(clist + "**************************");
            String[] catlist = clist.split(";");
            String cat = "";

            for(int i = 0; i < catlist.length;i++) {
                cat = catlist[i];
                System.out.println(cat + "--------------------------");

                new PostDataTask().execute("https://testfirebase-1fb45.firebaseio.com/categories/" + cat + "/" + pname + ".json");

            }

        } catch (InterruptedException e) {
            e.getMessage();
        } catch (ExecutionException e) {
            e.getMessage();
        }
        dbf.setValue(Integer.toString(Integer.parseInt(num)+1));
        userplist.setValue(plist+pname+";");




        Intent intent = new Intent(PostAProject.this, HomePage.class);
        startActivity(intent);
    }

    class PostDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try{
                return PostData(params[0]);
            } catch (IOException ex) {
                return "network error!";
            } catch (JSONException ex) {
                return "Data Invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (progressDialog != null) {
                progressDialog.dismiss();

            }

        }

        private String PostData(String urlPath)  throws IOException, JSONException {

            //still need to do userID,Date

            String project_title = "";
            String project_description = "";
            String require;
            String fromDate = "";
            String toDate = "";
            String rewards;
            fromDate = Integer.toString(month_x) + "/"+Integer.toString(day_x) + "/" +Integer.toString(year_x);
            toDate = Integer.toString(month_y) + "/"+Integer.toString(day_y) + "/" +Integer.toString(year_y);

            EditText projectTitleET = (EditText)findViewById(R.id.project_title);
            EditText projectDescriptionET = (EditText)findViewById(R.id.project_description);
            EditText requirement = (EditText)findViewById(R.id.requirement);

            //EditText rewardsET = (EditText)view.findViewById(R.id.reward);


            project_title = projectTitleET.getText().toString();//var store project title
            project_description =projectDescriptionET.getText().toString();//var store project description
            require = requirement.getText().toString();

            //String rewardstr = rewardsET.getText().toString();
            String rewardstr = "145";
            if(rewardstr.equals("")){
                rewards = "0";
            }
            else{
                rewards = rewardstr;
            }



            CheckBox artsCK = (CheckBox)findViewById(R.id.arts);
            CheckBox businessCK = (CheckBox)findViewById(R.id.business);
            CheckBox biologyCK = (CheckBox)findViewById(R.id.biology);
            CheckBox csCK = (CheckBox)findViewById(R.id.cs);
            CheckBox chemistryCK = (CheckBox)findViewById(R.id.chemistry);
            CheckBox educationCK = (CheckBox)findViewById(R.id.education);
            categories = "";
            if(artsCK.isChecked()){
                categories = categories+"arts"+";";
            }
            if(businessCK.isChecked()){
                categories = categories+"business"+";";
            }
            if(biologyCK.isChecked()){
                categories = categories+"biology"+";";

            }
            if(csCK.isChecked()){
                categories = categories+"cs"+";";

            }
            if(chemistryCK.isChecked()){
                categories = categories+"chemistry"+";";

            }
            if(educationCK.isChecked()){
                categories = categories+"education"+";";

            }

            System.out.println("project title is: " + project_title +"\n"+project_description + "\n" + require + "\n" + fromDate + "\n" + toDate + "\n" + categories + "\n");


            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;
            String owner = mAuth.getCurrentUser().getEmail().toString();
            try{
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("owner", owner);
                dataToSend.put("title", project_title);
                dataToSend.put("description", project_description);
                dataToSend.put("requirement", require);
                dataToSend.put("beginDate", fromDate);
                dataToSend.put("endDate", toDate);
                dataToSend.put("catagories",categories);
                dataToSend.put(	"award", rewards);
                //dataToSend.put("fromdate", fromDate);
                //dataToSend.put("todate", toDate);
                String melist = "";
                dataToSend.put("members",melist);

                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("PATCH");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
                if(bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }


            return categories;
        }

    }


}
