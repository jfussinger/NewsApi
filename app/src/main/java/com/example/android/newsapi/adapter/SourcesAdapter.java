package com.example.android.newsapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.newsapi.R;
import com.example.android.newsapi.model.Sources;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.MyViewHolder> {

    private static final String TAG = "sourcesadapter";

    //Sources sources;

    public List<Sources> sources;
    public int rowLayout;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView textViewName;
        @BindView(R.id.id) TextView textViewId;
        @BindView(R.id.description) TextView textViewDescription;
        @BindView(R.id.url) TextView textViewUrl;
        @BindView(R.id.category) TextView textViewCategory;
        @BindView(R.id.language) TextView textViewLanguage;
        @BindView(R.id.country) TextView textViewCountry;
        @BindView(R.id.sources)
        CardView cardView;

        Context context;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            context = view.getContext();

        }
    }


    public SourcesAdapter(List<Sources> sources, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.sources = sources;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sources_row_item, parent, false);
        @NonNull MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if(sources.get(i).getId()==null){
            holder.textViewId.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewName.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewDescription.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewUrl.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewCategory.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewLanguage.setVisibility(View.GONE);
        }
        if(sources.get(i).getId()==null){
            holder.textViewCountry.setVisibility(View.GONE);
        }

        TextView textViewId = holder.textViewId;
        TextView textViewName = holder.textViewName;
        TextView textViewDescription = holder.textViewDescription;
        TextView textViewUrl = holder.textViewUrl;
        TextView textViewCategory = holder.textViewCategory;
        TextView textViewLanguage = holder.textViewLanguage;
        TextView textViewCountry = holder.textViewCountry;

        textViewId.setText(sources.get(i).getId());
        textViewName.setText(sources.get(i).getName());
        textViewDescription.setText(sources.get(i).getDescription());
        textViewUrl.setText(sources.get(i).getUrl());
        textViewCategory.setText(sources.get(i).getCategory());
        textViewLanguage.setText(sources.get(i).getLanguage());
        textViewCountry.setText(sources.get(i).getCountry());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(sources.get(i).getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                context.startActivity(websiteIntent);

                Toast.makeText(holder.context, "Sources " + sources.get(i).getUrl() + " " + "selected",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setSources(List<Sources> sources) {
        if (sources != null) {
            this.sources = sources;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (sources != null) {
            return sources.size();
        } else { return 0;
        }
        //return sourcesList.size();
    }

}