package com.example.android.newsapi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.newsapi.R;
import com.example.android.newsapi.activity.DetailActivity;
import com.example.android.newsapi.model.Article;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private static final String TAG = "articleadapter";

    Article Articles;

    public List<Article> articles;
    public int rowLayout;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.urlToImage) ImageView imageViewUrlToImage;
        @BindView(R.id.title) TextView textViewTitle;
        @BindView(R.id.description) TextView textViewDescription;
        @BindView(R.id.publishedAt) TextView textViewPublishedAt;
        @BindView(R.id.news) CardView cardView;

        Context context;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            context = view.getContext();

        }
    }


    public ArticleAdapter(List<Article> articles, int rowLayout, Context context) {
        this.context = context;
        this.rowLayout = rowLayout;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.articles_row_item, parent, false);
        @NonNull MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

        if (articles.get(i).getUrlToImage()==null) {
            holder.imageViewUrlToImage.setVisibility(View.GONE);
        } else {

            final String image = articles.get(i).getUrlToImage();

            //http://sjudd.github.io/glide/doc/getting-started.html

            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.fitCenterTransform())
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .into(holder.imageViewUrlToImage);
        }

        if(articles.get(i).getTitle()==null){
            holder.textViewTitle.setVisibility(View.GONE);
        }
        if(articles.get(i).getDescription()==null){
            holder.textViewDescription.setVisibility(View.GONE);
        }
        if(articles.get(i).getPublishedAt()==null){
            holder.textViewPublishedAt.setVisibility(View.GONE);
        }

        TextView textViewTitle = holder.textViewTitle;
        TextView textViewDescription = holder.textViewDescription;
        TextView textViewPublishedAt = holder.textViewPublishedAt;
        textViewTitle.setText(articles.get(i).getTitle());
        textViewDescription.setText(articles.get(i).getDescription());

        textViewPublishedAt.setText(articles.get(i).getPublishedAt());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailActivity.class);

                Log.d(TAG, "ArticleAdapter");
                intent.putExtra("articles", Articles);
                intent.putExtra("articleKey", articles.get(holder.getAdapterPosition()).getArticleKey());
                intent.putExtra("position", articles.get(holder.getAdapterPosition()));
                intent.putExtra("urlToImage", articles.get(holder.getAdapterPosition()).getUrlToImage());
                intent.putExtra("title", articles.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("author", articles.get(holder.getAdapterPosition()).getAuthor());
                intent.putExtra("description", articles.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("publishedAt", articles.get(holder.getAdapterPosition()).getPublishedAt());
                intent.putExtra("url", articles.get(holder.getAdapterPosition()).getUrl());
                intent.putExtra("content", articles.get(holder.getAdapterPosition()).getContent());
                intent.putExtra("country", articles.get(holder.getAdapterPosition()).getCountry());
                intent.putExtra("category", articles.get(holder.getAdapterPosition()).getCategory());
                intent.putExtra("saveDate", articles.get(holder.getAdapterPosition()).getSaveDate());

                //https://stackoverflow.com/questions/23182853/calling-startactivity-from-outside-of-an-activity-context-requires-the-flag-ac

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

                Log.d(TAG, "articleKey:" + articles.get(i).getArticleKey());
                Log.d(TAG, "articlePosition:" + articles.get(holder.getAdapterPosition()));
                Log.d(TAG, "articleUrlToImage:" + articles.get(i).getUrl());
                Log.d(TAG, "articleTitle:" + articles.get(i).getTitle());
                Log.d(TAG, "articleAuthor:" + articles.get(i).getAuthor());
                Log.d(TAG, "articleDescription:" + articles.get(i).getDescription());
                Log.d(TAG, "articlePublishedAt:" + articles.get(i).getPublishedAt());
                Log.d(TAG, "articleUrl:" + articles.get(i).getUrl());
                Log.d(TAG, "articleContent:" + articles.get(i).getContent());
                Log.d(TAG, "articleCountry:" + articles.get(i).getCountry());
                Log.d(TAG, "articleCategory:" + articles.get(i).getCategory());
                Log.d(TAG, "articleSaveDate:" + articles.get(i).getSaveDate());

                Toast.makeText(holder.context, "Article " + articles.get(i).getTitle() + " " + "selected",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        } else {
            return 0;
        }
        //return articles.size();
    }

    public void setArticles(List<Article> articles) {
        if (articles != null) {
            this.articles = articles;
            notifyDataSetChanged();
        }
    }
}