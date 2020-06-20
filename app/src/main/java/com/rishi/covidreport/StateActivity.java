package com.rishi.covidreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rishi.covidreport.Adapters.StateAdapter;
import com.rishi.covidreport.ModalClass.StateModal;

import java.util.ArrayList;
import java.util.List;

public class StateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);
        List<StateModal> list = new ArrayList<>(MainActivity.state_list);
        RecyclerView recyclerView=findViewById(R.id.recycler_state);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        StateAdapter adapter=new StateAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
