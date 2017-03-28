package com.example.shuangke.toolbar;

import android.content.Intent;
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

import java.util.List;

public class userProjectInfor extends AppCompatActivity {
    String[] PostProjectTitles ={"CS 4261 \"Fee Split\"","CS 1332 \"short path finding\""};
    String[] JoinedProjectTitles = {"Classic Music online courses","CS4475 ZOO Escape","Africa Environment protection","Graph Theory","Numerical analysis","Cherrystone","Green Jade","Positive Impact","Hobby Finding"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_project_infor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Projects");
        ListView PostedProjects =(ListView)findViewById(R.id.postProject);
        ListView JoinedProjects =(ListView)findViewById(R.id.joinedProject);
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
    public void gotoHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void gotoPostProjectPage(View view){
        Intent intent = new Intent(this,PostProject.class);
        startActivity(intent);
    }

}
