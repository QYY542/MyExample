package com.myexample.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myexample.data.MyTarget.GList
import com.myexample.data.MyTarget.PersonList
import com.myexample.presentation.Diary.Mood

/*
  **Created by 24606 at 13:59 2022.
*/

class DetailTypeConverters {
    //PersonList
    @TypeConverter
    fun stringToPersonList(value: String?): PersonList? {
        val it = object : TypeToken<PersonList>() {}.type
        return Gson().fromJson(value, it)
    }

    @TypeConverter
    fun personListToString(value: PersonList?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    //GList
    @TypeConverter
    fun stringToGList(value: String?): GList? {
        val it = object : TypeToken<GList>() {}.type
        return Gson().fromJson(value, it)
    }

    @TypeConverter
    fun gListToString(value: GList?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    //mood

}
