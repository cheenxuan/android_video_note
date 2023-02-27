package org.base.hi.library.log;

/**
 * Author: xuan
 * Created on 2023/2/24 17:58.
 * <p>
 * Describe:
 */
public interface HiLogFormatter<T> {
    String format(T data);
}
