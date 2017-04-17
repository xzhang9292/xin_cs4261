package com.example.shuangke.weproject2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PostedProject extends AppCompatActivity {

    private String postedlist;
    private FirebaseAuth mAuth;
    private DatabaseReference userplist;
    private String uid;
    private ArrayList<String> titles;
    private ArrayList<String> rewards;
    private ArrayList<String> dates;
    private String outlist;
    private String[] listp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_project);

        titles = new ArrayList<>();
        rewards = new ArrayList<>();
        dates = new ArrayList<>();
        postedlist = "";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Posted Projects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getEmail().toString();
        uid = uid.replace("@","");
        uid = uid.replace(".","");

        try {
            outlist = new GetDataTask2().execute("https://testfirebase-1fb45.firebaseio.com/user/" + uid+"/projectlist.json").get();
            outlist = outlist.replace("\"","");
            listp = outlist.split(";");
            for (String project : listp) {
                if (project != "") {
                    new GetDataTask().execute("https://testfirebase-1fb45.firebaseio.com/projects/" + project + ".json");
                }
            }
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        ListView listView =(ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                ArrayList<String> alist = new ArrayList<String>();
                for(String s:listp) {
                    alist.add(s);
                }

                Intent intent = new Intent(view.getContext(),PostedProjectDetail.class);
                intent.putExtra("position",position);
                intent.putExtra("ptitle",titles.get(position));
                intent.putStringArrayListExtra("plist",alist);
                startActivityForResult(intent, position);
            }
        });


    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customlayoutprojectlist,null);
            TextView textView_title =(TextView)view.findViewById(R.id.project_title);
            TextView textView_reward =(TextView)view.findViewById(R.id.TextView_);
            TextView textView_date =(TextView)view.findViewById(R.id.date);
            textView_title.setText("Title:  "+titles.get(position));
            textView_reward.setText("Reward value:" + rewards.get(position));
            textView_date.setText("Period:" + dates.get(position));
            return view;
        }
    }


    class GetDataTask extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(PostedProject.this);
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
            int dindex = out.indexOf("title");
            int dindex_end = out.indexOf("}");
            if(dindex + 8 < dindex_end-1) {
                System.out.println(out.substring(dindex + 8, dindex_end - 1));
                titles.add(out.substring(dindex + 8, dindex_end - 1));
                int rindex = out.indexOf("award");
                int rindex_end = out.indexOf("beginDate");
                String sub = out.substring(rindex, rindex_end);
                int num = Integer.parseInt(sub.replaceAll("[\\D]", ""));
                rewards.add(" $" + Integer.toString(num));
                int indexrew = out.indexOf("beginDate");
                int end = out.indexOf("catagories");
                String fdate = out.substring(indexrew + 12, end - 3);
                int indexend = out.indexOf("endDate");
                int enddate = out.indexOf("members");
                String ldate = out.substring(indexend + 10, enddate - 3);
                dates.add(fdate + "   to   " + ldate);
            }
            return out;
        }
    }

    class GetDataTask2 extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(PostedProject.this);
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

            return out;
        }
    }

}
