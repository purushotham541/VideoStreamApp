package com.easycoding4all.videostreamapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.easycoding4all.videostreamapp.R;
import com.easycoding4all.videostreamapp.model.VideoModel;
import com.easycoding4all.videostreamapp.network.MyJson;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<VideoModel> videoModelList;
    Glide glide;
    Toolbar toolbar;
    GoogleSignInClient googleSignInClient;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar=findViewById(R.id.pgbr_sgn_out);
        progressBar.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("Videos list");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this,gso);
        recyclerView=findViewById(R.id.rcv);
        videoModelList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VideoAdapter videoAdapter=new VideoAdapter(videoModelList,this);
        recyclerView.setAdapter(videoAdapter);
        new MyJson(this,recyclerView,1).execute();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
         super.onCreateOptionsMenu(menu);
         MenuInflater menuInflater=getMenuInflater();
         menuInflater.inflate(R.menu.mymenu,menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.sign_out)
         {
             progressBar.setVisibility(View.VISIBLE);
             googleSignInClient.signOut();
             Intent intent=new Intent(MainActivity.this,SigninActivity.class);
             startActivity(intent);
             progressBar.setVisibility(View.INVISIBLE);
         }
         return true;
    }
}
