package com.example.myapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.ArrayList;

public class NameAllMasechtotAdapter extends RecyclerView.Adapter<NameAllMasechtotAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<String> allMasechtot = new ArrayList<>();

    public NameAllMasechtotAdapter(Context context, ArrayList<String> allMasechtot ) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.allMasechtot =allMasechtot;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_one_masechet, parent, false);
        return new NameAllMasechtotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setHolder(allMasechtot.get(position));

    }

    @Override
    public int getItemCount() {
        return allMasechtot.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView masechet;

        ViewHolder(View itemView) {
            super(itemView);
            masechet = itemView.findViewById(R.id.masechet_text);
        }

        public void setHolder(String nameMasechet) {
            masechet.setText(nameMasechet);
        }
    }



}
