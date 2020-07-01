package com.vb.redditposts.ui.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vb.redditposts.data.models.Posts;
import com.vb.redditposts.ui.adapters.PostsRecyclerViewAdapter;
import com.vb.redditposts.ui.views.IPostsFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PostsFragmentPresenter {

    private IPostsFragment mIPostsFragment;

    private List<Posts> mPostsList;

    private PostsRecyclerViewAdapter mAdapter;

    private RecyclerView mRecyclerView;



    public PostsFragmentPresenter(IPostsFragment mIPostsFragment, PostsRecyclerViewAdapter adapter,
                                  RecyclerView recyclerView) {
        this.mIPostsFragment = mIPostsFragment;
        this.mAdapter = adapter;
        this.mRecyclerView = recyclerView;
    }


    private static String makeHttpsRequest(URL url) throws IOException {
        String responce = "";
        if (url == null){
            return responce;
        }
        HttpsURLConnection httpsURLConnection = null;
        InputStream stream = null;
        try {
            httpsURLConnection = (HttpsURLConnection)url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setReadTimeout(30000);
            httpsURLConnection.setConnectTimeout(45000);
            httpsURLConnection.connect();
            if (httpsURLConnection.getResponseCode() == 200){
                stream = httpsURLConnection.getInputStream();
                responce = readFromStream(stream);
            } else {
                Log.e("Connection", "Bad responce code");
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (httpsURLConnection != null){
                httpsURLConnection.disconnect();
            }
            if (stream != null){
                stream.close();
            }
        }
        return responce;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        }
        catch (MalformedURLException m){
            return null;
        }
        return url;
    }

    public static List<Posts> makePostsReq(String postUrl){
        URL url = createURL(postUrl);
        String responce = null;
        try{
            responce = makeHttpsRequest(url);
        } catch (IOException e){
            e.printStackTrace();
        }
        List<Posts> posts = extractPosts(responce);
        return posts;
    }

    private static List<Posts> extractPosts(final String jsonObj){
        List<Posts> posts = new ArrayList<>();
        Bitmap mImageBitmap;
        SimpleDateFormat timeFormat = new SimpleDateFormat("h");
        long currentTime = Calendar.getInstance().getTimeInMillis();
        try{
            JSONObject postsObj = new JSONObject(jsonObj);
            JSONObject data = postsObj.getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            for (int i = 0; i < children.length(); i++){
                mImageBitmap = null;
                JSONObject currentChildren = children.getJSONObject(i);
                JSONObject childData = currentChildren.getJSONObject("data");
                String author = childData.getString("name");
                long time = childData.getLong("created");
                long timeFromCurrentTime = currentTime - time;
                String formattedTime = timeFormat.format(timeFromCurrentTime);
                String picture = childData.getString("thumbnail");
                int comments = childData.getInt("num_comments");
                try {
                    InputStream inputStream = new java.net.URL(picture).openStream();
                    mImageBitmap = BitmapFactory.decodeStream(inputStream);
                } catch (Exception e){
                    e.printStackTrace();
                }
                Log.d("request", "comments " + comments + " Time" + formattedTime);
                posts.add(new Posts(author, formattedTime, mImageBitmap, comments));
            }
        } catch (JSONException e){
            Log.e("JSON", "JSON exception");
        }
        return posts;
    }

    public void getPosts(String postsUrl, ProgressBar progressBar) {
        Thread t = new Thread(() -> {
            try{
                mPostsList = makePostsReq(postsUrl);
                mAdapter.setmPostsList(mPostsList);

            } finally {
                mIPostsFragment.getFragmentActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mIPostsFragment.getFragmentActivity()));
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(mIPostsFragment.getFragmentActivity()
                            , DividerItemDecoration.VERTICAL));
                });
            }
        });
        t.start();

    }
}
