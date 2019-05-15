package com.eijs.creditscore.java;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.others.Global;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Frag_Account extends Fragment {

    TextView name,hqname,ecode,netid,etype,wdate;
    String etypewrd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag__account, container, false);

        name = v.findViewById(R.id.name);
        hqname = v.findViewById(R.id.hqname);
        ecode = v.findViewById(R.id.ecode);
        netid = v.findViewById(R.id.netid);
        etype = v.findViewById(R.id.etype);
        wdate = v.findViewById(R.id.wdate);

        if(Global.emplevel.equalsIgnoreCase("1")){
            etypewrd = "PSR";
        }else if(Global.emplevel.equalsIgnoreCase("2")){
            etypewrd = "FM";
        }else if(Global.emplevel.equalsIgnoreCase("3")){
            etypewrd = "RM";
        }else if(Global.emplevel.equalsIgnoreCase("4")){
            etypewrd = "ZSM";
        }else if(Global.emplevel.equalsIgnoreCase("5")){
            etypewrd = "SM";
        }else if(Global.emplevel.equalsIgnoreCase("7")){
            etypewrd = "ADMIN";
        }

        name.setText(Global.ename.toUpperCase());
        hqname.setText(Global.hname.toUpperCase());
        ecode.setText(Global.ecode.toUpperCase());
        netid.setText(Global.netid.toUpperCase());
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        String inputDateStr=Global.date;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        wdate.setText(outputDateStr);
        etype.setText(etypewrd);

        return v;
    }

}
