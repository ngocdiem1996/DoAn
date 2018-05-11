package com.pdtrong.final_android.UserDatabase;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable{
    private String fullname;
    private String birthday;
    private String phone;
    private String username;
    private String password;

    public UserInfo(String username, String password, String fullname, String birthday, String phone) {
        this.fullname = fullname;
        this.birthday = birthday;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname( String fullname ) {
        this.fullname = fullname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday( String birthday ) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public UserInfo(Parcel parcel){
        this.username = parcel.readString();
        this.password = parcel.readString();
        this.fullname = parcel.readString();
        this.birthday = parcel.readString();
        this.phone = parcel.readString();
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.fullname);
        dest.writeString(this.birthday);
        dest.writeString(this.phone);
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel( Parcel source ) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray( int size ) {
            return new UserInfo[size];
        }
    };

}
