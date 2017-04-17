package com.example.shuangke.weproject2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item){
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(HomePage.this, HomePage.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                      break;


                    case R.id.nav_postProject:
                        intent = new Intent(HomePage.this, PostAProject.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                        break;


                    case R.id.nav_appliedProjects:
                        intent = new Intent(HomePage.this, AppliedProject.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    case R.id.nav_joinedProject:
                        intent = new Intent(HomePage.this, JoinedProject.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    case R.id.nav_postedProject:
                        intent = new Intent(HomePage.this, PostedProject.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                        break;
                    case R.id.nav_completeProject:
                        intent = new Intent(HomePage.this,CompleteProject.class);
                        drawerLayout.closeDrawers();
                        startActivity(intent);
                        break;

                    case R.id.nav_profile:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new My_Account());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Log out");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                }

                return true;
            }
        });
    }

    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    public void gotobusiness(View view){
        Intent intent = new Intent(HomePage.this, BusinessProjectList.class);
        startActivity(intent);
    }

    public void gotocs(View view){
        Intent intent = new Intent(HomePage.this, ComputerScienceProjectList.class);
        startActivity(intent);
    }

    public void gotoarts(View view){
        Intent intent = new Intent(HomePage.this, ArtsProjectList.class);
        startActivity(intent);
    }

    public void gotomath(View view){
        Intent intent = new Intent(HomePage.this, MathProjectList.class);
        startActivity(intent);
    }
    public void gotosports(View view){
        Intent intent = new Intent(HomePage.this,SportsProjectList.class);
        startActivity(intent);
    }

    public void gotomore(View view){
        Intent intent = new Intent(HomePage.this, AllCategories.class);
        startActivity(intent);
    }
}
