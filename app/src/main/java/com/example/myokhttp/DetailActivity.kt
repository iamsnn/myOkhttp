package com.example.myokhttp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class DetailActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = DetailAdapter()

        //do something
        //VIDEO_TITLE_KEY
        val navTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navTitle

        fetchJSON()

    }

    private fun fetchJSON() {
        val vId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY,-1)
        val url = "https://api.letsbuildthatapp.com/youtube/course_detail?id="+vId
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Wrong")
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val secondFeeds = gson.fromJson(body,Array<SecondFeed>::class.java)


                runOnUiThread{
                    recyclerView.adapter = DetailAdapter(secondFeeds)
                }
            }

        })

    }

    private class DetailAdapter(val secondFeeds :Array<SecondFeed>):
        RecyclerView.Adapter<myViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val cellFromRow = layoutInflater.inflate(R.layout.video_row2,parent,false)
            return myViewHolder(cellFromRow)
        }

        override fun onBindViewHolder(holder: myViewHolder, position: Int) {
            val secondFeed = secondFeeds.get(position)

            holder?.view?.findViewById<TextView>(R.id.detail_title).text = secondFeed.name
            holder?.view?.findViewById<TextView>(R.id.detail_content).text = secondFeed.duration

            //load img with Picasso
            val thumbnail = holder?.view?.findViewById<ImageView>(R.id.detail_image)
            Picasso.get().load(secondFeed.imageUrl).into(thumbnail)

            holder?.secondFeed = secondFeed
        }

        override fun getItemCount(): Int {
            return secondFeeds.size
        }
    }

    class myViewHolder(val view: View, var secondFeed:SecondFeed?=null): RecyclerView.ViewHolder(view) {

        companion object{
            val VIDEO_LINK_KEY = "video_link"
        }

        init {
            view.setOnClickListener {
                val intent = Intent(view.context,SecondPage::class.java)
                intent.putExtra(VIDEO_LINK_KEY,secondFeed?.link)

                view.context.startActivity(intent)

            }
        }
    }

}
