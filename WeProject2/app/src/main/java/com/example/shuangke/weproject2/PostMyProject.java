package com.example.shuangke.weproject2;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostMyProject extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private FirebaseAuth mAuth;
    public  View view;
    private DatabaseReference dbf;
    private DatabaseReference userplist;
    private String num;
    private String uid;
    private String plist;
    private String catagorieslist;
    private String pTocat;  //proejct list to categories
    private String categories = "";



    public PostMyProject() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_my_project, container, false);
        mAuth = FirebaseAuth.getInstance();


        TextView from = (TextView) view.findViewById(R.id.fromDate);


        from.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DialogFragment picker = new PostMyProject();
                picker.show(getFragmentManager(), "datePicker");
            }
        });
        final TextView to = (TextView) view.findViewById(R.id.toDate);
        to.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DialogFragment picker = new PostMyProject();
                picker.show(getFragmentManager(), "datePicker");
            }
        });
        // Inflate the layout for this fragment

        //get value of total counts
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
        //dbf.setValue(Long.toString(Long.parseLong(num) + 1));
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


        Button post = (Button)view.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                String pname = "&"+uid + num;
                new PostDataTask().execute("https://testfirebase-1fb45.firebaseio.com/projects/"+pname+".json");
                dbf.setValue(Integer.toString(Integer.parseInt(num)+1));
                userplist.setValue(plist+pname+";");
                String[] catlist = categories.split(";");
                String cat = "";
                for(int i = 0; i < catlist.length;i++) {
                    cat = catlist[i];
                    System.out.println(cat + "--------------------------");

                    new PostDataTask().execute("https://testfirebase-1fb45.firebaseio.com/categories/" + cat + "/" + pname + ".json");

                }

                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
            }
        });
        return view;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());

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


            EditText projectTitleET = (EditText)view.findViewById(R.id.projectTitle);
            EditText projectDescriptionET = (EditText)view.findViewById(R.id.description);
            EditText requirement = (EditText)view.findViewById(R.id.teamSize);

            EditText rewardsET = (EditText)view.findViewById(R.id.reward);

            project_title = projectTitleET.getText().toString();//var store project title
            project_description =projectDescriptionET.getText().toString();//var store project description
            require = requirement.getText().toString();

            String rewardstr = rewardsET.getText().toString();
            if(rewardstr.equals("")){
                rewards = "0";
            }
            else{
                rewards = rewardsET.getText().toString();
            }



            CheckBox artsCK = (CheckBox)view.findViewById(R.id.arts);
            CheckBox businessCK = (CheckBox)view.findViewById(R.id.business);
            CheckBox biologyCK = (CheckBox)view.findViewById(R.id.biology);
            CheckBox csCK = (CheckBox)view.findViewById(R.id.cs);
            CheckBox chemistryCK = (CheckBox)view.findViewById(R.id.chemistry);
            CheckBox educationCK = (CheckBox)view.findViewById(R.id.education);
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
               // dataToSend.put("beginDate", fromDate);
                //dataToSend.put("endDate", toDate);
                dataToSend.put("catagories",categories);
                dataToSend.put(	"award", rewards);
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


            return result.toString();
        }

    }

}

