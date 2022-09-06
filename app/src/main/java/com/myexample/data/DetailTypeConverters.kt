package com.myexample.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
  **Created by 24606 at 11:33 2022.
*/

//由于数据库无法添加除了规定类型外的所有类型,要写一个转化器来指导数据库进行复杂数据的转化
//class DetailTypeConverters {
//    @TypeConverter
//    fun stringToObject(value: String?): DetailType? {
//        val it = object : TypeToken<DetailType>() {}.type
//        return Gson().fromJson(value, it)
//    }
//
//    @TypeConverter
//    fun objectToString(value: DetailType?): String? {
//        val gson = Gson()
//        return gson.toJson(value)
//    }
//}