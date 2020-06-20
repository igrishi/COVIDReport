package com.rishi.covidreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rishi.covidreport.Adapters.DistrictAdapter;
import com.rishi.covidreport.ModalClass.DistrictModal;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        List<DistrictModal> list = new ArrayList<>(MainActivity.district_list);
        RecyclerView recyclerView=findViewById(R.id.recycler_district);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DistrictAdapter adapter=new DistrictAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
