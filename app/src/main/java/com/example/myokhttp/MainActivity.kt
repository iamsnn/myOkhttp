package com.example.myokhttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

        }

        fetchJSON()

    }

    private fun fetchJSON() {
        //val url = "http://192.168.0.150:8090/demo/users"
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object:Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Wrong")
            }
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body,HomeFeed::class.java)

                runOnUiThread {
                    recyclerView.adapter = MyAdapter(homeFeed)
                }

            }

        })
    }

}