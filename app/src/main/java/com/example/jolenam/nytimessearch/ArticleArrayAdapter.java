package com.example.jolenam.nytimessearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jolenam.nytimessearch.Activities.ArticleActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jolenam on 6/20/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

    // store member variable for articles
    private List<Article> mArticles;

    // Pass in article array into constructor
    public ArticleArrayAdapter(List<Article> articles) {
        mArticles = articles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivImage;
        public TextView tvTitle;
        public TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvType = (TextView) itemView.findViewById(R.id.tvType);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Article article = mArticles.get(position);

            Intent i = new Intent(v.getContext(), ArticleActivity.class);

            // pass in that article into intent

            i.putExtra("article", article);

            // launch activity
            v.getContext().startActivity(i);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate custom layout
        View articleView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);

        // Set item views based on data model
        TextView textView = viewHolder.tvTitle;
        textView.setText(article.getHeadline());

        TextView type = viewHolder.tvType;
        type.setText(article.getType());

        ImageView imgView = viewHolder.ivImage;
        imgView.setImageResource(0);

        String thumbnail = article.getThumbNail();

        if (!TextUtils.isEmpty(thumbnail)) {
            // POTENTIAL PROBLEM HERE: imgView.getContext()???
            Picasso.with(imgView.getContext())
                    .load(thumbnail)
                    //.transform(new RoundedCornersTransformation(5, 5))
                    .into(imgView);
            imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

}
