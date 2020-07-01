package com.vb.redditposts.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.redditposts.R;
import com.vb.redditposts.ui.adapters.PostsRecyclerViewAdapter;
import com.vb.redditposts.ui.presenters.PostsFragmentPresenter;
import com.vb.redditposts.ui.views.IPostsFragment;

public class PostsFragment extends Fragment implements IPostsFragment {

    private static final String POSTS_URL = "https://www.reddit.com/top.json";

    private NavController mNavController;

    private PostsFragmentPresenter mPostsFragmentPresenter;
    PostsRecyclerViewAdapter mAdapter;

    private RecyclerView mPostsRecycler;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        setmNavController();
        mProgressBar = view.findViewById(R.id.progress_bar);
        recyclerInit(view);
        mAdapter = new PostsRecyclerViewAdapter(getContext(), mNavController);
        mPostsFragmentPresenter = new PostsFragmentPresenter(this, mAdapter, mPostsRecycler);
        makeRequest();
        return view;
    }

    private void setmNavController(){
        mNavController = Navigation.findNavController(getActivity(), R.id.host_fragment);
    }

    private void makeRequest(){
        mPostsFragmentPresenter.getPosts(POSTS_URL, mProgressBar);
    }

    private void recyclerInit(View view){
        mPostsRecycler = view.findViewById(R.id.posts_list);
    }



    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }
}
