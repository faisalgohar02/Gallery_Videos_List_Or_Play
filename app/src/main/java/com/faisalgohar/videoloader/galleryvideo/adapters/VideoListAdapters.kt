package com.faisalgohar.videoloader.galleryvideo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faisalgohar.videoloader.galleryvideo.R
import com.faisalgohar.videoloader.galleryvideo.databinding.VideoAdapterListItemsBinding
import com.faisalgohar.videoloader.galleryvideo.interfaces.OnVideoClickIF
import com.faisalgohar.videoloader.galleryvideo.models.VideoModel

class VideoListAdapters (private val context: Context, private val arrayList: ArrayList<VideoModel>, private val onVideoClickIF: OnVideoClickIF) :
    RecyclerView.Adapter<VideoListAdapters.WeatherHolder>() {

    inner class WeatherHolder(val binding: VideoAdapterListItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        return WeatherHolder(
            VideoAdapterListItemsBinding.inflate(
                LayoutInflater.from(context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.binding.run {

            Glide.with(context)
                .load(arrayList.get(position).videoPath.toString())
                .apply(RequestOptions().centerCrop())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(videoThumb)


            videoThumb.setOnClickListener {
                onVideoClickIF.onVideoClick(arrayList.get(position).videoPath!!)
            }
        }
    }
    override fun getItemCount(): Int = arrayList.size
}