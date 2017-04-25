package com.jzg.lib.slp.inf;

/**
 * 当前选择的图片个数发生变化 <br/>
 * Created by lipanquan on 2017/4/25.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/25
 */
public interface ICurrentSelectedCountChangeListener {

    /**
     * 当前选择的图片个数发生变化
     *
     * @param selectCountSum 选择的图片总张数
     */
    void onCountChange(int selectCountSum);
}