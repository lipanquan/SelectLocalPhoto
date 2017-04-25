package com.jzg.lib.slp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzg.lib.slp.CofigSelectPhoto.ConfigSelectPhoto;
import com.jzg.lib.slp.R;
import com.jzg.lib.slp.view.TouchImageView;

import java.util.ArrayList;

/**
 * 预览大图 <br/>
 * Created by lipanquan on 2017/4/25.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/25
 */
public class ShowPreviewActivity extends Activity implements View.OnClickListener {

    /**
     * 标题栏
     */
    private View rlTitle;

    /**
     * 标题栏返回按钮
     */
    private TextView tv_left;

    /**
     * 标题栏标题
     */
    private TextView tv_title;

    /**
     * 标题栏右边按钮
     */
    private TextView tv_right;

    /**
     * 显示预览照片
     */
    private TouchImageView timSelectPhoto;

    /**
     * 当前显示图片的路径
     */
    private String photoPath;

    /**
     * 0标示查看， 1 标示选择
     */
    private int flag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            // 通知栏所需颜色
            tintManager.setStatusBarTintResource(ConfigSelectPhoto.build().getSelectPhotoStatusBarColor());// 通知栏所需颜色
        }
        setContentView(R.layout.activity_show_preview_photo);
        initView();
        initData();
        initTitle();
        setListener();
    }

    private void setListener() {
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    private void initData() {
        photoPath = getIntent().getStringExtra("photoPath");
        flag = getIntent().getIntExtra("flag", 0);
        Glide.with(this)
                .load("file://" + photoPath)
                .dontAnimate()
                .placeholder(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .error(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .into(timSelectPhoto);
    }

    private void initTitle() {
        tv_left.setCompoundDrawablesWithIntrinsicBounds(ConfigSelectPhoto.build().getSelectPhotoTitleLeftTextSelector(), 0, 0, 0);
        tv_left.setTextColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleLeftTextColor()));
        tv_left.setText(getResources().getString(ConfigSelectPhoto.build().getSelectPhotoTitleLeftText()));
        tv_title.setTextColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleTextColor()));
        rlTitle.setBackgroundColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleBackgroundColor()));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlTitle.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ConfigSelectPhoto.build().getSelectPhotoTitleHeight(),
                getResources().getDisplayMetrics());
        rlTitle.setLayoutParams(layoutParams);
        rlTitle.setBackgroundColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleBackgroundColor()));
        tv_right.setTextColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleLeftTextColor()));
        tv_title.setText("预览");
        tv_right.setVisibility(flag == 1 ? View.VISIBLE : View.GONE);
        tv_right.setText(R.string.ok);
    }

    private void initView() {
        rlTitle = findViewById(R.id.rlTitle);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        timSelectPhoto = (TouchImageView) findViewById(R.id.timSelectPhoto);
    }

    public void finishCurrentActivity() {
        finish();
        ConfigSelectPhoto.build().getOnActivityFinishListener().onActivityFinish(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_right) { // 确定
            Intent intent = new Intent();
            intent.putExtra("flag", flag);
            if (flag == 1) {
                ArrayList<String> photoPaths = new ArrayList<String>(1);
                photoPaths.add(photoPath);
                intent.putExtra(ConfigSelectPhoto.build().getSelectPhotoExtraKey(), photoPaths);
            }
            setResult(RESULT_OK, intent);
            finishCurrentActivity();
        } else if (v == tv_left) {
            finishCurrentActivity();
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishCurrentActivity();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
