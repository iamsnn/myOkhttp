package com.example.myokhttp

import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val homeFeed: HomeFeed):
    RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount():Int{
        return homeFeed.videos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellFromRow = layoutInflater.inflate(R.layout.video_row,parent,false)
        return CustomViewHolder(cellFromRow)
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = homeFeed.videos.get(position)

        holder?.view?.findViewById<TextView>(R.id.textView_video_title).text = video.name
        holder?.view?.findViewById<TextView>(R.id.textView_channel_name).text = video.channel.name

        //load img with Picasso
        val thumbnail1 = holder?.view?.findViewById<ImageView>(R.id.thumbnail)
        Picasso.get().load(video.imageUrl).into(thumbnail1)

        val thumbnail2 = holder?.view?.findViewById<ImageView>(R.id.sthumbnail)
        Picasso.get().load(video.channel.profileImageUrl).into(thumbnail2)

        holder?.video = video
    }
}

// add interactive functions
class CustomViewHolder(val view:View,var video:Video?=null): RecyclerView.ViewHolder(view) {

    companion object {
        val VIDEO_TITLE_KEY =  "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }


    init {

        view.setOnClickListener {

            val intent = Intent(view.context,DetailActivity::class.java)

            //pass content
            intent.putExtra(VIDEO_TITLE_KEY,video?.name)
            intent.putExtra(VIDEO_ID_KEY,video?.id)

            view.context.startActivity(intent)

        }
    }
}