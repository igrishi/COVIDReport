package com.rishi.covidreport.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rishi.covidreport.ModalClass.StateModal;
import com.rishi.covidreport.R;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {
    private List<StateModal> statelist;
    private Context context;
    public StateAdapter(List<StateModal> statelist) {
        this.statelist = statelist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.state_row,parent,false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(statelist.get(position).getName());
        int confirmed=statelist.get(position).getConfirmed();
        int death=statelist.get(position).getDeaths();
        holder.confirm.setText(String.valueOf(confirmed));
        holder.death.setText(String.valueOf(death));
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }

    private void showDialog(int position) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.state_dialog);
        TextView name=dialog.findViewById(R.id.sd_name);
        TextView confirmed=dialog.findViewById(R.id.sd_confirmed);
        TextView active=dialog.findViewById(R.id.sd_active);
        TextView recovered =dialog.findViewById(R.id.sd_recovered);
        TextView death=dialog.findViewById(R.id.sd_death);

        name.setText(statelist.get(position).getName());
        confirmed.setText(String.valueOf(statelist.get(position).getConfirmed()));
        active.setText(String.valueOf(statelist.get(position).getActive()));
        recovered.setText(String.valueOf(statelist.get(position).getRecovered()));
        death.setText(String.valueOf(statelist.get(position).getDeaths()));

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return statelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,confirm,death;
        View row;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.s_name);
            confirm=itemView.findViewById(R.id.s_confirm);
            death=itemView.findViewById(R.id.s_death);
            row=itemView;
        }
    }
}
