package com.rishi.covidreport;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private static int confirm_no, active_no, recover_no, death_no;
    private static int confirm_no_s, active_no_s, recover_no_s, death_no_s;
    private static int confirm_no_c, active_no_c, recover_no_c, death_no_c;
    @SuppressLint("StaticFieldLeak")
    private static TextView state_new_case, country_new_case;
    private static int state_n_case, country_n_case;
    private TextView district_name, state_name;
    static String statecode;

    public MyLocation() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_my_location, container, false);
        Log.d(TAG, "onCreateView: ");
        //finding id
        district_name = v.findViewById(R.id.district_name);
        state_name = v.findViewById(R.id.state_name);
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

    void updatingdata(JSONObject response) {
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
    }

    void updatingstatedata(JSONObject response) {
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
    }

    @SuppressLint("SetTextI18n")
    void updatecountrydata(JSONObject response) {
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
    }

    @SuppressLint("SetTextI18n")
    void newcases(JSONObject response, String statecode) {
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

    void setslocationdata(String district, String state) {
        district_name.setText(district);
        state_name.setText(state);
    }
}
