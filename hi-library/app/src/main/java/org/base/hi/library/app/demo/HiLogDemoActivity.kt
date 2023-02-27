package org.base.hi.library.app.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.base.hi.library.app.R
import org.base.hi.library.log.HiLog
import org.base.hi.library.log.HiLogConfig
import org.base.hi.library.log.HiLogType

class HiLogDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)

        findViewById<Button>(R.id.btn_print_log).setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {

        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, HiLogType.E, "------", "5566")

        HiLog.a("9900")
    }
}