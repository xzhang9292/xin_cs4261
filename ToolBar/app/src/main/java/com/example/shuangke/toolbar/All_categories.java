package com.example.shuangke.toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class All_categories extends AppCompatActivity {

    int[] IMAGES = {R.drawable.medical, R.drawable.music,R.drawable.sports,R.drawable.photography,R.drawable.chemistry,R.drawable.math,R.drawable.cs,R.drawable.bussiness,R.drawable.education};
    String[] NMAES ={"Health","Music","Sports","Photography","Chemistry","Math","Computer Science","Business","Education"};
    String []Descriptions = {"1","2","3","4","5","6","7","8","9"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        Toolbar my_toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(my_toolBar);
        getSupportActionBar().setTitle("All Categories");

        ListView listView =(ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter =new CustomAdapter();
        listView.setAdapter(customAdapter);

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
            TextView textView_description =(TextView)view.findViewById(R.id.textView_description);
            imageView.setImageResource(IMAGES[position]);
            textView_name.setText(NMAES[position]);
            textView_description.setText(Descriptions[position]);
            return view;
        }
    }
    public void gotoHomePage(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void gotoSigninPage(View view){
        Intent intent =new Intent(this,signIn.class);
        startActivity(intent);
    }
}
