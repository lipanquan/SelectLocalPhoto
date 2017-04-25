package com.jzg.lib.slp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jzg.lib.slp.CofigSelectPhoto.ConfigSelectPhoto;
import com.jzg.lib.slp.R;
import com.jzg.lib.slp.bean.LocalAlbumInfo;

import java.util.List;


/**
 * 本地相册列表适配器 <br/>
 * Created by lipanquan on 2017/4/24.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/24
 */
public class LocalAlbumAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private Context context;

    /**
     * 本地相册信息列表
     */
    protected List<LocalAlbumInfo> localAlbumList;

    /**
     * 本地相册列表适配器构造
     *
     * @param mContext        上下文
     * @param mLocalImageList 本地相册信息列表
     */
    public LocalAlbumAdapter(Context mContext, List<LocalAlbumInfo> mLocalImageList) {
        this.context = mContext;
        this.localAlbumList = mLocalImageList;
    }

    @Override
    public int getCount() {
        return localAlbumList.size();
    }

    @Override
    public Object getItem(int position) {
        return localAlbumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(this.context, R.layout.adapter_local_image_item, null);
            holder.ivLocalImageIcon = (ImageView) convertView.findViewById(R.id.ivLocalImageIcon);
            holder.tvLocalImageName = (TextView) convertView.findViewById(R.id.tvLocalImageName);
            holder.tvLocalImageNumber = (TextView) convertView.findViewById(R.id.tvLocalImageNumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load("file://" + localAlbumList.get(position).getTopImagePath())
                .dontAnimate()
                .placeholder(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .error(ConfigSelectPhoto.build().getSelectPhotoListLoadDefaultImage())
                .into(holder.ivLocalImageIcon);
        holder.tvLocalImageName.setText(localAlbumList.get(position).getFolderName().trim());
        holder.tvLocalImageNumber.setText(Integer.toString(localAlbumList.get(position).getImageCounts()) + "张");
        return convertView;
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        /**
         * 相册的第一张图片
         */
        ImageView ivLocalImageIcon;

        /**
         * 相册名
         */
        TextView tvLocalImageName;

        /**
         * 相册中照片的个数
         */
        TextView tvLocalImageNumber;
    }
}
