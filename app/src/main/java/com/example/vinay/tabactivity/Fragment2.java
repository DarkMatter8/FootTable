package com.example.vinay.tabactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class Fragment2 extends Fragment {

    private AdapterTeam mAdapter;
    public String jsondata;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.tab2,container,false);

        String jsonURL = "http://api.football-data.org/v1/competitions/436/teams";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(jsonURL)
                .header("X-Auth-Token","a75aa1e62d6c4c87bd9fc71f7de21358")
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

                            JSONArray jArray = json.getJSONArray("teams");

                            for(int i=0;i<jArray.length();i++){
                                JSONObject json_data = jArray.getJSONObject(i);
                                Team TData = new Team();
                                TData.name= json_data.getString("name");

                                data.add(TData);
                            }

                            RecyclerView mTeamlist = (RecyclerView)view.findViewById(R.id.recycler_view);
                            mAdapter = new AdapterTeam(getActivity(), data);


                            mTeamlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mTeamlist.setAdapter(mAdapter);




                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}
