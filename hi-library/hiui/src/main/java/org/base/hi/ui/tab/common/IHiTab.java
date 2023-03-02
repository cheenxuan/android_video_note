package org.base.hi.ui.tab.common;

import androidx.annotation.Px;

/**
 * Author: xuan
 * Created on 2023/3/2 15:48.
 * <p>
 * Describe:
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    
    void setHiTabInfo(D data);

    /**
     * Dynamically modify the height of an item
     * @param height
     */
    void resetHeight(@Px int height);

    /**
     * Dynamically modify the width of an item
     * @param width
     */
    void resetWidth(@Px int width);
}
