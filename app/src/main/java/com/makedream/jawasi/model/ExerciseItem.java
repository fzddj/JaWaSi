package com.makedream.jawasi.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;

/**
 * Created by ddj on 16-7-16.
 */
@Entity
public class ExerciseItem implements Parcelable {

    @Id
    private Long id;

    //类型
    private int type;

    //名称
    private String typeName;

    //每次时间或个数
    private int perNum;

    //总数
    private long totalNum;

    //创建时间
    private Date createDate;

    //进行的天数
    private int days;

    @Transient
    private boolean complete;

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public int getPerNum() {
        return this.perNum;
    }

    public void setPerNum(int perNum) {
        this.perNum = perNum;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Generated(hash = 1724136666)
    public ExerciseItem(Long id, int type, String typeName, int perNum, long totalNum,
            Date createDate, int days) {
        this.id = id;
        this.type = type;
        this.typeName = typeName;
        this.perNum = perNum;
        this.totalNum = totalNum;
        this.createDate = createDate;
        this.days = days;
    }

    @Generated(hash = 780870238)
    public ExerciseItem() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.typeName);
        dest.writeInt(this.perNum);
        dest.writeLong(this.totalNum);
        dest.writeLong(this.createDate != null ? this.createDate.getTime() : -1);
        dest.writeInt(this.days);
        dest.writeByte(this.complete ? (byte) 1 : (byte) 0);
    }

    protected ExerciseItem(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.type = in.readInt();
        this.typeName = in.readString();
        this.perNum = in.readInt();
        this.totalNum = in.readLong();
        long tmpCreateDate = in.readLong();
        this.createDate = tmpCreateDate == -1 ? null : new Date(tmpCreateDate);
        this.days = in.readInt();
        this.complete = in.readByte() != 0;
    }

    public static final Creator<ExerciseItem> CREATOR = new Creator<ExerciseItem>() {
        @Override
        public ExerciseItem createFromParcel(Parcel source) {
            return new ExerciseItem(source);
        }

        @Override
        public ExerciseItem[] newArray(int size) {
            return new ExerciseItem[size];
        }
    };
}
