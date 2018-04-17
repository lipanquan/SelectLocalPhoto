# SelectLocalPhoto
选择本地图片的demo，功能请参考运行效果
标题栏标题左边文字的颜色
标题栏标题左边按钮的选择器（state_pressed标示）
选择相册列表动画时长
选择好照片的按钮的文字
选择好照片的按钮的选择器
选择照片预览时未加载时显示
选择照片标示选择状态（state_selected标示）
选择图片界面关闭时的回调（用于做关闭界面的动画处理）

具体使用参看com.jzg.slp.MainActivity
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

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
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
