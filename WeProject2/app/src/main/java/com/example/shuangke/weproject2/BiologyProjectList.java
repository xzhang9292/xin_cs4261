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
import android.widget.ListView;
import android.widget.TextView;

public class BiologyProjectList extends AppCompatActivity {

    String[] TITLES ={"Biology Project1","Biology Project2","Arts Project3","Arts Project4","Arts Project5","Arts Project6","Arts Project7","Arts Project8","Arts Project9"};
    String[] REWARD ={"1000$","2000$","3000$","4000$","5000$","6000$","7000$","8000$","9000$"};
    String[] DATE ={"From 2016/4/6","From 2017/4/6","From 2018/4/6","From 2019/4/6","From 2020/4/6","From 2021/4/6","From 2022/4/6","From 2023/4/6","From 2024/4/6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biology_project_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Biology Project List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView =(ListView)findViewById(R.id.listView);
       CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(view.getContext(),BiologyProjectDetail.class);
                startActivityForResult(intent, position);

            }
        });
    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return TITLES.length;
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
            textView_title.setText(TITLES[position]);
            textView_reward.setText("Reward value:" + REWARD[position]);
            textView_date.setText("Starting date:" + DATE[position]);
            return view;
        }
    }


}
