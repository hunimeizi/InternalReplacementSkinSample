package com.haolin.internal.replacement.skin.library

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.core.view.LayoutInflaterCompat
import com.haolin.internal.replacement.skin.library.core.CustomAppCompatViewInflater
import com.haolin.internal.replacement.skin.library.core.ViewsMatch
import com.haolin.internal.replacement.skin.library.utils.ActionBarUtils
import com.haolin.internal.replacement.skin.library.utils.NavigationUtils
import com.haolin.internal.replacement.skin.library.utils.StatusBarUtils


open class SkinActivity : AppCompatActivity() {

    private var viewInflater: CustomAppCompatViewInflater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        if (openChangeSkin()) {
            if (viewInflater == null) {
                viewInflater = CustomAppCompatViewInflater(context);
            }
            viewInflater!!.name = name
            viewInflater!!.attrs = attrs
            return viewInflater!!.autoMatch()
        }
        return super.onCreateView(parent, name, context, attrs)
    }

    /**
     * @return 是否开启换肤，增加此开关是为了避免开发者误继承此父类，导致未知bug
     */
    protected open fun openChangeSkin(): Boolean {
        return false
    }

    protected fun setDayNightMode(@NightMode nightMode: Int) {
        val isPost21 = Build.VERSION.SDK_INT >= 21
        delegate.localNightMode = nightMode
        if (isPost21) {
            // 换状态栏
            StatusBarUtils.forStatusBar(this)
            // 换标题栏
            ActionBarUtils.forActionBar(this)
            // 换底部导航栏
            NavigationUtils.forNavigation(this)
        }
        val decorView = window.decorView
        applyDayNightForView(decorView)
    }

    /**
     * 回调接口 给具体控件换肤操作
     */
    private fun applyDayNightForView(view: View?) {
        if (view is ViewsMatch) {
            val viewsMatch = view as ViewsMatch
            viewsMatch.skinnableView()
        }
        if (view is ViewGroup) {
            val parent = view
            val childCount = parent.childCount
            for (i in 0 until childCount) {
                applyDayNightForView(parent.getChildAt(i))
            }
        }
    }

}