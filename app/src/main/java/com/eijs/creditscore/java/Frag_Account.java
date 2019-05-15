package com.eijs.creditscore.java;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.IsUpdateReqRes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_Account extends Fragment {

    TextView name,hqname,ecode,netid,etype,wdate;
    String etypewrd;
    ViewDialog progress;
    ImageView hidlogo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_frag__account, container, false);
        progress = new ViewDialog(getActivity());
        hidlogo = v.findViewById(R.id.hidlogo);
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

        hidlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Global.emplevel.equalsIgnoreCase("7")){
                    getReportLink();
                }
            }
        });

        return v;
    }

    private void getReportLink() {

        progress.show();
        Call<IsUpdateReqRes> call = RetrofitClient
                .getInstance().getApi().ReportLink();
        call.enqueue(new Callback<IsUpdateReqRes>() {

            @Override
            public void onResponse(Call<IsUpdateReqRes> call, Response<IsUpdateReqRes> response) {
                IsUpdateReqRes isUpReqRes = response.body();
                progress.dismiss();
                String link = isUpReqRes.getLink();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                startActivity(intent, bndlanimation);
            }

            @Override
            public void onFailure(Call<IsUpdateReqRes> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getActivity(), "Due to slow internet app unable to get report link !", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
