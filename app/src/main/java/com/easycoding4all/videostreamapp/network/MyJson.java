package com.easycoding4all.videostreamapp.network;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.easycoding4all.videostreamapp.model.VideoModel;
import com.easycoding4all.videostreamapp.view.PlayVideoAdapter;
import com.easycoding4all.videostreamapp.view.VideoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyJson extends AsyncTask<String,String,List<VideoModel>>
{

    private Context context;
    private RecyclerView listView;
    int flag;

    public MyJson(Context context, RecyclerView listView, int flag)
    {
        this.context = context;
        this.listView = listView;
        this.flag = flag;
    }

    public MyJson(Context context, RecyclerView listView) {
        this.context = context;
        this.listView = listView;
    }
    @Override
    protected List<VideoModel> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = null;
        try
        {
            URL url=new URL("https://interview-e18de.firebaseio.com/media.json?print=pretty");
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            bufferedReader=new BufferedReader(inputStreamReader);
            String line="";
            stringBuffer=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(httpURLConnection==null)
            {httpURLConnection.disconnect();
            }
            if(bufferedReader==null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        String myjsonfile=stringBuffer.toString();
       // List<String> movienamelist=new ArrayList<>();
        List<VideoModel> movieModellist=new ArrayList<>();
        try
        {
            JSONArray jsonArray=new JSONArray(myjsonfile);
            for (int i=0;i<jsonArray.length();i++)
            {
                VideoModel videoModel=new VideoModel();
                JSONObject videodetails=jsonArray.getJSONObject(i);
                videoModel.setDescription(videodetails.getString("description"));
                videoModel.setThumb(videodetails.getString("thumb"));
                videoModel.setTitle(videodetails.getString("title"));
                videoModel.setUrl(videodetails.getString("url"));
                videoModel.setId(videodetails.getInt("id"));
                movieModellist.add(videoModel);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return movieModellist;


    }

    @Override
    protected void onPostExecute(List<VideoModel> movieModellist) {
        super.onPostExecute(movieModellist);
        if(flag==1)
        {
            VideoAdapter videoAdapter=new VideoAdapter(movieModellist,context);
            listView.setAdapter(videoAdapter);

        }
        else if(flag==2)
        {
            PlayVideoAdapter playVideoAdapter=new PlayVideoAdapter(movieModellist,context);
            listView.setAdapter(playVideoAdapter);

        }



    }
}
