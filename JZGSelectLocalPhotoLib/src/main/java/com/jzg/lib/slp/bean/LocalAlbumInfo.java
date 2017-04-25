package com.jzg.lib.slp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 本地相册信息 <br/>
 * Created by lipanquan on 2017/4/24.<br />
 * phoneNumber:18500214652 <br />
 * email:lipq@jingzhengu.com <br />
 *
 * @author lipanquan   2017/4/24
 */
public class LocalAlbumInfo implements Parcelable {

    /**
     * 文件夹的第一张图片路径
     */
    private String topImagePath;

    /**
     * 文件夹名
     */
    private String folderName;

    /**
     * 文件夹中的图片数
     */
    private int imageCounts;

    public String getTopImagePath() {
        return topImagePath;
    }

    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImageCounts() {
        return imageCounts;
    }

    public void setImageCounts(int imageCounts) {
        this.imageCounts = imageCounts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.topImagePath);
        dest.writeString(this.folderName);
        dest.writeInt(this.imageCounts);
    }

    public LocalAlbumInfo() {
    }

    protected LocalAlbumInfo(Parcel in) {
        this.topImagePath = in.readString();
        this.folderName = in.readString();
        this.imageCounts = in.readInt();
    }

    public static final Creator<LocalAlbumInfo> CREATOR = new Creator<LocalAlbumInfo>() {
        @Override
        public LocalAlbumInfo createFromParcel(Parcel source) {
            return new LocalAlbumInfo(source);
        }

        @Override
        public LocalAlbumInfo[] newArray(int size) {
            return new LocalAlbumInfo[size];
        }
    };
}
