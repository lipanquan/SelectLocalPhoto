package com.jzg.lib.slp.CofigSelectPhoto;

import android.app.Activity;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.jzg.lib.slp.R;
import com.jzg.lib.slp.inf.IOnActivityFinishListener;

/**
 * ConfigSelectPhoto配置选择本地图片参数类 <br/>
 * Created by lipanquan on 2017/4/24.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/24
 */
public final class ConfigSelectPhoto implements IOnActivityFinishListener {

    /**
     * 选择图片的标题背景颜色
     */
    private int selectPhotoTitleBackgroundColor = R.color.white;

    /**
     * 选择图片标题的高度（单位dp）
     */
    private int selectPhotoTitleHeight = 44;

    /**
     * 选择图片界面的状态栏颜色
     */
    private int selectPhotoStatusBarColor = R.color.black;

    /**
     * 标题栏标题文字的颜色
     */
    private int selectPhotoTitleTextColor = R.color.black;

    /**
     * 标题栏标题左边文字的颜色
     */
    private int selectPhotoTitleLeftTextColor = R.color.black;

    /**
     * 标题栏标题左边按钮的选择器（state_pressed标示）
     */
    private int selectPhotoTitleLeftTextSelector = R.drawable.selector_btn_back;

    /**
     * 标题栏标题左边文字
     */
    private int selectPhotoTitleLeftText = R.string.back;

    /**
     * 选择相册列表动画时长
     */
    private int selectAlbumAnimationDuration = 200;

    /**
     * 选择好照片的按钮的文字
     */
    private int selectPhotoOkButtonText = R.string.ok;

    /**
     * 选择好照片的按钮的选择器
     */
    private int selectPhotoOkButtonSelector = 0;

    /**
     * 选择照片预览时未加载时显示
     */
    private int selectPhotoListLoadDefaultImage = R.drawable.img_load_photo_default;

    /**
     * 选择照片标示选择状态（state_selected标示）
     */
    private int selectPhotoListSelector = R.drawable.selector_select_phone_states;

    /**
     * 最多选择本地图片的张数（>0 的值）
     */
    private int selectPhotoCount = 6;

    /**
     * 选择照片列表，点击某一项时是否进入预览图片（当前选择图片张数==1时，直接进入预览，确定后直接返回图片地址）
     */
    private boolean selectPhotoIsShowPreview = true;

    /**
     * 选择照片列表带回数据时的key值
     */
    private String selectPhotoExtraKey = "selectedImages";

    /**
     * 选择图片界面关闭时的回调（用于做关闭界面的动画处理）
     */
    private IOnActivityFinishListener onActivityFinishListener = this;

    private ConfigSelectPhoto() {
    }

    private static final ConfigSelectPhoto configSelectPhoto = new ConfigSelectPhoto();

    /**
     * 获取ConfigSelectPhoto对象
     *
     * @return ConfigSelectPhoto对象
     */
    public synchronized static ConfigSelectPhoto build() {
        return configSelectPhoto;
    }

