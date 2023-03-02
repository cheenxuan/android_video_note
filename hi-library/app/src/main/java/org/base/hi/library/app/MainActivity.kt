package org.base.hi.library.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.base.hi.library.app.demo.HiLogDemoActivity
import org.base.hi.ui.tab.bottom.HiTabBottom
import org.base.hi.ui.tab.bottom.HiTabBottomInfo

class MainActivity : Activity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabBottom = findViewById<HiTabBottom>(R.id.main_tab_bottom)
        val homeInfo=HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        tabBottom.setHiTabInfo(homeInfo)
        

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_log_demo -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java))
            }
        }
    }
}