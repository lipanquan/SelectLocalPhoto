package com.jzg.lib.slp.inf;

import android.app.Activity;

/**
 * 当选择图片界面关闭时回调的接口 <br/>
 * Created by lipanquan on 2017/4/25.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/25
 */
public interface IOnActivityFinishListener {
    /**
     * 界面关闭时回调的接口
     *
     * @param activity 选择图片界面
     */
    void onActivityFinish(Activity activity);
}
