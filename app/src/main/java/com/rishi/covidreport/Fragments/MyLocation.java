package com.rishi.covidreport.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.rishi.covidreport.DistrictActivity;
import com.rishi.covidreport.Fragments.Graph;
import com.rishi.covidreport.MainActivity;
import com.rishi.covidreport.ModalClass.DistrictModal;
import com.rishi.covidreport.R;
import com.rishi.covidreport.StateActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLocation extends Fragment {
    private static String TAG = "MainActivity";
    @SuppressLint("StaticFieldLeak")
    private static TextView confirm, active, recover, death,
            confirm_state, active_state, recover_state, death_state,
            confirm_c, active_c, recover_c, death_c;
    @SuppressLint("StaticFieldLeak")
    private static TextView lastupdate;
    private static String last_update_time;
    private static int confirm_no;
    private static int active_no;
    private static int recover_no;
    private static int death_no;
    private static int confirm_no_s;
    private static int active_no_s;
    private static int recover_no_s;
    private static int death_no_s;
    private static int confirm_no_c;
    private static int active_no_c;
    private static int recover_no_c;
    private static int death_no_c;
    @SuppressLint("StaticFieldLeak")
    private static TextView state_new_case;
    @SuppressLint("StaticFieldLeak")
    private static TextView country_new_case;
    private static int state_n_case;
    private static int country_n_case;
    private TextView district_name;
    private TextView state_name;
    public static String statecode;
    private ProgressBar local;
    private boolean flag=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_location, container, false);
        Log.d(TAG, "onCreateView: ");
        //finding id
        district_name = v.findViewById(R.id.district_name);
        state_name = v.findViewById(R.id.state_name);
        local=v.findViewById(R.id.d_progress);

        confirm = v.findViewById(R.id.confirm_number);
        active = v.findViewById(R.id.active_number);
        recover = v.findViewById(R.id.recovered_number);
        death = v.findViewById(R.id.death_number);

        confirm_state = v.findViewById(R.id.confirm_state_number);
        active_state = v.findViewById(R.id.active_state_number);
        recover_state = v.findViewById(R.id.recovered_state_no);
        death_state = v.findViewById(R.id.death_state_number);

        confirm_c = v.findViewById(R.id.confirm_country_number);
        active_c = v.findViewById(R.id.active_country_number);
        recover_c = v.findViewById(R.id.recovered_country_no);
        death_c = v.findViewById(R.id.death_country_number);

        lastupdate = v.findViewById(R.id.last_updated);
        state_new_case = v.findViewById(R.id.new_state_cases);
        country_new_case = v.findViewById(R.id.new_country_cases);
        if(flag){
            local.setVisibility(View.GONE);
        }

        ConstraintLayout state = v.findViewById(R.id.country_data_layout);
        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), StateActivity.class);
                startActivity(intent);
            }
        });
        ConstraintLayout district=v.findViewById(R.id.state_data_layout);
        district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DistrictActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: view restored");
        updatingdata_t();
        updatingstatedata_t();
        updatecountrydata_s();
        newcases_t();
        district_name.setText(MainActivity.District);
        state_name.setText(MainActivity.State);
    }

    public void updatingdata(JSONObject response) {
        try {
            confirm_no = response.getInt("confirmed");
            active_no = response.getInt("active");
            recover_no = response.getInt("recovered");
            death_no = response.getInt("deceased");
            Log.d(TAG, "updatingdata: " + confirm_no + " " + active_no + " " + recover_no + " " + death_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        confirm.setText(String.valueOf(confirm_no));
        active.setText(String.valueOf(active_no));
        recover.setText(String.valueOf(recover_no));
        death.setText(String.valueOf(death_no));

        Graph.setdistrict(confirm_no, active_no, recover_no, death_no);
    }

    public void updatingstatedata(JSONObject response) {
        try {
            statecode = response.getString("statecode");
            statecode = statecode.toLowerCase();
            confirm_no_s = response.getInt("confirmed");
            active_no_s = response.getInt("active");
            recover_no_s = response.getInt("recovered");
            death_no_s = response.getInt("deaths");
            Log.d(TAG, "updatingstatedata: " + confirm_no + " " + active_no + " " + recover_no + " " + death_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        confirm_state.setText(String.valueOf(confirm_no_s));
        active_state.setText(String.valueOf(active_no_s));
        recover_state.setText(String.valueOf(recover_no_s));
        death_state.setText(String.valueOf(death_no_s));

        Graph.setstate(confirm_no_s, active_no_s, recover_no_s, death_no_s);
    }

    @SuppressLint("SetTextI18n")
    public void updatecountrydata(JSONObject response) {
        try {
            confirm_no_c = response.getInt("confirmed");
            active_no_c = response.getInt("active");
            recover_no_c = response.getInt("recovered");
            death_no_c = response.getInt("deaths");
            last_update_time = response.getString("lastupdatedtime");
            Log.d(TAG, "updatingdata: " + confirm_no + " " + active_no + " " + recover_no + " " + death_no);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        confirm_c.setText(String.valueOf(confirm_no_c));
        active_c.setText(String.valueOf(active_no_c));
        recover_c.setText(String.valueOf(recover_no_c));
        death_c.setText(String.valueOf(death_no_c));

        lastupdate.setText("     last updated on\n " + last_update_time);
        Graph.setcountry(confirm_no_c, active_no_c, recover_no_c, death_no_c);
    }

    @SuppressLint("SetTextI18n")
    public void newcases(JSONObject response, String statecode) {
        try {
            Log.d(TAG, "newcases: " + statecode);
            state_n_case = response.getInt(statecode);
            country_n_case = response.getInt("tt");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (state_n_case != 0) {
            state_new_case.setText("+" + state_n_case + " new cases in your state");
        }
        if (country_n_case != 0) {
            country_new_case.setText("+" + country_n_case + " new cases in india");
        }
        flag=true;
        local.setVisibility(View.GONE);
    }

    private static void updatingdata_t() {
        confirm.setText(String.valueOf(confirm_no));
        active.setText(String.valueOf(active_no));
        recover.setText(String.valueOf(recover_no));
        death.setText(String.valueOf(death_no));
    }

    private static void updatingstatedata_t() {
        confirm_state.setText(String.valueOf(confirm_no_s));
        active_state.setText(String.valueOf(active_no_s));
        recover_state.setText(String.valueOf(recover_no_s));
        death_state.setText(String.valueOf(death_no_s));
    }

    @SuppressLint("SetTextI18n")
    private static void updatecountrydata_s() {
        confirm_c.setText(String.valueOf(confirm_no_c));
        active_c.setText(String.valueOf(active_no_c));
        recover_c.setText(String.valueOf(recover_no_c));
        death_c.setText(String.valueOf(death_no_c));
        lastupdate.setText("     last updated on\n " + last_update_time);
    }

    @SuppressLint("SetTextI18n")
    private void newcases_t() {
        if (state_n_case != 0) {
            state_new_case.setText("+" + state_n_case + " new cases in your state");
        }
        if (country_n_case != 0) {
            country_new_case.setText("+" + country_n_case + " new cases in india");
        }
    }

    public void setslocationdata(String district, String state) {
        district_name.setText(district);
        state_name.setText(state);
        Graph.dis_name = district;
        Graph.state_name = state;
        Log.d(TAG, "setslocationdata: " + district + " " + state);
    }
}
