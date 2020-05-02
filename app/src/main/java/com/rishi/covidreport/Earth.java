package com.rishi.covidreport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;

public class Earth extends Fragment {
    private int globe_case, globe_death;
    private int country_case_1, country_death_1, country_case_2, country_death_2, country_case_3, country_death_3;
    private int country_case_4, country_death_4, country_case_5, country_death_5;
    private TextView globecase, globedeath, cc1, cd1, cc2, cd2, cc3, cd3, cc4, cd4, cc5, cd5;
    private String cn1, cn2, cn3, cn4, cn5;
    private TextView country_name_1, country_name_2, country_name_3, country_name_4, country_name_5;
    private boolean flag = false;

    void fromJSON(JSONArray response) {
        try {
            globe_case = response.getJSONObject(0).getInt("cases");
            globe_death = response.getJSONObject(0).getInt("deaths");

            country_case_1 = response.getJSONObject(1).getInt("cases");
            country_death_1 = response.getJSONObject(1).getInt("deaths");
            cn1 = response.getJSONObject(1).getString("country");

            country_case_2 = response.getJSONObject(2).getInt("cases");
            country_death_2 = response.getJSONObject(2).getInt("deaths");
            cn2 = response.getJSONObject(2).getString("country");

            country_case_3 = response.getJSONObject(3).getInt("cases");
            country_death_3 = response.getJSONObject(3).getInt("deaths");
            cn3 = response.getJSONObject(3).getString("country");

            country_case_4 = response.getJSONObject(4).getInt("cases");
            country_death_4 = response.getJSONObject(4).getInt("deaths");
            cn4 = response.getJSONObject(4).getString("country");

            country_case_5 = response.getJSONObject(5).getInt("cases");
            country_death_5 = response.getJSONObject(5).getInt("deaths");
            cn5 = response.getJSONObject(5).getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (flag) {
            globecase.setText(String.valueOf(globe_case));
            globedeath.setText(String.valueOf(globe_death));

            country_name_1.setText(cn1);
            country_name_2.setText(cn2);
            country_name_3.setText(cn3);
            country_name_4.setText(cn4);
            country_name_5.setText(cn5);

            cc1.setText(String.valueOf(country_case_1));
            cc2.setText(String.valueOf(country_case_2));
            cc3.setText(String.valueOf(country_case_3));
            cc4.setText(String.valueOf(country_case_4));
            cc5.setText(String.valueOf(country_case_5));

            cd1.setText(String.valueOf(country_death_1));
            cd2.setText(String.valueOf(country_death_2));
            cd3.setText(String.valueOf(country_death_3));
            cd4.setText(String.valueOf(country_death_4));
            cd5.setText(String.valueOf(country_death_5));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_earth, container, false);
        flag = true;
        globecase = v.findViewById(R.id.global_case_no);
        globedeath = v.findViewById(R.id.global_death_no);
        cc1 = v.findViewById(R.id.cc1);
        cc2 = v.findViewById(R.id.cc2);
        cc3 = v.findViewById(R.id.cc3);
        cc4 = v.findViewById(R.id.cc4);
        cc5 = v.findViewById(R.id.cc5);

        cd1 = v.findViewById(R.id.cd1);
        cd2 = v.findViewById(R.id.cd2);
        cd3 = v.findViewById(R.id.cd3);
        cd4 = v.findViewById(R.id.cd4);
        cd5 = v.findViewById(R.id.cd5);

        country_name_1 = v.findViewById(R.id.cn1);
        country_name_2 = v.findViewById(R.id.cn2);
        country_name_3 = v.findViewById(R.id.cn3);
        country_name_4 = v.findViewById(R.id.cn4);
        country_name_5 = v.findViewById(R.id.cn5);
        if (flag) {
            globecase.setText(String.valueOf(globe_case));
            globedeath.setText(String.valueOf(globe_death));

            country_name_1.setText(cn1);
            country_name_2.setText(cn2);
            country_name_3.setText(cn3);
            country_name_4.setText(cn4);
            country_name_5.setText(cn5);

            cc1.setText(String.valueOf(country_case_1));
            cc2.setText(String.valueOf(country_case_2));
            cc3.setText(String.valueOf(country_case_3));
            cc4.setText(String.valueOf(country_case_4));
            cc5.setText(String.valueOf(country_case_5));

            cd1.setText(String.valueOf(country_death_1));
            cd2.setText(String.valueOf(country_death_2));
            cd3.setText(String.valueOf(country_death_3));
            cd4.setText(String.valueOf(country_death_4));
            cd5.setText(String.valueOf(country_death_5));
        }
        return v;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        globecase.setText(String.valueOf(globe_case));
        globedeath.setText(String.valueOf(globe_death));

        country_name_1.setText(cn1);
        country_name_2.setText(cn2);
        country_name_3.setText(cn3);
        country_name_4.setText(cn4);
        country_name_5.setText(cn5);

        cc1.setText(String.valueOf(country_case_1));
        cc2.setText(String.valueOf(country_case_2));
        cc3.setText(String.valueOf(country_case_3));
        cc4.setText(String.valueOf(country_case_4));
        cc5.setText(String.valueOf(country_case_5));

        cd1.setText(String.valueOf(country_death_1));
        cd2.setText(String.valueOf(country_death_2));
        cd3.setText(String.valueOf(country_death_3));
        cd4.setText(String.valueOf(country_death_4));
        cd5.setText(String.valueOf(country_death_5));
    }
}
