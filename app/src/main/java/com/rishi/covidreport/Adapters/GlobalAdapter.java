package com.rishi.covidreport.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rishi.covidreport.ModalClass.GlobalModal;
import com.rishi.covidreport.R;

import java.util.ArrayList;
import java.util.List;

public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.ViewHolder> implements Filterable {

    private List<GlobalModal> g_list,g_listfull;
    private Context context;

    public  GlobalAdapter(List<GlobalModal> g_list, List<GlobalModal> g_listfull, Context context){
        this.g_list=g_list;
        this.g_listfull=g_listfull;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.global_stats_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String name=g_list.get(position).getCountry_name();
        String flag=g_list.get(position).getFlag();
        int cases=g_list.get(position).getCases();
        int deaths=g_list.get(position).getDeaths();
        holder.name.setText(name);
        Glide.with(context).load(flag).into(holder.flag);
        holder.confirm.setText(String.valueOf(cases));
        holder.deaths.setText(String.valueOf(deaths));


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);
            }
        });
    }

    private void showDialog(int position) {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.country_dialog);

        TextView name=dialog.findViewById(R.id.cd_name);
        TextView confirmed=dialog.findViewById(R.id.cd_confirmed);
        TextView active=dialog.findViewById(R.id.cd_active);
        TextView recovered =dialog.findViewById(R.id.cd_recovered);
        TextView death=dialog.findViewById(R.id.cd_death);
        ImageView flag=dialog.findViewById(R.id.cd_flag);

        name.setText(g_list.get(position).getCountry_name());
        confirmed.setText(String.valueOf(g_list.get(position).getCases()));
        active.setText(String.valueOf(g_list.get(position).getActive()));
        recovered.setText(String.valueOf(g_list.get(position).getRecovered()));
        death.setText(String.valueOf(g_list.get(position).getDeaths()));
        Glide.with(context).load(g_list.get(position).getFlag()).into(flag);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return g_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,confirm,deaths;
        private ImageView flag;
        private View row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.c_name);
            flag=itemView.findViewById(R.id.c_flag);
            confirm=itemView.findViewById(R.id.c_confirm);
            deaths=itemView.findViewById(R.id.c_death);
            row=itemView;
        }
    }


    @Override
    public Filter getFilter() {
        return g_listfilter;
    }

    private Filter g_listfilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // it is automatically executed on the background thread even if we have complicated filter logic, it will not freeze the app
            List<GlobalModal> filteredlist=new ArrayList<>(); // this is a new list which will only contain the filtered items
            if(constraint == null || constraint.length()==0){
                //nothing typed in the search bar
                // since nothing typed hence filtered list will contain all the items
                filteredlist.addAll(g_listfull);
            }else{
                //we type case the charate sequence to string then to lower case so that it is case sensitive
                // and remove the white spaces in the beginning and end using trim()
                String country_name=constraint.toString().toLowerCase().trim();
                Log.d("search", "input: "+country_name);
                for(GlobalModal modal: g_listfull){
                    String x=modal.getCountry_name().toLowerCase();
                    Log.d("search", "country: "+x);
                    if(modal.getCountry_name().toLowerCase().contains(country_name)){
                        // country name is also converted to lower case for matching and then if it
                        // contains the given string then we add it to the filtered list
                        filteredlist.add(modal);
                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filteredlist;
            //this will return the filtered items as Filter result and then publish result method is executed
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // and result will be published on the UI thread over here
            Log.d("search", "publishResults: "+"updating ui");
            g_list.clear();
            g_list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
