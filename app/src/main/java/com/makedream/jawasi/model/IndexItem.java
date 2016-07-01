package com.makedream.jawasi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/30.
 */

public class IndexItem implements Parcelable {

    //买入价格
    private String Buy;

    private String Code;

    private String Decimal;


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDecimal() {
        return Decimal;
    }

    public void setDecimal(String decimal) {
        Decimal = decimal;
    }

    public String getBuy() {
        return Buy;
    }

    public void setBuy(String buy) {
        Buy = buy;
    }



    @Override
    public String toString() {
        return "IndexItem{" +
                "Buy='" + Buy + '\'' +
                ", Code='" + Code + '\'' +
                ", Decimal='" + Decimal + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Buy);
        dest.writeString(this.Code);
        dest.writeString(this.Decimal);
    }

    public IndexItem() {
    }

    protected IndexItem(Parcel in) {
        this.Buy = in.readString();
        this.Code = in.readString();
        this.Decimal = in.readString();
    }

    public static final Creator<IndexItem> CREATOR = new Creator<IndexItem>() {
        @Override
        public IndexItem createFromParcel(Parcel source) {
            return new IndexItem(source);
        }

        @Override
        public IndexItem[] newArray(int size) {
            return new IndexItem[size];
        }
    };
}