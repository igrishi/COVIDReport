package com.rishi.covidreport.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rishi.covidreport.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class Graph extends Fragment {
    private static String TAG = "Graph";
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
    public static String dis_name = null;
    public static String state_name = null;

    public static void setstate(int confirm_no_s, int active_no_s, int recover_no_s, int death_no_s) {
        Graph.confirm_no_s = confirm_no_s;
        Graph.active_no_s = active_no_s;
        Graph.recover_no_s = recover_no_s;
        Graph.death_no_s = death_no_s;
        Log.d(TAG, "setstate: setstate done");
    }

    public static void setcountry(int confirm_no_c, int active_no_c, int recover_no_c, int death_no_c) {
        Graph.confirm_no_c = confirm_no_c;
        Graph.active_no_c = active_no_c;
        Graph.recover_no_c = recover_no_c;
        Graph.death_no_c = death_no_c;
        Log.d(TAG, "setcountry: setcountry done");
    }

    public static void setdistrict(int confirm_no, int active_no, int recover_no, int death_no) {
        Graph.confirm_no = confirm_no;
        Graph.active_no = active_no;
        Graph.recover_no = recover_no;
        Graph.death_no = death_no;
        Log.d(TAG, "set country: set country done");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_graph, container, false);
        TextView dn = view.findViewById(R.id.district_graph_name);
        TextView sn = view.findViewById(R.id.state_graph_name);
        Log.d(TAG, "onCreateView: " + dis_name + " " + state_name);
            dn.setText(dis_name);
            sn.setText(state_name);
        PieChart pie_s = view.findViewById(R.id.piechart);
        pie_s.addPieSlice(new PieModel("confirmed", confirm_no_s, Color.parseColor("#E24B4B")));
        pie_s.addPieSlice(new PieModel("active", active_no_s, Color.parseColor("#60A0E4")));
        pie_s.addPieSlice(new PieModel("recovered", recover_no_s, Color.parseColor("#61E964")));
        pie_s.addPieSlice(new PieModel("death", death_no_s, Color.parseColor("#4A524A")));

        PieChart pie_c = view.findViewById(R.id.piechart_c);
        pie_c.addPieSlice(new PieModel("confirmed", confirm_no_c, Color.parseColor("#E24B4B")));
        pie_c.addPieSlice(new PieModel("active", active_no_c, Color.parseColor("#60A0E4")));
        pie_c.addPieSlice(new PieModel("recovered", recover_no_c, Color.parseColor("#61E964")));
        pie_c.addPieSlice(new PieModel("death", death_no_c, Color.parseColor("#4A524A")));

        PieChart pie_d = view.findViewById(R.id.piechart_d);
        pie_d.addPieSlice(new PieModel("confirmed", confirm_no, Color.parseColor("#E24B4B")));
        pie_d.addPieSlice(new PieModel("active", active_no, Color.parseColor("#60A0E4")));
        pie_d.addPieSlice(new PieModel("recovered", recover_no, Color.parseColor("#61E964")));
        pie_d.addPieSlice(new PieModel("death", death_no, Color.parseColor("#4A524A")));

        pie_s.startAnimation();
        pie_d.startAnimation();
        pie_c.startAnimation();
        return view;
    }

}
