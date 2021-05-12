package com.haolin.internal.replacement.skin.library.core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatViewInflater
import com.haolin.internal.replacement.skin.library.views.*

class CustomAppCompatViewInflater(private var mContext: Context) : AppCompatViewInflater() {

    var name = "" // 控件名
        get() = field
        set(value) {
            field = value
        }

    var attrs: AttributeSet? = null
    get() = field
    set(value){field = value}

    /**
     * @return 自动匹配控件名，并初始化控件对象
     */
    fun autoMatch() : View?{
       return when(name){
           "LinearLayout" -> {
               SkinnableLinearLayout(mContext, attrs)
           }
           "RelativeLayout" -> {
               SkinnableRelativeLayout(mContext, attrs)
           }
           "TextView" -> {
               SkinnableTextView(mContext, attrs)
           }
           "ImageView" -> {
               SkinnableImageView(mContext, attrs)
           }
           "Button" -> {
               SkinnableButton(mContext, attrs)
           }
           else -> {
               null
           }
       }
    }

}
