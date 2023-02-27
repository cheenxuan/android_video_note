package org.base.hi.library.app

import android.app.Application
import com.google.gson.Gson
import org.base.hi.library.log.HiConsolePrinter
import org.base.hi.library.log.HiLogConfig
import org.base.hi.library.log.HiLogConfig.JsonParser
import org.base.hi.library.log.HiLogManager

/**
 * Author: xuan
 * Created on 2023/2/24 17:11.
 *
 * Describe:
 */
class MApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {

            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalTag(): String {
                return "MyApplication"
            }

            override fun enable(): Boolean {
                return true
            }
        }, HiConsolePrinter())
    }

}