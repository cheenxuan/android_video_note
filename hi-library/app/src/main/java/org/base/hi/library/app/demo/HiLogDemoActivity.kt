package org.base.hi.library.app.demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.base.hi.library.app.R
import org.base.hi.library.log.*

class HiLogDemoActivity : AppCompatActivity() {

    private var viewPrinter: HiViewPrinter? = null
    private var filePrinter: HiFilePrinter? = null
    private var logPath: String? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_log_demo)
        viewPrinter = HiViewPrinter(this)
        logPath = this.getFilesDir().getAbsolutePath() + "/log"
        filePrinter = HiFilePrinter.getInstance(logPath,20000L)
        findViewById<Button>(R.id.btn_print_log).setOnClickListener {
            printLog()
        }
        viewPrinter!!.viewProvider.showFloatingView()

        HiLogManager.getInstance().addPrinter(viewPrinter)
        HiLogManager.getInstance().addPrinter(filePrinter)
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

        HiLog.d("9900")
    }
}