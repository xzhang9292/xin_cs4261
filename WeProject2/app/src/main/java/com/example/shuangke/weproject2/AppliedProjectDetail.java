package com.example.shuangke.weproject2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AppliedProjectDetail extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_project_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Applied Projects Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        decriptionData = "";
        requirementData = "";
        int position = getIntent().getIntExtra("position",0);
        final ArrayList<String> plist = getIntent().getStringArrayListExtra("plist");
        ptitle = getIntent().getStringExtra("ptitle");
        pid = plist.get(position);
        description = (TextView)findViewById(R.id.description);
        reward = (TextView) findViewById(R.id.reward);
        requirement = (TextView) findViewById(R.id.requirement);
        new GetDataTask().execute("https://testfirebase-1fb45.firebaseio.com/projects/"+pid+".json");

    }

    class GetDataTask extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(AppliedProjectDetail.this);
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

            //System.out.println(members + "++++++++++++++");

            return out;
        }
    }


}
