package com.easycoding4all.videostreamapp.view;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.easycoding4all.videostreamapp.R;
import com.easycoding4all.videostreamapp.model.VideoModel;
import com.easycoding4all.videostreamapp.network.MyJson;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.List;

public class VideoPlay extends AppCompatActivity
{
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    String video_uri="";
    RecyclerView rcv;
    List<VideoModel> videoModelList;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        rcv=findViewById(R.id.video_play_rcv);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        assert bundle != null;
        video_uri=bundle.getString("video_url");
        position=bundle.getInt("position");
        simpleExoPlayerView=(SimpleExoPlayerView) findViewById(R.id.explyr);
        playVideo(video_uri);

        videoModelList=new ArrayList<>();
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        PlayVideoAdapter playVideoAdapter=new PlayVideoAdapter(videoModelList,getApplicationContext());
        rcv.setAdapter(playVideoAdapter);
        new MyJson(this,rcv,2).execute();
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState)
                {
                    case ExoPlayer.STATE_ENDED:
                        video_uri=VideoAdapter.videoModelList.get(position++).getUrl();
                        Toast.makeText(getApplicationContext(),""+video_uri,Toast.LENGTH_LONG).show();
                        playVideo(video_uri);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


    }

    @Override
    protected void onResume()
    {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (simpleExoPlayer != null) {
           long position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    public void playVideo(String url)
    {
        try
        {
            BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
            TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer=ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            Uri uri=Uri.parse(url);
            DefaultHttpDataSourceFactory defaultHttpDataSourceFactory=new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
            MediaSource mediaSource=new ExtractorMediaSource(uri,defaultHttpDataSourceFactory,extractorsFactory,null,null);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
        catch (Exception e)

        {
            Toast.makeText(getApplicationContext(),"Exception",Toast.LENGTH_LONG).show();
        }

    }



}
