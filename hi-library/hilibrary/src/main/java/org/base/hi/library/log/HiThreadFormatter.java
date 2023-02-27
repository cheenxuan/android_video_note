package org.base.hi.library.log;

/**
 * Author: xuan
 * Created on 2023/2/24 17:59.
 * <p>
 * Describe:
 */
public class HiThreadFormatter implements HiLogFormatter<Thread>{
    
    @Override
    public String format(Thread data) {
        return "Thread : " + data.getName();
    }
}
