package com.example.vinay.tabactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by vinay on 4/6/17.
 */
public class AdapterTeam extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Team> data= Collections.emptyList();
    Team current;
    int currentPos=0;

    public AdapterTeam(Context context, List<Team> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.list_item, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        Team current=data.get(position);
        myHolder.name.setText(current.name);
        myHolder.points.setText(current.points);
        myHolder.played.setText(current.played);
        myHolder.win.setText(current.win);
        myHolder.draw.setText(current.draw);
        myHolder.lose.setText(current.lose);
        myHolder.gd.setText(current.gd);
        myHolder.pos.setText(current.position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView points;
        TextView played;
        TextView win;
        TextView draw;
        TextView lose;
        TextView gd;
        TextView pos;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            points= (TextView) itemView.findViewById(R.id.points);
            played = (TextView)itemView.findViewById(R.id.played);
            win = (TextView)itemView.findViewById(R.id.win);
            draw = (TextView)itemView.findViewById(R.id.draw);
            lose = (TextView)itemView.findViewById(R.id.lose);
            gd = (TextView)itemView.findViewById(R.id.gd);
            pos = (TextView)itemView.findViewById(R.id.pos);
        }
    }
}