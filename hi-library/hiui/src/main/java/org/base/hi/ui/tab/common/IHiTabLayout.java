package org.base.hi.ui.tab.common;

import android.view.ViewGroup;

import java.util.List;

/**
 * Author: xuan
 * Created on 2023/3/2 15:49.
 * <p>
 * Describe:
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {
    
    Tab findTab(D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelect(D defaultInfo);

    void inflateInfo(List<D> infoList);

    public interface OnTabSelectedListener<D> {
        
        void onTabSelectedChange(int index, D prevInfo, D nextInfo);
    }
}

