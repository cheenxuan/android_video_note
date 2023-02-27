package org.base.hi.library.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.base.hi.library.app.demo.HiLogDemoActivity

class MainActivity : Activity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_log_demo -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java))
            }
        }
    }
}