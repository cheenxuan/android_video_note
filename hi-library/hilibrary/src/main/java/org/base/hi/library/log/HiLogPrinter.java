package org.base.hi.library.log;

import androidx.annotation.NonNull;

/**
 * Author: xuan
 * Created on 2023/2/24 17:55.
 * <p>
 * Describe:
 */
public interface HiLogPrinter {
    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);
}
