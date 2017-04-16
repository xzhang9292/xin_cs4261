package com.example.shuangke.weproject2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton bt = (ImageButton)view.findViewById(R.id.more);
        bt.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), AllCategories.class);
               startActivity(intent);
           }
        });


        ImageButton bt_business = (ImageButton)view.findViewById(R.id.business);
        bt_business.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BusinessProjectList.class);
                startActivity(intent);
            }
        });

        ImageButton bt_cs = (ImageButton)view.findViewById(R.id.cs);
        bt_cs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ComputerScienceProjectList.class);
                startActivity(intent);
            }
        });

        ImageButton bt_arts = (ImageButton)view.findViewById(R.id.arts);
        bt_arts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ArtsProjectList.class);
                startActivity(intent);
            }
        });

        ImageButton bt_math = (ImageButton)view.findViewById(R.id.math);
        bt_math.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MathProjectList.class);
                startActivity(intent);
            }
        });

        ImageButton bt_sports = (ImageButton)view.findViewById(R.id.sports);
        bt_sports.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SportsProjectList.class);
                startActivity(intent);
            }
        });


        return view;
    }




}
