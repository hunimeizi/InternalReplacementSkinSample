package com.haolin.internal.replacement.skin.library.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.haolin.internal.replacement.skin.library.R
import com.haolin.internal.replacement.skin.library.bean.AttrsBean
import com.haolin.internal.replacement.skin.library.core.ViewsMatch

class SkinnableRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), ViewsMatch {

    private val attrsBean: AttrsBean
    override fun skinnableView() {
        // 根据自定义属性，获取styleable中的background属性
        val key =
            R.styleable.SkinnableRelativeLayout[R.styleable.SkinnableRelativeLayout_android_background]
        // 根据styleable获取控件某属性的resourceId
        val backgroundResourceId = attrsBean.getViewResource(key)
        if (backgroundResourceId > 0) {
            // 兼容包转换
            val drawable = ContextCompat.getDrawable(context, backgroundResourceId)
            // 控件自带api，这里不用setBackgroundColor()因为在9.0测试不通过
            // setBackgroundDrawable在这里是过时了
            background = drawable
        }
    }

    init {
        attrsBean = AttrsBean()

        // 根据自定义属性，匹配控件属性的类型集合，如：background
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SkinnableRelativeLayout,
            defStyleAttr, 0
        )
        // 存储到临时JavaBean对象
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableRelativeLayout)
        // 这一句回收非常重要！obtainStyledAttributes()有语法提示！！
        typedArray.recycle()
    }
}