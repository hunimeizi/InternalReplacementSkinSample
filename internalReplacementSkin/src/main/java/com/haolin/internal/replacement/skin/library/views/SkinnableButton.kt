package com.haolin.internal.replacement.skin.library.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.haolin.internal.replacement.skin.library.R
import com.haolin.internal.replacement.skin.library.bean.AttrsBean
import com.haolin.internal.replacement.skin.library.core.ViewsMatch

class SkinnableButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.buttonStyle
) : AppCompatButton(context, attrs, defStyleAttr), ViewsMatch {

    private val attrsBean: AttrsBean = AttrsBean()
    override fun skinnableView() {
        // 根据自定义属性，获取styleable中的background属性
        var key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background]
        // 根据styleable获取控件某属性的resourceId
        val backgroundResourceId = attrsBean.getViewResource(key)
        if (backgroundResourceId > 0) {
            // 兼容包转换
            val drawable = ContextCompat.getDrawable(context, backgroundResourceId)
            // 控件自带api，这里不用setBackgroundColor()因为在9.0测试不通过
            // setBackgroundDrawable本来过时了，但是兼容包重写了方法
            setBackgroundDrawable(drawable)
        }

        // 根据自定义属性，获取styleable中的textColor属性
        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_textColor]
        val textColorResourceId = attrsBean.getViewResource(key)
        if (textColorResourceId > 0) {
            val color = ContextCompat.getColorStateList(context, textColorResourceId)
            setTextColor(color)
        }
    }

    init {

        // 根据自定义属性，匹配控件属性的类型集合，如：background + textColor
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SkinnableButton,
            defStyleAttr, 0
        )
        // 存储到临时JavaBean对象
        attrsBean.saveViewResource(typedArray, R.styleable.SkinnableButton)
        // 这一句回收非常重要！obtainStyledAttributes()有语法提示！！
        typedArray.recycle()
    }
}