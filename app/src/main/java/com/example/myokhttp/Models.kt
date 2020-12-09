package com.example.myokhttp

class HomeFeed(val videos:List<Video>)
class Video(val id:Int,val name:String, val link:String,
            val imageUrl:String,
            val channel: Channel)
class Channel(val name:String, val profileImageUrl:String)

class SecondFeed(val name: String,val duration: String,val imageUrl: String,val link: String)
