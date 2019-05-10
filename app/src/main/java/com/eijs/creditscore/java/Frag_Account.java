package com.eijs.creditscore.java;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.others.Global;


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
        }

        name.setText(Global.ename.toUpperCase());
        hqname.setText(Global.hname.toUpperCase());
        ecode.setText(Global.ecode.toUpperCase());
        netid.setText(Global.netid.toUpperCase());
        wdate.setText(Global.date.toUpperCase());
        etype.setText(etypewrd);

        return v;
    }

}
