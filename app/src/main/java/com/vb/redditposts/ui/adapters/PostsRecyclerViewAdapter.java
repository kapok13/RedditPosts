package com.vb.redditposts.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.redditposts.R;
import com.vb.redditposts.data.models.Posts;

import java.util.List;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.PostsViewHolder>{

    private List<Posts> mPostsList;

    private Context mContext;

    private NavController mNavController;

    public PostsRecyclerViewAdapter(Context mContext, NavController navController) {
        this.mContext = mContext;
        this.mNavController = navController;
    }

    public void setmPostsList(List<Posts> mPostsList) {
        this.mPostsList = mPostsList;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_posts_item, parent, false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Posts currentPost = mPostsList.get(position);
        holder.authorName.setText(currentPost.getAuthor());
        holder.time.setText(currentPost.getTime());
        holder.commentary.setText(String.valueOf(currentPost.getCommentsNumber()));
        holder.pic.setImageBitmap(currentPost.getPicture());
        holder.pic.setOnClickListener( v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", currentPost);
            mNavController.navigate(R.id.pictureFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        if (mPostsList != null){
            return mPostsList.size();
        } else {
            return 0;
        }
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView authorName;
        TextView time;
        TextView commentary;
        ImageView pic;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.authorName = itemView.findViewById(R.id.author_name_text);
            this.time = itemView.findViewById(R.id.time_text);
            this.commentary = itemView.findViewById(R.id.commentary_number_text);
            this.pic = itemView.findViewById(R.id.picture_image);
        }
    }
}
