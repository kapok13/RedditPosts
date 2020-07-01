package com.vb.redditposts.ui.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vb.redditposts.R;
import com.vb.redditposts.ui.views.IPictureFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureFragmentPresenter {

    private IPictureFragment mIPictureFragment;
    private FileOutputStream mOutputStream;

    public PictureFragmentPresenter(IPictureFragment mIPictureFragment) {
        this.mIPictureFragment = mIPictureFragment;
    }

    public void requestForStorageWritePermission(int requestCode, Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , requestCode);
        }
    }

    public void requestForStorageReadPermission(int requestCode, Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , requestCode);
        }
    }

    public void downloadPicture(ImageView imageView){
        BitmapDrawable picMb = (BitmapDrawable) imageView.getDrawable();
        Bitmap bm = picMb.getBitmap();
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Pictures/" + "/AnimeWallpapers/");
        dir.mkdir();
        File file = new File(dir, System.currentTimeMillis() + ".jpg");
        try {
            mOutputStream = new FileOutputStream(file);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 100, mOutputStream);
        Toast.makeText(mIPictureFragment.provideContext(),
                mIPictureFragment.provideContext()
                .getResources().getString(R.string.photo_saved), Toast.LENGTH_LONG)
                .show();
        try {
            mOutputStream.flush();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        try {
            mOutputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
