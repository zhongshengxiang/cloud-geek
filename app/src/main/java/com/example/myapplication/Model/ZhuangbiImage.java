// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.myapplication.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ZhuangbiImage implements Parcelable {
    public String description;
    public String image_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.image_url);
    }

    public ZhuangbiImage() {
    }

    protected ZhuangbiImage(Parcel in) {
        this.description = in.readString();
        this.image_url = in.readString();
    }

    public static final Parcelable.Creator<ZhuangbiImage> CREATOR = new Parcelable.Creator<ZhuangbiImage>() {
        @Override
        public ZhuangbiImage createFromParcel(Parcel source) {
            return new ZhuangbiImage(source);
        }

        @Override
        public ZhuangbiImage[] newArray(int size) {
            return new ZhuangbiImage[size];
        }
    };
}
