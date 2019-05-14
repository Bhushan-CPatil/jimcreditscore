package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.DetHistOfDocRes;
import com.eijs.creditscore.pojo.DoEntDocListItem;
import com.eijs.creditscore.pojo.EntDocListRes;
import com.eijs.creditscore.pojo.FullHistoryItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedHistory extends AppCompatActivity {

    ViewDialog progressDialoge;
    ConstraintLayout sv ;
    RecyclerView histlist;
    ImageView norecord;
    public List<FullHistoryItem> hislst = new ArrayList<>();
    String selecode,seldrname,selcntcd,selnetid,selposition;
    TextView ttlrec,drname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seldrname = getIntent().getStringExtra("drname");
        selecode = getIntent().getStringExtra("selecode");
        selcntcd = getIntent().getStringExtra("selcntcd");
        selnetid = getIntent().getStringExtra("selnetid");
        selposition = getIntent().getStringExtra("position");
        progressDialoge=new ViewDialog(DetailedHistory.this);
        sv = findViewById(R.id.sv);
        histlist = findViewById(R.id.hislst);
        norecord = findViewById(R.id.norecord);
        ttlrec = findViewById(R.id.ttlrec);
        drname = findViewById(R.id.docname);
        drname.setText("DOCTOR NAME : "+seldrname.toUpperCase());
        setHisLstAdapter();
        callApi();
    }

    public void setHisLstAdapter(){
        histlist.setNestedScrollingEnabled(true);
        histlist.setLayoutManager(new LinearLayoutManager(DetailedHistory.this));
        histlist.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(DetailedHistory.this).inflate(R.layout.detail_history_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final FullHistoryItem model = hislst.get(i);
                myHolder.edate.setText("DATE - "+model.getEntdate());
                myHolder.ds.setText(model.getDailyscore());
                myHolder.ms.setText(model.getMthscore());
            }

            @Override
            public int getItemCount() {
                return hislst.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView edate,ds,ms;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    edate = itemView.findViewById(R.id.date);
                    ds = itemView.findViewById(R.id.dailyscore);
                    ms = itemView.findViewById(R.id.monthlyscore);
                }
            } }
        );
    }

    private void callApi() {
        progressDialoge.show();

        retrofit2.Call<DetHistOfDocRes> call1 = RetrofitClient
                .getInstance().getApi().getFullHistOfDoc(selecode, selnetid, selcntcd, Global.date,Global.yr,Global.mth);
        call1.enqueue(new Callback<DetHistOfDocRes>() {
            @Override
            public void onResponse(retrofit2.Call<DetHistOfDocRes> call1, Response<DetHistOfDocRes> response) {
                DetHistOfDocRes res = response.body();
                progressDialoge.dismiss();
                hislst = res.getFullHistory();
                ttlrec.setText("SHOWING LATEST "+hislst.size()+" RECORDS");
                if(hislst.size()>0) {
                    norecord.setVisibility(View.GONE);
                    histlist.setVisibility(View.VISIBLE);
                    histlist.getAdapter().notifyDataSetChanged();
                }else{
                    histlist.setVisibility(View.GONE);
                    norecord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DetHistOfDocRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to fetch data !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Re-try", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callApi();
                            }
                        });
                snackbar.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        } return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        DetailedHistory.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
