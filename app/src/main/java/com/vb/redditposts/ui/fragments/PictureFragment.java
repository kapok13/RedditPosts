package com.vb.redditposts.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.vb.redditposts.R;
import com.vb.redditposts.data.models.Posts;
import com.vb.redditposts.ui.presenters.PictureFragmentPresenter;
import com.vb.redditposts.ui.views.IPictureFragment;

public class PictureFragment extends Fragment implements IPictureFragment {

    private Posts mPost;

    private ImageView mPostPic;

    private PictureFragmentPresenter mPictureFragmentPresenter;

    private static final int STORAGE_CHANGE_REQUEST_CODE = 2;
    private static final int STORAGE_READ_REQUEST_CODE = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        setmPost();
        mPictureFragmentPresenter = new PictureFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        mPostPic = view.findViewById(R.id.post_image);
        mPostPic.setImageBitmap(mPost.getPicture());
        return view;
    }

    private void setmPost(){
        if (getArguments().getSerializable("post") != null){
            mPost = (Posts) getArguments().getSerializable("post");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_picture, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_pic){
            mPictureFragmentPresenter.requestForStorageWritePermission(STORAGE_CHANGE_REQUEST_CODE,
                    getContext(), getActivity());
            mPictureFragmentPresenter.requestForStorageReadPermission(STORAGE_READ_REQUEST_CODE,
                    getContext(), getActivity());
            mPictureFragmentPresenter.downloadPicture(mPostPic);
            NavController navController = Navigation.findNavController(getActivity(), R.id.host_fragment);
            navController.navigate(R.id.postsFragment);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Context provideContext() {
        return getContext();
    }
}
