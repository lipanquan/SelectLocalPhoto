package com.jzg.slp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jzg.lib.slp.CofigSelectPhoto.ConfigSelectPhoto;
import com.jzg.lib.slp.activity.SelectPhotoActivity;
import com.jzg.lib.slp.inf.IOnActivityFinishListener;

import java.util.ArrayList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConfigSelectPhoto.build().setSelectAlbumAnimationDuration(200)
                .setSelectPhotoStatusBarColor(R.color.red)
                .setSelectPhotoTitleBackgroundColor(R.color.red)
                .setSelectPhotoTitleLeftText(R.string.back)
                .setSelectPhotoTitleLeftTextColor(R.color.white)
                .setSelectPhotoTitleTextColor(R.color.white)
                .setSelectPhotoListLoadDefaultImage(R.drawable.img_load_photo_default)
                .setSelectPhotoTitleHeight(44)
                .setSelectPhotoListSelector(R.drawable.selector_select_phone_states)
                .setSelectPhotoTitleLeftTextSelector(R.drawable.selector_title_back_btn)
                .setSelectPhotoOkButtonText(R.string.ok)
                .setSelectPhotoCount(2)
                .setSelectPhotoOkButtonSelector(R.drawable.selector_sure_select_photo)
                .setSelectPhotoIsShowPreview(true)
                .setSelectPhotoExtraKey("photos")
                .setOnActivityFinishListener(new IOnActivityFinishListener() {
                    @Override
                    public void onActivityFinish(Activity activity) {
                        activity.overridePendingTransition(com.jzg.lib.slp.R.anim.tran_last_enter, com.jzg.lib.slp.R.anim.tran_last_exit);
                    }
                });

        findViewById(R.id.btnSelectPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, SelectPhotoActivity.class), 101);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> phofos = data.getStringArrayListExtra(ConfigSelectPhoto.build().getSelectPhotoExtraKey());
            Toast.makeText(this, phofos.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
