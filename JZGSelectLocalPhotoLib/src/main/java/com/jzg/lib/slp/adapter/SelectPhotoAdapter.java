package com.jzg.lib.slp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jzg.lib.slp.CofigSelectPhoto.ConfigSelectPhoto;
import com.jzg.lib.slp.R;
import com.jzg.lib.slp.inf.ICurrentSelectedCountChangeListener;

import java.util.List;


/**
 * 选择本地图片列表适配器 <br/>
 * Created by lipanquan on 2017/4/24.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/24
 */
public class SelectPhotoAdapter extends BaseAdapter {
    /**
     * 上下文
     */
    private Context context;

    /**
     * 当前相册中的所有图片集合
     */
    private List<String> currentAlbumInPhotos;

    /**
     * 当前选择的图片个数发生变化
     */
    private ICurrentSelectedCountChangeListener currentSelectedCountChangeListener;

    /**
     * 当前显示的页码
     */
    private int currentShowPhotoPage;

    /**
     * 当前选中的照片集合
     */
    private List<String> currentSelectPhotoLists;

    /**
     * 选择本地图片列表适配器
     *
     * @param context                            上下文
     * @param currentSelectPhotoLists            当前相册中的所有图片集合
     * @param currentAlbumInPhotos               当前选中的照片集合
     * @param currentSelectedCountChangeListener 当前选择的图片个数发生变化
     * @param currentShowPhotoPage               当前显示的页码
     */
    public SelectPhotoAdapter(Context context, List<String> currentSelectPhotoLists, List<String> currentAlbumInPhotos,
                              ICurrentSelectedCountChangeListener currentSelectedCountChangeListener, int currentShowPhotoPage) {
        this.context = context;
        this.currentSelectPhotoLists = currentSelectPhotoLists;
        this.currentAlbumInPhotos = currentAlbumInPhotos;
        this.currentSelectedCountChangeListener = currentSelectedCountChangeListener;
        this.currentShowPhotoPage = currentShowPhotoPage;

    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.currentShowPhotoPage = 0;
    }

    @Override
    public int getCount() {
        return currentAlbumInPhotos.size();
    }

    @Override
    public Object getItem(int position) {

        return currentAlbumInPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(this.context, R.layout.adapter_select_image_item, null);
            holder.rlSelectPhoto = (RelativeLayout) convertView.findViewById(R.id.rlSelectPhoto);
            holder.ivShowSelectPhoto = (ImageView) convertView.findViewById(R.id.ivShowSelectPhoto);
            holder.ivStatesSelectPhoto = (ImageView) convertView.findViewById(R.id.ivStatesSelectPhoto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load("file://" + currentAlbumInPhotos.get(position))
                .dontAnimate()
                .placeholder(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .error(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .into(holder.ivShowSelectPhoto);
        holder.ivShowSelectPhoto.setColorFilter(null);
        holder.ivStatesSelectPhoto.setColorFilter(null);
        if (currentShowPhotoPage == 1) {
            //设置id_item_rl的点击事件
            holder.rlSelectPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 已经选择过该图片
                    if (currentSelectPhotoLists.contains(currentAlbumInPhotos.get(position))) {
                        currentSelectPhotoLists.remove(currentAlbumInPhotos.get(position));
                        holder.ivStatesSelectPhoto.setSelected(false);
                        holder.ivShowSelectPhoto.setColorFilter(null);
                    } else { // 未选择该图片
                        currentSelectPhotoLists.add(currentAlbumInPhotos.get(position));
                        holder.ivStatesSelectPhoto.setSelected(true);
                        holder.ivShowSelectPhoto.setColorFilter(Color.parseColor("#77000000"));
                    }
                    if (currentSelectPhotoLists.size() > 0) {
                        currentSelectedCountChangeListener.onCountChange(1);
                    } else {
                        currentSelectedCountChangeListener.onCountChange(0);
                    }
                }
            });
        } else {
            //设置id_item_select的点击事件
            holder.ivStatesSelectPhoto.setOnClickListener(new View.OnClickListener() {
                //选择，则将图片变暗，反之则反之
                @Override
                public void onClick(View v) {
                    // 已经选择过该图片
                    if (currentSelectPhotoLists.contains(currentAlbumInPhotos.get(position))) {
                        currentSelectPhotoLists.remove(currentAlbumInPhotos.get(position));
                        holder.ivStatesSelectPhoto.setSelected(false);
                        holder.ivShowSelectPhoto.setColorFilter(null);
                    } else { // 未选择该图片
                        if (currentSelectPhotoLists.size() < ConfigSelectPhoto.build().getSelectPhotoCount()) {
                            currentSelectPhotoLists.add(currentAlbumInPhotos.get(position));
                            holder.ivStatesSelectPhoto.setSelected(true);
                            holder.ivShowSelectPhoto.setColorFilter(Color.parseColor("#77000000"));
                        } else {
                            Toast.makeText(context, "您最多可以选择" + ConfigSelectPhoto.build().getSelectPhotoCount() + "张", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (currentSelectPhotoLists.size() > 0) {
                        currentSelectedCountChangeListener.onCountChange(1);
                    } else {
                        currentSelectedCountChangeListener.onCountChange(0);
                    }
                }
            });
        }
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (ConfigSelectPhoto.build().getSelectPhotoCount() == 1)
            holder.ivStatesSelectPhoto.setVisibility(View.GONE);
        holder.ivStatesSelectPhoto.setImageResource(ConfigSelectPhoto.build().getSelectPhotoListSelector());
        if (currentSelectPhotoLists.contains(currentAlbumInPhotos.get(position))) {
            holder.ivStatesSelectPhoto.setSelected(true);
            holder.ivShowSelectPhoto.setColorFilter(Color.parseColor("#77000000"));
        } else {
            holder.ivShowSelectPhoto.setColorFilter(null);
            holder.ivStatesSelectPhoto.setSelected(false);
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout rlSelectPhoto;
        ImageView ivShowSelectPhoto;
        ImageView ivStatesSelectPhoto;
    }
}
