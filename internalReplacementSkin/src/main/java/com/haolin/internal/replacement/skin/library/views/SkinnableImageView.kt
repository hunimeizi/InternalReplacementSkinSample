package com.haolin.internal.replacement.skin.library.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.haolin.internal.replacement.skin.library.R
import com.haolin.internal.replacement.skin.library.bean.AttrsBean
import com.haolin.internal.replacement.skin.library.core.ViewsMatch

class SkinnableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ViewsMatch {

    private val attrsBean: AttrsBean = AttrsBean()

    override fun skinnableView() {
        // 根据自定义属性，获取styleable中的src属性
        val key = R.styleable.SkinnableImageView[R.styleable.SkinnableImageView_android_src]
        // 根据styleable获取控件某属性的resourceId
        val backgroundResourceId = attrsBean.getViewResource(key)
        if (backgroundResourceId > 0) {
            // 兼容包转换
            val drawable = ContextCompat.getDrawable(context, backgroundResourceId)
            setImageDrawable(drawable)
        }
    }

    init {

        // 根据自定义属性，匹配控件属性的类型集合，如：src
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SkinnableImageView,
            defStyleAttr, 0
        )
        // 存储到临时JavaBean对象
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableImageView)
        // 这一句回收非常重要！obtainStyledAttributes()有语法提示！！
        typedArray.recycle()
    }
}