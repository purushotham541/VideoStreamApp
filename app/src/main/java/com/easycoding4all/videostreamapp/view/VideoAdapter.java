package com.easycoding4all.videostreamapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.easycoding4all.videostreamapp.R;
import com.easycoding4all.videostreamapp.model.VideoModel;
import java.util.List;
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>
{
    public static List<VideoModel> videoModelList;
    Context context;
    public VideoAdapter(List<VideoModel> videoModelList, Context context) {
        this.videoModelList = videoModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.my_video_structure,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.tv_title.setText(videoModelList.get(i).getTitle());
        viewHolder.tv_description.setText(videoModelList.get(i).getDescription());
        Glide.with(context)
                .load(videoModelList.get(i).getThumb())
                .override(360,200)
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_url=videoModelList.get(viewHolder.getLayoutPosition()).getUrl();
                Intent intent=new Intent(context,VideoPlay.class);
                intent.putExtra("video_url",current_url);
                intent.putExtra("position",viewHolder.getLayoutPosition());

                context.startActivity(intent);
                //Toast.makeText(context, "Image_Url"+current_url, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return videoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_description;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_title=itemView.findViewById(R.id.video_title);
            tv_description=itemView.findViewById(R.id.video_decription);
            imageView=itemView.findViewById(R.id.iv);

        }
    }
}
