package com.makedream.jawasi.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ddj on 16-8-7.
 */
@Entity
public class StockNote {

    @Id
    private Long id;

    @Unique
    private String stockId;

    /**
     * 股票笔记
     */
    private String stockContent;

    /**
     * 创建日期
     */
    private Date createDate;


    @Generated(hash = 1111340955)
    public StockNote(Long id, String stockId, String stockContent, Date createDate) {
        this.id = id;
        this.stockId = stockId;
        this.stockContent = stockContent;
        this.createDate = createDate;
    }

    @Generated(hash = 1231312583)
    public StockNote() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getStockContent() {
        return stockContent;
    }

    public void setStockContent(String stockContent) {
        this.stockContent = stockContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
