package com.eijs.creditscore.java;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.others.ViewDialog;


public class Frag_history extends Fragment {

    View view;
    RecyclerView rv_hist;
    ViewDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_history, container, false);
        progressDialog=new ViewDialog(getActivity());
        rv_hist = view.findViewById(R.id.rv_history);
        rv_hist.setNestedScrollingEnabled(false);
        rv_hist.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_hist.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.history_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder myHolder= (Holder) viewHolder;

                myHolder.itemView.setTag(i);
                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }

            @Override
            public int getItemCount() {
                return 20;
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView name,hqname;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.name);
                    hqname = itemView.findViewById(R.id.hqname);
                }
            } }
        );

        //getEmplist();
        return view;
    }

}
