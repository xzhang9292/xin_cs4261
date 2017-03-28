package com.example.shuangke.toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

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
import java.util.ArrayList;

public class PostProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Post a new project");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }
    public void gotoHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void gotoUserProjectInfor(View view){

//        String project_title = "";
//        String project_description = "";
//        int teamSize = 0;
//        String fromDate = "";
//        String toDate = "";
//        ArrayList categories = new ArrayList();
//        double rewards = 0;
//        ArrayList contactInfo = new ArrayList();
//
//        EditText projectTitleET = (EditText)findViewById(R.id.project_title);
//        EditText projectDescriptionET = (EditText)findViewById(R.id.project_description);
//        EditText teamSizeET = (EditText)findViewById(R.id.team_size);
//        EditText fromDateET = (EditText)findViewById(R.id.editText5);
//        EditText toDateET = (EditText)findViewById(R.id.editText6);
//        EditText rewardsET = (EditText)findViewById(R.id.project_reward);
//        EditText emailET = (EditText)findViewById(R.id.email_address);
//        EditText phoneET = (EditText)findViewById(R.id.contact_phoneNumber);
//        EditText addressET = (EditText)findViewById(R.id.contact_adress);
//
//        project_title = projectTitleET.getText().toString();//var store project title
//        project_description =projectDescriptionET.getText().toString();//var store project description
//        String teamSizeStr = teamSizeET.getText().toString();
//        if(teamSizeStr.equals("")){
//            teamSize = 0;
//        }
//        else{
//            teamSize = Integer.parseInt(teamSizeET.getText().toString());
//        }
//
//
//        fromDate = fromDateET.getText().toString();
//        toDate = toDateET.getText().toString();
//        String rewardstr = rewardsET.getText().toString();
//        if(rewardstr.equals("")){
//            rewards = 0;
//        }
//        else{
//            rewards = Double.parseDouble(rewardsET.getText().toString());
//        }
//
//
//        String email = emailET.getText().toString();
//        String phone = phoneET.getText().toString();
//        String address = addressET.getText().toString();
//
//        if(email.length()!= 0 && email != null){
//            contactInfo.add(email);
//        }
//        if(phone.length()!= 0 && phone != null){
//            contactInfo.add(phone);
//        }
//        if(address.length()!= 0 && address != null){
//            contactInfo.add(address);
//        }
//
//        CheckBox healthCK = (CheckBox)findViewById(R.id.health_checkBox);
//        CheckBox musicCK = (CheckBox)findViewById(R.id.MusicCk1);
//        CheckBox csCK = (CheckBox)findViewById(R.id.csCK1);
//        CheckBox sportsCK = (CheckBox)findViewById(R.id.sportsCK1);
//        CheckBox businessCK = (CheckBox)findViewById(R.id.businessCK1);
//        CheckBox artCK = (CheckBox)findViewById(R.id.ArtsCK1);
//
//        if(healthCK.isChecked()){
//            categories.add("health");
//        }
//        if(musicCK.isChecked()){
//            categories.add("music");
//        }
//        if(csCK.isChecked()){
//            categories.add("CS");
//        }
//        if(sportsCK.isChecked()){
//            categories.add("sports");
//        }
//        if(businessCK.isChecked()){
//            categories.add("business");
//        }
//        if(artCK.isChecked()){
//            categories.add("arts");
//        }
//
//
//        System.out.println("project title is: " + project_title +"\n"+project_description + "\n" + teamSize + "\n" + fromDate + "\n" + toDate + "\n" + categories + "\n" + contactInfo);
        new PostDataTask().execute("http://192.168.1.4:3000/api/project");
        Intent intent = new Intent(this,userProjectInfor.class);
        startActivity(intent);
    }

    class PostDataTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PostProject.this);
            progressDialog.setMessage("loading data...");
            progressDialog.show();
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
            //still need to do userID

            String project_title = "";
            String project_description = "";
            int teamSize = 0;
            String fromDate = "";
            String toDate = "";
            ArrayList categories = new ArrayList();
            double rewards = 0;
            ArrayList contactInfo = new ArrayList();

            EditText projectTitleET = (EditText)findViewById(R.id.project_title);
            EditText projectDescriptionET = (EditText)findViewById(R.id.project_description);
            EditText teamSizeET = (EditText)findViewById(R.id.team_size);
            EditText fromDateET = (EditText)findViewById(R.id.editText5);
            EditText toDateET = (EditText)findViewById(R.id.editText6);
            EditText rewardsET = (EditText)findViewById(R.id.project_reward);
            EditText emailET = (EditText)findViewById(R.id.email_address);
            EditText phoneET = (EditText)findViewById(R.id.contact_phoneNumber);
            EditText addressET = (EditText)findViewById(R.id.contact_adress);

            project_title = projectTitleET.getText().toString();//var store project title
            project_description =projectDescriptionET.getText().toString();//var store project description
            String teamSizeStr = teamSizeET.getText().toString();
            if(teamSizeStr.equals("")){
                teamSize = 0;
            }
            else{
                teamSize = Integer.parseInt(teamSizeET.getText().toString());
            }


            fromDate = fromDateET.getText().toString();
            toDate = toDateET.getText().toString();
            String rewardstr = rewardsET.getText().toString();
            if(rewardstr.equals("")){
                rewards = 0;
            }
            else{
                rewards = Double.parseDouble(rewardsET.getText().toString());
            }


            String email = emailET.getText().toString();
            String phone = phoneET.getText().toString();
            String address = addressET.getText().toString();

            if(email.length()!= 0 && email != null){
                contactInfo.add(email);
            }
            if(phone.length()!= 0 && phone != null){
                contactInfo.add(phone);
            }
            if(address.length()!= 0 && address != null){
                contactInfo.add(address);
            }

            CheckBox healthCK = (CheckBox)findViewById(R.id.health_checkBox);
            CheckBox musicCK = (CheckBox)findViewById(R.id.MusicCk1);
            CheckBox csCK = (CheckBox)findViewById(R.id.csCK1);
            CheckBox sportsCK = (CheckBox)findViewById(R.id.sportsCK1);
            CheckBox businessCK = (CheckBox)findViewById(R.id.businessCK1);
            CheckBox artCK = (CheckBox)findViewById(R.id.ArtsCK1);

            if(healthCK.isChecked()){
                categories.add("health");
            }
            if(musicCK.isChecked()){
                categories.add("music");
            }
            if(csCK.isChecked()){
                categories.add("CS");
            }
            if(sportsCK.isChecked()){
                categories.add("sports");
            }
            if(businessCK.isChecked()){
                categories.add("business");
            }
            if(artCK.isChecked()){
                categories.add("arts");
            }


            System.out.println("project title is: " + project_title +"\n"+project_description + "\n" + teamSize + "\n" + fromDate + "\n" + toDate + "\n" + categories + "\n" + contactInfo);






            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;
            try{
                JSONObject dataToSend = new JSONObject();
                dataToSend.put("owner", "studentA");
                dataToSend.put("title", project_title);
                dataToSend.put("description", project_description);
                dataToSend.put("size", teamSize);
                dataToSend.put("beginDate", fromDate);
                dataToSend.put("endDate", toDate);
                dataToSend.put("catagories",categories);
                dataToSend.put(	"award", rewards);
                dataToSend.put(	"contactMethod",contactInfo );
                ArrayList<String> melist = new ArrayList<>();
                dataToSend.put("members",melist.toString());
//                dataToSend.put("email", "studentA@gmail.com");
//                dataToSend.put("phone", "4444444444");
//                ArrayList<String> a = new ArrayList<>();
//                ArrayList<String> b = new ArrayList<>();
//                dataToSend.put("joinedProject", "aaaaa");
//                dataToSend.put("state", "ga");
//                dataToSend.put("state", "ga");
//                dataToSend.put("state", "ga");
//                dataToSend.put("state", "ga");
//                dataToSend.put("postProject", "bbbbb");
//                b.add("cccc");
//                b.add("ddddd");
//                dataToSend.put("postProject", b);


                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("POST");
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
