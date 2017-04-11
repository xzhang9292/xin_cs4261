package com.example.shuangke.weproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AllCategories extends AppCompatActivity {
    int[] IMAGES = {R.drawable.arts, R.drawable.business1,R.drawable.biology,R.drawable.cs1,R.drawable.chemistry,R.drawable.education,R.drawable.math1,R.drawable.music,R.drawable.sports1};
    String[] NMAES ={"Arts","Business","Biology","Computer Science","Chemistry","Education","Math","Music","Sports"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//set go back bar

        ListView listView =(ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(position == 0) {
                    Intent intent = new Intent(view.getContext(),ArtsProjectList.class);
                    startActivityForResult(intent, 0);
                }
                //go to business project list
                if(position == 1) {
                    Intent intent = new Intent(view.getContext(),BusinessProjectList.class);
                    startActivityForResult(intent, 1);
                }

                if(position == 2) {
                    Intent intent = new Intent(view.getContext(),BiologyProjectList.class);
                    startActivityForResult(intent, 2);
                }
                if(position == 3) {
                    Intent intent = new Intent(view.getContext(),ComputerScienceProjectList.class);
                    startActivityForResult(intent, 3);
                }
                if(position == 4) {
                    Intent intent = new Intent(view.getContext(),ChemistryProjectList.class);
                    startActivityForResult(intent, 4);
                }
                if(position == 5) {
                    Intent intent = new Intent(view.getContext(),EducationProjectList.class);
                    startActivityForResult(intent, 5);
                }
                if(position == 6) {
                    Intent intent = new Intent(view.getContext(),MathProjectList.class);
                    startActivityForResult(intent, 6);
                }
                if(position == 7) {
                    Intent intent = new Intent(view.getContext(),MusicProjectList.class);
                    startActivityForResult(intent, 7);
                }
                if(position == 8) {
                    Intent intent = new Intent(view.getContext(),SportsProjectList.class);
                    startActivityForResult(intent, 8);
                }
            }
        });

    }


    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return IMAGES.length;
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
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView =(ImageView)view.findViewById(R.id.imageView);
            TextView textView_name =(TextView)view.findViewById(R.id.textView_name);
            imageView.setImageResource(IMAGES[position]);
            textView_name.setText(NMAES[position]);
            return view;
        }
    }

}
