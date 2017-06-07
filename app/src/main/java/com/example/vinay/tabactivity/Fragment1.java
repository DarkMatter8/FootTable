package com.example.vinay.tabactivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vinay on 4/6/17.
 */
public class Fragment1 extends Fragment {

    private AdapterTeam mAdapter;
    public String jsondata;
    public String matchweek;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {


            final View view = inflater.inflate(R.layout.tab1,container,false);


            String jsonURL = "http://api.football-data.org/v1/competitions/426/leagueTable";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(jsonURL)
                    .build();

            okhttp3.Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    Toast.makeText(getActivity(),"some error",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    jsondata = response.body().string();


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<Team> data=new ArrayList<>();
                                JSONObject json = new JSONObject(jsondata);
                                matchweek = json.getString("matchday");
                                JSONArray jArray = json.getJSONArray("standing");

                                for(int i=0;i<jArray.length();i++){
                                    JSONObject json_data = jArray.getJSONObject(i);
                                    Team TData = new Team();
                                    TData.name= json_data.getString("teamName");
                                    TData.points=json_data.getString("points");
                                    TData.played=json_data.getString("playedGames");
                                    TData.win=json_data.getString("wins");
                                    TData.draw=json_data.getString("draws");
                                    TData.lose=json_data.getString("losses");
                                    TData.gd=json_data.getString("goalDifference");
                                    data.add(TData);
                                }



                                RecyclerView mTeamlist = (RecyclerView)view.findViewById(R.id.recycler_view);
                                mAdapter = new AdapterTeam(getActivity(), data);
                                mTeamlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                                mTeamlist.setAdapter(mAdapter);


                                Toast.makeText(getActivity(),"MatchWeek:"+matchweek, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });

            return view;
        }else{
            View view = inflater.inflate(R.layout.no_internet,container,false);
            return view;
        }


    }
}
