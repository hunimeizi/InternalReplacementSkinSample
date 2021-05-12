package com.haolin.internal.replacement.skin.sample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.haolin.internal.replacement.skin.library.SkinActivity


class MainActivity : SkinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(if (getBoolean("isSystem",true))
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM else AppCompatDelegate.MODE_NIGHT_NO)
        findViewById<Button>(R.id.btnDayOrNight).setOnClickListener {
            dayOrNight()
        }
        findViewById<Button>(R.id.btnSystem).setOnClickListener {
            putBoolean("isSystem",true)
            restartApp()
        }
    }

    fun dayOrNight(){
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                setDayNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            else -> {
            }
        }

    }

    override fun openChangeSkin(): Boolean {
        return !getBoolean("isSystem",true)
    }

    fun putBoolean(key: String?, value: Boolean): Boolean {
        val settings: SharedPreferences =
            getSharedPreferences(this.packageName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        val settings = getSharedPreferences(this.packageName, MODE_PRIVATE)
        return settings.getBoolean(key, defaultValue)
    }

    fun restartApp(){
        val intent = packageManager.getLaunchIntentForPackage(packageName);
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
        //杀掉以前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}