package com.example.finalapp.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.finalapp.domain.model.User
import com.google.gson.Gson

object SharedPrefs {
    fun saveCurrentUser(user: User, activity: Activity) =
        activity.getSharedPreferences("MyUser", Context.MODE_PRIVATE)
            .edit().putString("1", Gson().toJson(user)).apply()

    fun getCurrentUser(activity: Context): User? {
        val pref: SharedPreferences? =
            activity.getSharedPreferences("MyUser", Context.MODE_PRIVATE)
        return Gson().fromJson(pref?.getString("1", null), User::class.java)
    }

    fun logOut(activity: Activity) =
        activity.getSharedPreferences("MyUser", Context.MODE_PRIVATE)
            .edit().clear().commit()
}