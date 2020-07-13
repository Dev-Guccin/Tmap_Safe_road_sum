package com.example.tsafe_load;

import android.os.Parcel;
import android.os.Parcelable;

public class location implements Parcelable {
    String latitude;
    String longitude;

    protected location(Parcel in) {
        latitude = in.readString();
        longitude = in.readString();
    }

    public location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public final Creator<location> CREATOR = new Creator<location>() {
        @Override
        public location createFromParcel(Parcel in) {
            return new location(in);
        }

        @Override
        public location[] newArray(int size) {
            return new location[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }
}
