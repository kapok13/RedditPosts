package com.vb.redditposts.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.redditposts.R;
import com.vb.redditposts.ui.adapters.PostsRecyclerViewAdapter;
import com.vb.redditposts.ui.presenters.PostsFragmentPresenter;
import com.vb.redditposts.ui.views.IPostsFragment;

public class PostsFragment extends Fragment implements IPostsFragment {

    private static final String POSTS_URL = "https://www.reddit.com/top.json";

    private NavController mNavController;

    private PostsFragmentPresenter mPostsFragmentPresenter;

    private RecyclerView mPostsRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPostsFragmentPresenter = new PostsFragmentPresenter(this);
        makeRequest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        setmNavController();
        recyclerInit(view);
        return view;
    }

    private void setmNavController(){
        mNavController = Navigation.findNavController(getActivity(), R.id.host_fragment);
    }

    private void makeRequest(){
        mPostsFragmentPresenter.getPosts(POSTS_URL);
    }

    private void recyclerInit(View view){
        mPostsRecycler = view.findViewById(R.id.posts_list);
        PostsRecyclerViewAdapter adapter = new PostsRecyclerViewAdapter(getContext(), mNavController);
        adapter.setmPostsList(mPostsFragmentPresenter.getmPostsList());
        mPostsRecycler.setAdapter(adapter);
        mPostsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mPostsRecycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public Activity getFragmentActivity() {
        return getActivity();
    }
}
