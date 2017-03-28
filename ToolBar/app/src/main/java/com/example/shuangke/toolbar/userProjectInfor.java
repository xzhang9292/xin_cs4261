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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class userProjectInfor extends AppCompatActivity {
    String[] PostProjectTitles ={"CS 4261 \"Fee Split\"","CS 1332 \"short path finding\""};
    String[] JoinedProjectTitles = {"Classic Music online courses","CS4475 ZOO Escape","Africa Environment protection","Graph Theory","Numerical analysis","Cherrystone","Green Jade","Positive Impact","Hobby Finding"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetDataTask().execute("http://128.61.1.235:3000/api/user/58dad0881fe6c91aeef03958");
        setContentView(R.layout.activity_user_project_infor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Projects");

        //



        //
        ListView PostedProjects =(ListView)findViewById(R.id.postProject);
        ListView JoinedProjects =(ListView)findViewById(R.id.joinedProject);

        //

        //
        userProjectInfor.CustomAdapter customAdapter =new userProjectInfor.CustomAdapter();
        userProjectInfor.CustomAdapter2 customAdapter2 =new userProjectInfor.CustomAdapter2();

        PostedProjects.setAdapter(customAdapter);
        JoinedProjects.setAdapter(customAdapter2);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    class CustomAdapter extends BaseAdapter {
        ArrayList itemlist = new ArrayList<String>();

        @Override
        public int getCount() {
            return PostProjectTitles.length;
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
            view = getLayoutInflater().inflate(R.layout.projectlist,null);
            TextView textView_postedProjectsTitles =(TextView)view.findViewById(R.id.postItem);
            textView_postedProjectsTitles.setText(PostProjectTitles[position]);
            return view;
        }
    }

    class CustomAdapter2 extends BaseAdapter {


        @Override
        public int getCount() {
            return JoinedProjectTitles.length;
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
            view = getLayoutInflater().inflate(R.layout.projectlist,null);
            TextView textView_joinedProjectsTitles =(TextView)view.findViewById(R.id.postItem);
            textView_joinedProjectsTitles.setText(JoinedProjectTitles[position]);
            return view;
        }
    }


    class GetDataTask extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(userProjectInfor.this);
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

        private int getendindex(int begin, String s) {
            int end = begin;
            int counter = 1;
            for(int i = begin; i < s.length(); i++){
                if(s.charAt(i) == '{'){
                    counter++;
                }
                if(s.charAt(i)=='}'){
                    counter--;
                    if(counter == 0) {
                        end = i+1;
                        return end;
                    }
                }

            }
            return end;
        }

        private String getData(String urlPath) throws IOException, JSONException{
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader = null;
            JSONObject a;
            try{
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    result.append(line);

                }
                String out = result.toString();
                a = new JSONObject(out);
                //JSONObject a = new JSONObject(jlist[0]);
                //System.out.println(a.get("firstname"));

            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            //System.out.print(result);
//            int bindex = out.indexOf('{');
//            int endindex= getendindex(bindex,out.substring(bindex,out.length()));
//            JSONObject a= new JSONObject(out.substring(bindex,endindex+1));
            //return result.toString();
            PostProjectTitles = a.getString("postProject").split(";");
            JoinedProjectTitles = a.getString("joinedProject").split(";");
            return a.getString("firstName");
        }
    }



    public void gotoHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void gotoPostProjectPage(View view){
        Intent intent = new Intent(this,PostProject.class);
        startActivity(intent);
    }

}
