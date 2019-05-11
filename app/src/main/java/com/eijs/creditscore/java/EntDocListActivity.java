package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.DoEntDocListItem;
import com.eijs.creditscore.pojo.EntDocListRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntDocListActivity extends AppCompatActivity {

    ViewDialog progressDialoge;
    ConstraintLayout sv ;
    RecyclerView doctorslist;
    ImageView norecord;
    public List<DoEntDocListItem> drlst = new ArrayList<>();
    String eanme,selecode,selnetid,selposition;
    TextView ttldoc,empname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ent_doc_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eanme = getIntent().getStringExtra("ename");
        selecode = getIntent().getStringExtra("selecode");
        selnetid = getIntent().getStringExtra("selnetid");
        selposition = getIntent().getStringExtra("position");
        progressDialoge=new ViewDialog(EntDocListActivity.this);
        sv = findViewById(R.id.sv);
        doctorslist = findViewById(R.id.doclist);
        norecord = findViewById(R.id.norecord);
        ttldoc = findViewById(R.id.ttldoc);
        empname = findViewById(R.id.empname);
        empname.setText("PSR NAME : "+eanme.toUpperCase());
        setDocLstAdapter();
        callApi();
    }

    public void setDocLstAdapter(){
        doctorslist.setNestedScrollingEnabled(true);
        doctorslist.setLayoutManager(new LinearLayoutManager(EntDocListActivity.this));
        doctorslist.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(EntDocListActivity.this).inflate(R.layout.ent_doc_list_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final DoEntDocListItem model = drlst.get(i);
                myHolder.drname.setText(model.getDrcd()+" - "+model.getDrname().toUpperCase());
                if(model.getStatus().equalsIgnoreCase("A")) {
                    myHolder.linlayout.setBackgroundResource(R.drawable.auborder);
                }else{
                    myHolder.linlayout.setBackgroundResource(0);
                }
                myHolder.itemView.setTag(i);
                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSubmit(EntDocListActivity.this);
                    }
                });
                myHolder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSubmit(EntDocListActivity.this);
                    }
                });

                myHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo delete popup
                    }
                });
            }

            @Override
            public int getItemCount() {
                return drlst.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView drname;
                ImageButton edit,delete;
                LinearLayout linlayout;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    drname = itemView.findViewById(R.id.drname);
                    edit = itemView.findViewById(R.id.doentry);
                    delete = itemView.findViewById(R.id.delete);
                    linlayout = itemView.findViewById(R.id.linlayout);
                }
            } }
        );
    }

    private void callApi() {
        progressDialoge.show();

        retrofit2.Call<EntDocListRes> call1 = RetrofitClient
                .getInstance().getApi().doEntryDocList(selnetid,Global.date);
        call1.enqueue(new Callback<EntDocListRes>() {
            @Override
            public void onResponse(retrofit2.Call<EntDocListRes> call1, Response<EntDocListRes> response) {
                EntDocListRes res = response.body();
                progressDialoge.dismiss();
                drlst = res.getDoEntDocList();
                ttldoc.setText("TOTAL DOCTORS : "+drlst.size());
                if(drlst.size()>0) {
                    norecord.setVisibility(View.GONE);
                    doctorslist.setVisibility(View.VISIBLE);
                    doctorslist.getAdapter().notifyDataSetChanged();
                }else{
                    doctorslist.setVisibility(View.GONE);
                    norecord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<EntDocListRes> call1, Throwable t) {
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

    public static void dialogSubmit(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.score_entry_popup);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        final EditText ds = dialog.findViewById(R.id.score);
        final EditText ms = dialog.findViewById(R.id.mthscore);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f1 = true,f2= true;
                if(ds.getText().toString().isEmpty()){
                    ds.setError("Enter a daily score");
                    ds.requestFocus();
                    f1 = false;
                }
                if(ms.getText().toString().isEmpty()){
                    ms.setError("Enter a monthly score");
                    ms.requestFocus();
                    f2 = false;
                }
                if(f1 && f2) {
                    //todo call submit api
                    dialog.dismiss();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
        EntDocListActivity.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
