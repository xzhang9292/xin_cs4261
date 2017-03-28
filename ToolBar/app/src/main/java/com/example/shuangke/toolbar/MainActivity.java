package com.example.shuangke.toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.app.SearchManager;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar my_toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(my_toolBar);
        getSupportActionBar().setTitle("Searching Project");
        getSupportActionBar().setIcon(getDrawable(R.drawable.ic_action_name));

        SearchView searchview = (SearchView) findViewById(R.id.searchview);
        searchview.setQueryHint("Type keywords here to find project");
        searchview.setBackgroundColor(Color.LTGRAY);

    }

    public void gotoAllCategoriesActivity(View view){
      Intent intent = new Intent(this,All_categories.class);
      startActivity(intent);
    }
    public void gotoSigninPage(View view){
        Intent intent =new Intent(this,signIn.class);
        startActivity(intent);
    }
}
