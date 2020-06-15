package com.rishi.covidreport.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rishi.covidreport.Adapters.GlobalAdapter;
import com.rishi.covidreport.ModalClass.GlobalModal;
import com.rishi.covidreport.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Earth extends Fragment{

    private List<GlobalModal> g_list,g_listfull;
    private String TAG="Earth";
    private GlobalAdapter adapter;
    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_earth,container,false);
        g_list=new ArrayList<>();
        g_listfull=new ArrayList<>();
        loading=view.findViewById(R.id.g_progress);
        globe_data();
        Context context = getContext();
        RecyclerView recycler = view.findViewById(R.id.global_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recycler.setLayoutManager(layoutManager);
        adapter=new GlobalAdapter(g_list,g_listfull, context);
        recycler.setAdapter(adapter);
        SearchView searchView=view.findViewById(R.id.c_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // we will be using this only because we want to change our recycler view in real time
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    private void globe_data(){
        Log.d("global_data", "globedata: ");
        String globe_url = "https://corona.lmao.ninja/v2/countries";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(globe_url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.d(TAG, "onSuccess: "+"global data obtained");
                for(int i=0;i<response.length();++i){
                    try {
                        JSONObject country=response.getJSONObject(i);
                        String country_name=country.getString("country");
                        String flag=country.getJSONObject("countryInfo").getString("flag");
                        int cases=country.getInt("cases");
                        int active=country.getInt("active");
                        int recovered=country.getInt("recovered");
                        int deaths=country.getInt("deaths");

                        GlobalModal modal=new GlobalModal(country_name,flag,cases,deaths,recovered,active);
                        g_list.add(modal);
                        g_listfull.add(modal);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, "onFailure: "+throwable.toString()+" "+statusCode);
            }
        });
    }

}
