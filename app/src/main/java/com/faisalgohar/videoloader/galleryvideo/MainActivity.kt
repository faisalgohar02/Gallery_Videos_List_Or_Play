package com.faisalgohar.videoloader.galleryvideo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.faisalgohar.videoloader.galleryvideo.adapters.VideoListAdapters
import com.faisalgohar.videoloader.galleryvideo.databinding.ActivityMainBinding
import com.faisalgohar.videoloader.galleryvideo.helper.MediaLoader
import com.faisalgohar.videoloader.galleryvideo.interfaces.OnVideoClickIF
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() , OnVideoClickIF {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkPermission()) {
            requestPermission()
            binding.loadBtn.visibility= View.VISIBLE
        }
        else{
            binding.loadBtn.visibility= View.GONE
           var mediaLoader= MediaLoader()
            binding.videoRecyclerView.run {
                layoutManager = GridLayoutManager(this@MainActivity,3)
                setHasFixedSize(true)
                adapter = VideoListAdapters(this@MainActivity, mediaLoader.loadVideo(this@MainActivity),this@MainActivity)
            }
        }
        binding.loadBtn.setOnClickListener {

            if(checkPermission())
            {
                binding.loadBtn.visibility= View.GONE
                var mediaLoader= MediaLoader()
                binding.videoRecyclerView.run {
                    layoutManager = GridLayoutManager(this@MainActivity,3)
                    setHasFixedSize(true)
                    adapter = VideoListAdapters(this@MainActivity, mediaLoader.loadVideo(this@MainActivity),this@MainActivity)
                }
            }
            else{
                binding.loadBtn.visibility= View.VISIBLE
                Toast.makeText(this, "Read Gallery Permission Not Granted", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 121)
    }



    override fun onVideoClick(path: String) {
        val videoFile = File(path)
        val fileUri: Uri =
            FileProvider.getUriForFile(
                Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", videoFile)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, "video/*")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }

}