    /**
     * 设置选择图片的标题背景颜色
     *
     * @param selectPhotoTitleBackgroundColor 选择图片的标题背景颜色
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleBackgroundColor(@ColorRes int selectPhotoTitleBackgroundColor) {
        this.selectPhotoTitleBackgroundColor = selectPhotoTitleBackgroundColor;
        return this;
    }

    /**
     * 设置选择图片界面的状态栏颜色
     *
     * @param selectPhotoStatusBarColor 选择图片界面的状态栏颜色
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoStatusBarColor(@ColorRes int selectPhotoStatusBarColor) {
        this.selectPhotoStatusBarColor = selectPhotoStatusBarColor;
        return this;
    }

    /**
     * 设置标题栏标题文字的颜色
     *
     * @param selectPhotoTitleTextColor 标题栏标题文字的颜色
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleTextColor(@ColorRes int selectPhotoTitleTextColor) {
        this.selectPhotoTitleTextColor = selectPhotoTitleTextColor;
        return this;
    }

    /**
     * 设置标题栏标题左边文字的颜色
     *
     * @param selectPhotoTitleLeftTextColor 标题栏标题左边文字的颜色
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleLeftTextColor(@ColorRes int selectPhotoTitleLeftTextColor) {
        this.selectPhotoTitleLeftTextColor = selectPhotoTitleLeftTextColor;
        return this;
    }

    /**
     * 设置选择照片标示选择状态（state_selected标示）
     *
     * @param selectPhotoListSelector 选择照片标示选择状态（state_selected标示）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoListSelector(@DrawableRes int selectPhotoListSelector) {
        this.selectPhotoListSelector = selectPhotoListSelector;
        return this;
    }

    /**
     * 设置标题栏标题左边文字
     *
     * @param selectPhotoTitleLeftText 标题栏标题左边文字
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleLeftText(@StringRes int selectPhotoTitleLeftText) {
        this.selectPhotoTitleLeftText = selectPhotoTitleLeftText;
        return this;
    }

    /**
     * 设置选择相册列表动画时长
     *
     * @param selectAlbumAnimationDuration 选择相册列表动画时长
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectAlbumAnimationDuration(int selectAlbumAnimationDuration) {
        this.selectAlbumAnimationDuration = selectAlbumAnimationDuration;
        return this;
    }

    /**
     * 设置选择照片预览时未加载时显示
     *
     * @param selectPhotoListLoadDefaultImage 选择照片预览时未加载时显示
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoListLoadDefaultImage(@DrawableRes int selectPhotoListLoadDefaultImage) {
        this.selectPhotoListLoadDefaultImage = selectPhotoListLoadDefaultImage;
        return this;
    }

    /**
     * 设置选择好照片的按钮的选择器
     *
     * @param selectPhotoOkButtonSelector 选择好照片的按钮的选择器
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoOkButtonSelector(@DrawableRes int selectPhotoOkButtonSelector) {
        this.selectPhotoOkButtonSelector = selectPhotoOkButtonSelector;
        return this;
    }

    /**
     * 设置选择照片列表，点击某一项时是否进入预览图片（当前选择图片张数==1时，直接进入预览，确定后直接返回图片地址）
     *
     * @param selectPhotoIsShowPreview 选择照片列表，点击某一项时是否进入预览图片（当前选择图片张数==1时，直接进入预览，确定后直接返回图片地址）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoIsShowPreview(boolean selectPhotoIsShowPreview) {
        this.selectPhotoIsShowPreview = selectPhotoIsShowPreview;
        return this;
    }

    /**
     * 设置选择照片列表带回数据时的key值
     *
     * @param selectPhotoExtraKey 选择照片列表带回数据时的key值
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoExtraKey(String selectPhotoExtraKey) {
        this.selectPhotoExtraKey = selectPhotoExtraKey;
        return this;
    }

    /**
     * 设置选择好照片的按钮的文字
     *
     * @param selectPhotoOkButtonText 选择好照片的按钮的文字
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoOkButtonText(@StringRes int selectPhotoOkButtonText) {
        this.selectPhotoOkButtonText = selectPhotoOkButtonText;
        return this;
    }

    /**
     * 设置最多选择本地图片的张数（>0 的值）
     *
     * @param selectPhotoCount 最多选择本地图片的张数（>0 的值）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoCount(int selectPhotoCount) {
        if (selectPhotoCount < 1)
            throw new RuntimeException("最多选择本地图片的张数（>0 的值）");
        this.selectPhotoCount = selectPhotoCount;
        return this;
    }

    /**
     * 设置标题栏标题左边按钮的选择器（state_pressed标示）
     *
     * @param selectPhotoTitleLeftTextSelector 标题栏标题左边按钮的选择器（state_pressed标示）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleLeftTextSelector(@DrawableRes int selectPhotoTitleLeftTextSelector) {
        this.selectPhotoTitleLeftTextSelector = selectPhotoTitleLeftTextSelector;
        return this;
    }

    /**
     * 设置选择图片标题的高度（单位dp）
     *
     * @param selectPhotoTitleHeight 选择图片标题的高度（单位dp）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setSelectPhotoTitleHeight(int selectPhotoTitleHeight) {
        if (selectPhotoCount < 1)
            throw new RuntimeException("选择本地图片的标题高度必须大于 33dp");
        this.selectPhotoTitleHeight = selectPhotoTitleHeight;
        return this;
    }

    /**
     * 设置选择图片界面关闭时的回调（用于做关闭界面的动画处理）
     *
     * @param onActivityFinishListener 选择图片界面关闭时的回调（用于做关闭界面的动画处理）
     * @return ConfigSelectPhoto
     */
    public ConfigSelectPhoto setOnActivityFinishListener(@NonNull IOnActivityFinishListener onActivityFinishListener) {
        this.onActivityFinishListener = onActivityFinishListener;
        return this;
    }

