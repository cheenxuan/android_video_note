package org.base.hi.library.log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author: xuan
 * Created on 2023/2/28 13:56.
 * <p>
 * Describe:
 */
public class HiLogMo {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String flattenedLog() {
        return getFlattened() + "\n" + log;
    }

    public String getFlattened() {
        return format(timeMillis) + " " + getLogLevel(level) + '/' + tag + ":";
    }

    private String format(long timeMillis) {
        return sdf.format(timeMillis);
    }

    public String getLogLevel(@NonNull int level) {
        String leveStr = "";
        switch (level) {
            case HiLogType.V:
                leveStr = "V";
                break;
            case HiLogType.D:
                leveStr = "D";
                break;
            case HiLogType.E:
                leveStr = "E";
                break;
            case HiLogType.W:
                leveStr = "W";
                break;
            case HiLogType.I:
                leveStr = "I";
                break;
            case HiLogType.A:
                leveStr = "A";
                break;
        }
        return leveStr;
    }
}
