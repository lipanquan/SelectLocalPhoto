package com.jzg.lib.slp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.lib.slp.CofigSelectPhoto.ConfigSelectPhoto;
import com.jzg.lib.slp.R;
import com.jzg.lib.slp.adapter.LocalAlbumAdapter;
import com.jzg.lib.slp.adapter.SelectPhotoAdapter;
import com.jzg.lib.slp.bean.LocalAlbumInfo;
import com.jzg.lib.slp.inf.ICurrentSelectedCountChangeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 选择本地图片 <br/>
 * Created by lipanquan on 2017/4/24.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/24
 */
public class SelectPhotoActivity extends Activity implements ICurrentSelectedCountChangeListener,
        View.OnClickListener, AdapterView.OnItemClickListener {

    private final static int SCAN_OK = 1;
    private final static int SCAN_NO = 2;
    private final static int SCAN_NOT_STORAGE = 3;

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
     * 选择图片
     */
    private GridView gvSelectPhoto;

    /**
     * 选择相册列表
     */
    private ListView lvSelectAlbum;

    /**
     * 选择相册列表布局
     */
    private RelativeLayout rlSelectAlbum;

    /**
     * 选择相册按钮
     */
    private TextView tvSelectAlbum;

    /**
     * 确定选择的图片
     */
    private Button btnSelectOk;

    /**
     * 所有相册和所有相册对象照片的map
     */
    private static HashMap<String, List<String>> albumInfos;

    /**
     * 当前相册中本地照片信息集合
     */
    private List<LocalAlbumInfo> localImageInfos;

    /**
     * 当前选择的相册对象的GridView显示适配器
     */
    private SelectPhotoAdapter selectImageAdapter;

    /**
     * 标志当前是否是显示的是选择相册界面
     */
    private boolean isShowSelectAlbum = false;

    /**
     * 显示选择相册列表动画
     */
    private TranslateAnimation animationShowSelectAlbumList;

    /**
     * 关闭选择相册列表动画
     */
    private TranslateAnimation animationCloseSelectAlbumList;

    /**
     * 当前相册显示的图片列表
     */
    private List<String> showCurrentAlbumList = new ArrayList<String>();

    /**
     * 当前选中的照片路径集合
     */
    private List<String> currentSelectedPhotos = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    setTitle(localImageInfos.get(0).getFolderName());
                    lvSelectAlbum.setAdapter(new LocalAlbumAdapter(SelectPhotoActivity.this, localImageInfos));
                    List<String> childList = albumInfos.get(localImageInfos.get(0).getFolderName());
                    showCurrentAlbumList.clear();
                    showCurrentAlbumList.addAll(childList);
                    selectImageAdapter = new SelectPhotoAdapter(SelectPhotoActivity.this, currentSelectedPhotos, showCurrentAlbumList, SelectPhotoActivity.this, 0);
                    gvSelectPhoto.setAdapter(selectImageAdapter);
                    break;
                case SCAN_NO:
                    Toast.makeText(getApplicationContext(), "该相册中没有图片，请重新选择", Toast.LENGTH_SHORT).show();
                    break;
                case SCAN_NOT_STORAGE:
                    Toast.makeText(getApplicationContext(), "没有读写权限", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == this.lvSelectAlbum) { // 选择相册
            List<String> childList = this.albumInfos.get(this.localImageInfos.get(position).getFolderName());
            String childName = this.localImageInfos.get(position).getFolderName();
            this.showCurrentAlbumList.clear();
            this.showCurrentAlbumList.addAll(childList);
            tv_title.setText(childName);
            if (this.selectImageAdapter == null) {
                this.selectImageAdapter = new SelectPhotoAdapter(this, this.currentSelectedPhotos, this.showCurrentAlbumList, this, 0);
                this.gvSelectPhoto.setAdapter(selectImageAdapter);
            } else
                this.selectImageAdapter.notifyDataSetInvalidated();
            closeShowSelectAlbum();
        } else if (parent == this.gvSelectPhoto) { // 选择图片
            if (ConfigSelectPhoto.build().getSelectPhotoCount() == 1) {
                if (ConfigSelectPhoto.build().isSelectPhotoIsShowPreview()) { // 进入预览界面
                    Toast.makeText(this, "进入预览界面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ShowPreviewActivity.class);
                    // 0只预览 + 选择
                    intent.putExtra("flag", 1);
                    intent.putExtra("photoPath", this.showCurrentAlbumList.get(position));
                    startActivityForResult(intent, Integer.MAX_VALUE);
                    overridePendingTransition(R.anim.tran_next_enter, R.anim.tran_next_exit);
                } else {
                    this.currentSelectedPhotos.add(this.showCurrentAlbumList.get(position));
                    this.btnSelectOk.performClick();
                }
            } else {
                if (ConfigSelectPhoto.build().isSelectPhotoIsShowPreview()) { // 进入预览界面
                    Toast.makeText(this, "进入预览界面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ShowPreviewActivity.class);
                    // 0只预览
                    intent.putExtra("flag", 0);
                    intent.putExtra("photoPath", this.showCurrentAlbumList.get(position));
                    startActivityForResult(intent, Integer.MAX_VALUE);
                    overridePendingTransition(R.anim.tran_next_enter, R.anim.tran_next_exit);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Integer.MAX_VALUE && resultCode == RESULT_OK && data != null) {
            int flag = data.getIntExtra("flag", 0);
            if (flag == 0) {

            } else if (flag == 1) {
                this.currentSelectedPhotos.addAll(data.getStringArrayListExtra(ConfigSelectPhoto.build().getSelectPhotoExtraKey()));
                btnSelectOk.performClick();
            }
        }
    }

    /**
     * 初始化标题栏
     */
    @SuppressLint("NewApi")
    private void initTitle() {
        tv_left.setCompoundDrawablesWithIntrinsicBounds(ConfigSelectPhoto.build().getSelectPhotoTitleLeftTextSelector(), 0, 0, 0);
        tv_left.setTextColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleLeftTextColor()));
        tv_left.setText(getResources().getString(ConfigSelectPhoto.build().getSelectPhotoTitleLeftText()));
        tv_title.setTextColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleTextColor()));
        rlTitle.setBackgroundColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleBackgroundColor()));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rlTitle.getLayoutParams();
        layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ConfigSelectPhoto.build().getSelectPhotoTitleHeight(),
                getResources().getDisplayMetrics());
        rlTitle.setLayoutParams(layoutParams);
        rlTitle.setBackgroundColor(getResources().getColor(ConfigSelectPhoto.build().getSelectPhotoTitleBackgroundColor()));
        tv_right.setVisibility(View.GONE);

        btnSelectOk.setText(ConfigSelectPhoto.build().getSelectPhotoOkButtonText());
        if (ConfigSelectPhoto.build().getSelectPhotoCount() == 1) {
            btnSelectOk.setVisibility(View.GONE);
        }
        btnSelectOk.setBackgroundResource(ConfigSelectPhoto.build().getSelectPhotoOkButtonSelector());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_select_local_photo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();
        initTitle();
        initData();
        setListener();
        initAnimation();
    }

    private void setListener() {
        tv_left.setOnClickListener(this);
        rlSelectAlbum.setOnClickListener(this);
        tvSelectAlbum.setOnClickListener(this);
        btnSelectOk.setOnClickListener(this);
        lvSelectAlbum.setOnItemClickListener(this);
        gvSelectPhoto.setOnItemClickListener(this);
    }

    private void initView() {
        rlTitle = findViewById(R.id.rlTitle);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_right = (TextView) findViewById(R.id.tv_right);
        gvSelectPhoto = (GridView) findViewById(R.id.gvSelectPhoto);
        lvSelectAlbum = (ListView) findViewById(R.id.lvSelectAlbum);
        rlSelectAlbum = (RelativeLayout) findViewById(R.id.rlSelectAlbum);
        tvSelectAlbum = (TextView) findViewById(R.id.tvSelectAlbum);
        btnSelectOk = (Button) findViewById(R.id.btnSelectOk);
    }

    private void initData() {
        if (albumInfos != null) {
            albumInfos.clear();
        } else
            albumInfos = new HashMap<String, List<String>>();

        localImageInfos = new ArrayList<LocalAlbumInfo>();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mHandler.sendEmptyMessage(SCAN_NOT_STORAGE);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();
                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + " = ? or "
                                + MediaStore.Images.Media.MIME_TYPE + " = ? ",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();
                    //根据父路径名将图片放入到albumInfos中
                    if (!albumInfos.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<String>();
                        chileList.add(path);
                        albumInfos.put(parentName, chileList);
                    } else {
                        albumInfos.get(parentName).add(path);
                    }
                }
                mCursor.close();
                localImageInfos = subGroupOfImage(albumInfos);
                //通知Handler扫描图片完成
                if (localImageInfos == null || localImageInfos.size() == 0) {
                    mHandler.sendEmptyMessage(SCAN_NO);
                } else {
                    mHandler.sendEmptyMessage(SCAN_OK);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v == tv_left) { // 返回上一页
            if (isShowSelectAlbum) {
                closeShowSelectAlbum();
            } else {
                finishCurrentActivity();
            }
        } else if (v == tvSelectAlbum) {
            closeShowSelectAlbum();
        } else if (v == rlSelectAlbum) {
            closeShowSelectAlbum();
        } else if (v == btnSelectOk) {
            Intent intent = getIntent();
            intent.putStringArrayListExtra(ConfigSelectPhoto.build().getSelectPhotoExtraKey(), (ArrayList<String>) this.currentSelectedPhotos);
            setResult(RESULT_OK, intent);
            finishCurrentActivity();
        }
    }

    @Override
    public void onCountChange(int selectCountSum) {
        if (ConfigSelectPhoto.build().getSelectPhotoCount() == 1) {
            btnSelectOk.setVisibility(View.GONE);
        } else if (selectCountSum == 1) {
            btnSelectOk.setEnabled(true);
            btnSelectOk.setTextColor(getResources().getColor(R.color.black));
            btnSelectOk.setText(getResources().getString(ConfigSelectPhoto.build().getSelectPhotoOkButtonText())
                    + "(" + this.currentSelectedPhotos.size() + "/)" + ConfigSelectPhoto.build().getSelectPhotoCount());
        } else if (selectCountSum == 0) {
            btnSelectOk.setEnabled(false);
            btnSelectOk.setTextColor(getResources().getColor(R.color.disEnable_cccccc));
            btnSelectOk.setText(getResources().getString(ConfigSelectPhoto.build().getSelectPhotoOkButtonText()));
        }
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<LocalAlbumInfo> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<LocalAlbumInfo> list = new ArrayList<LocalAlbumInfo>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            LocalAlbumInfo mImageBean = new LocalAlbumInfo();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片
            list.add(mImageBean);
        }
        return list;
    }

    /**
     * 设置动画
     */
    private void initAnimation() {
        animationShowSelectAlbumList = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animationShowSelectAlbumList.setDuration(ConfigSelectPhoto.build().getSelectAlbumAnimationDuration());
        animationShowSelectAlbumList.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                rlSelectAlbum.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        });

        animationCloseSelectAlbumList = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        animationCloseSelectAlbumList.setDuration(ConfigSelectPhoto.build().getSelectAlbumAnimationDuration());
        animationCloseSelectAlbumList.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                rlSelectAlbum.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 关闭更多选项的Menu
     */
    private void closeShowSelectAlbum() {
        if (isShowSelectAlbum) {
            lvSelectAlbum.startAnimation(animationCloseSelectAlbumList);
            isShowSelectAlbum = false;
            tvSelectAlbum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.select_album_open, 0);
        } else {
            rlSelectAlbum.setVisibility(View.VISIBLE);
            lvSelectAlbum.startAnimation(animationShowSelectAlbumList);
            isShowSelectAlbum = true;
            tvSelectAlbum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.select_album_close, 0);
        }
    }

    public void finishCurrentActivity() {
        finish();
        ConfigSelectPhoto.build().getOnActivityFinishListener().onActivityFinish(this);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShowSelectAlbum) {
                closeShowSelectAlbum();
            } else {
                finishCurrentActivity();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.currentSelectedPhotos.clear();
    }
}