    /**
     * 获取选择好照片的按钮的选择器
     *
     * @return 选择好照片的按钮的选择器
     */
    public int getSelectPhotoOkButtonSelector() {
        return selectPhotoOkButtonSelector;
    }

    /**
     * 获取选择照片标示选择状态（state_selected标示）
     *
     * @return 选择照片标示选择状态（state_selected标示）
     */
    public int getSelectPhotoListSelector() {
        return selectPhotoListSelector;
    }

    /**
     * 获取选择照片列表，点击某一项时是否进入预览图片（当前选择图片张数==1时，直接进入预览，确定后直接返回图片地址）
     *
     * @return 选择照片列表，点击某一项时是否进入预览图片（当前选择图片张数==1时，直接进入预览，确定后直接返回图片地址）
     */
    public boolean isSelectPhotoIsShowPreview() {
        return selectPhotoIsShowPreview;
    }

    /**
     * 获取选择照片列表带回数据时的key值
     *
     * @return 选择照片列表带回数据时的key值
     */
    public String getSelectPhotoExtraKey() {
        return selectPhotoExtraKey;
    }

    /**
     * 获取标题栏标题左边按钮的选择器（state_pressed标示）
     *
     * @return 标题栏标题左边按钮的选择器（state_pressed标示）
     */
    public int getSelectPhotoTitleLeftTextSelector() {
        return selectPhotoTitleLeftTextSelector;
    }

    /**
     * 获取最多选择本地图片的张数（>0 的值）
     *
     * @return 最多选择本地图片的张数（>0 的值）
     */
    public int getSelectPhotoCount() {
        return selectPhotoCount;
    }

    /**
     * 获取选择好照片的按钮的文字
     *
     * @return 选择好照片的按钮的文字
     */
    public int getSelectPhotoOkButtonText() {
        return selectPhotoOkButtonText;
    }

    /**
     * 获取选择图片的标题背景颜色
     *
     * @return 选择图片的标题背景颜色
     */
    public int getSelectPhotoTitleBackgroundColor() {
        return selectPhotoTitleBackgroundColor;
    }

    /**
     * 获取标题栏标题文字的颜色
     *
     * @return 标题栏标题文字的颜色
     */
    public int getSelectPhotoTitleTextColor() {
        return selectPhotoTitleTextColor;
    }

    /**
     * 获取标题栏标题左边文字的颜色
     *
     * @return 标题栏标题左边文字的颜色
     */
    public int getSelectPhotoTitleLeftTextColor() {
        return selectPhotoTitleLeftTextColor;
    }

    /**
     * 获取选择图片标题的高度（单位dp）
     *
     * @return 选择图片标题的高度（单位dp）
     */
    public int getSelectPhotoTitleHeight() {
        return selectPhotoTitleHeight;
    }

    /**
     * 获取标题栏标题左边文字
     *
     * @return 标题栏标题左边文字
     */
    public int getSelectPhotoTitleLeftText() {
        return selectPhotoTitleLeftText;
    }

    /**
     * 获取选择图片界面的状态栏颜色
     *
     * @return 选择图片界面的状态栏颜色
     */
    public int getSelectPhotoStatusBarColor() {
        return selectPhotoStatusBarColor;
    }

    /**
     * 获取选择相册列表动画时长
     *
     * @return 选择相册列表动画时长
     */
    public int getSelectAlbumAnimationDuration() {
        return selectAlbumAnimationDuration;
    }

    /**
     * 获取选择照片预览时未加载时显示
     *
     * @return 选择照片预览时未加载时显示
     */
    public int getSelectPhotoListLoadDefaultImage() {
        return selectPhotoListLoadDefaultImage;
    }

    /**
     * 获取选择图片界面关闭时的回调（用于做关闭界面的动画处理）
     *
     * @return 选择图片界面关闭时的回调（用于做关闭界面的动画处理）
     */
    public IOnActivityFinishListener getOnActivityFinishListener() {
        return onActivityFinishListener;
    }

    @Override
    public void onActivityFinish(Activity activity) {
        activity.overridePendingTransition(R.anim.tran_last_enter, R.anim.tran_last_exit);
    }
}