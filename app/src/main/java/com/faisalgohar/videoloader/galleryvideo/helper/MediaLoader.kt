package com.faisalgohar.videoloader.galleryvideo.helper

import android.annotation.SuppressLint
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.faisalgohar.videoloader.galleryvideo.models.VideoModel

class MediaLoader {


    @SuppressLint("Range")
    fun loadVideo(context: Context): ArrayList<VideoModel> {
        var videoArrayList = ArrayList<VideoModel>()
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID
        )
        val selection = MediaStore.Video.Media.MIME_TYPE + " ='video/mp4' OR " +
                MediaStore.Video.Media.MIME_TYPE + " ='video/WMV' OR " +
                MediaStore.Video.Media.MIME_TYPE + " ='video/MKV' OR " +
                MediaStore.Video.Media.MIME_TYPE + " ='video/AVI' OR " +
                MediaStore.Video.Media.MIME_TYPE + " ='video/MOV'"

        var cursor = context.getApplicationContext().getContentResolver().query(
            uri, projection, selection, null,
            MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        )
        val DISPLAY_NAME_COLUMN = MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        val PATH_COLUMN = MediaStore.Video.Media.DATA
        val ID_COLUMN = MediaStore.Video.Media._ID
        while (cursor!!.moveToNext()) {
            val bucket = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME_COLUMN))
            val image = cursor.getString(cursor.getColumnIndex(PATH_COLUMN))
            val dataId = cursor.getString(cursor.getColumnIndex(ID_COLUMN))

            Log.d("TAG", "loadVideo: bucket= ${bucket}")
            Log.d("TAG", "loadVideo: image= ${image}")
            Log.d("TAG", "loadVideo: dataId= ${dataId}")

            videoArrayList.add(VideoModel(bucket,image,0))


        }
        cursor.close()
        return videoArrayList
    }

}