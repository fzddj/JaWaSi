package com.makedream.jawasi.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by ddj on 16-7-16.
 */
@Entity
public class ExeciseItem {

    @Id
    private Long id;

    private int type;

    private Date createDate;

    private int num;

    @Unique
    private String dateKey;

    @Generated(hash = 1985330060)
    public ExeciseItem(Long id, int type, Date createDate, int num, String dateKey) {
        this.id = id;
        this.type = type;
        this.createDate = createDate;
        this.num = num;
        this.dateKey = dateKey;
    }

    @Generated(hash = 1373108124)
    public ExeciseItem() {
    }

    public Long getId() {
        return id;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
