package org.base.hi.library.log;

import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import static org.base.hi.library.log.HiLogConfig.MAX_LEN;

/**
 * Author: xuan
 * Created on 2023/2/27 15:32.
 * <p>
 * Describe: console printer
 * 
 */
public class HiConsolePrinter implements HiLogPrinter {
    @Override
    public void print(@NonNull @NotNull HiLogConfig config, int level, String tag, @NonNull @NotNull String printString) {

        int len = printString.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }

            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }

        } else {
            Log.println(level, tag, printString);
        }
    }
}
