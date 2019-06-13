package com.easycoding4all.videostreamapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easycoding4all.videostreamapp.R;
import com.easycoding4all.videostreamapp.model.VideoModel;
import com.nostra13.universalimageloader.utils.L;

import java.util.List;

public class PlayVideoAdapter extends RecyclerView.Adapter<PlayVideoAdapter.ViewHolder2>
{
    private List<VideoModel> videoModelList;
    Context context;
    // int resource;
    /*public VideoAdapter(List<VideoModel> videoModelList, Context context, int resource) {
        this.videoModelList = videoModelList;
        this.context = context;
        this.resource = resource;
    }*/


    public PlayVideoAdapter(List<VideoModel> videoModelList, Context context) {
        this.videoModelList = videoModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public PlayVideoAdapter.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.videostructure,parent,false);
        PlayVideoAdapter.ViewHolder2 viewHolder=new PlayVideoAdapter.ViewHolder2(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayVideoAdapter.ViewHolder2 holder, int position)
    {
        holder.tv_title.setText(videoModelList.get(position).getTitle());
        holder.tv_description.setText(videoModelList.get(position).getDescription());
        Glide.with(context)
                .load(videoModelList.get(position).getThumb())
                .override(360,200)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int list_position=holder.getLayoutPosition();
               String url= videoModelList.get(list_position).getUrl();
                Toast.makeText(context,""+url,Toast.LENGTH_LONG).show();
                videoModelList.remove(holder.getAdapterPosition());




            }
        });



    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder
    {

        TextView tv_title,tv_description;
        ImageView imageView;
        public ViewHolder2(@NonNull View itemView)
        {

                super(itemView);
                tv_title=itemView.findViewById(R.id.v_title);
                tv_description=itemView.findViewById(R.id.v_decription);
                imageView=itemView.findViewById(R.id.iv_playVideo);



        }

    }
}
