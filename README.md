### 内置换肤
```kotlin
class MainActivity : SkinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        findViewById<Button>(R.id.btnDayOrNight).setOnClickListener {
            dayOrNight()
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
        return true
    }
}

```

##### 自定义了一些View 还不够完善 待更新