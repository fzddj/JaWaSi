package com.makedream.jawasi.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ddj on 16-8-6.
 */
@Entity
public class Stock implements Parcelable {

    @Id
    private Long id;

    @Unique
    private String stockId;

    private String stockName;

    /**
     * 流通股本
     */
    private long stockAllNum;

    /**
     * 当前价格
     */
    private double currentPrice;

    /**
     * 计划卖出价格
     */
    private double planSalePrice;

    /**
     * 计划买入价格
     */
    private double planBuyPrice;

    /**
     * 计划补仓价格
     */
    private double nextPlanBuyPrice;

    /**
     * 市盈率
     */
    private double shiYingLv;

    /**
     * 市净率
     */
    private double shiJingLv;


    @Generated(hash = 1701416346)
    public Stock(Long id, String stockId, String stockName, long stockAllNum,
            double currentPrice, double planSalePrice, double planBuyPrice,
            double nextPlanBuyPrice, double shiYingLv, double shiJingLv) {
        this.id = id;
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockAllNum = stockAllNum;
        this.currentPrice = currentPrice;
        this.planSalePrice = planSalePrice;
        this.planBuyPrice = planBuyPrice;
        this.nextPlanBuyPrice = nextPlanBuyPrice;
        this.shiYingLv = shiYingLv;
        this.shiJingLv = shiJingLv;
    }

    @Generated(hash = 1902438397)
    public Stock() {
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

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public long getStockAllNum() {
        return stockAllNum;
    }

    public void setStockAllNum(long stockAllNum) {
        this.stockAllNum = stockAllNum;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getPlanSalePrice() {
        return planSalePrice;
    }

    public void setPlanSalePrice(double planSalePrice) {
        this.planSalePrice = planSalePrice;
    }

    public double getPlanBuyPrice() {
        return planBuyPrice;
    }

    public void setPlanBuyPrice(double planBuyPrice) {
        this.planBuyPrice = planBuyPrice;
    }

    public double getNextPlanBuyPrice() {
        return nextPlanBuyPrice;
    }

    public void setNextPlanBuyPrice(double nextPlanBuyPrice) {
        this.nextPlanBuyPrice = nextPlanBuyPrice;
    }

    public double getShiYingLv() {
        return shiYingLv;
    }

    public void setShiYingLv(double shiYingLv) {
        this.shiYingLv = shiYingLv;
    }

    public double getShiJingLv() {
        return shiJingLv;
    }

    public void setShiJingLv(double shiJingLv) {
        this.shiJingLv = shiJingLv;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.stockId);
        dest.writeString(this.stockName);
        dest.writeLong(this.stockAllNum);
        dest.writeDouble(this.currentPrice);
        dest.writeDouble(this.planSalePrice);
        dest.writeDouble(this.planBuyPrice);
        dest.writeDouble(this.nextPlanBuyPrice);
        dest.writeDouble(this.shiYingLv);
        dest.writeDouble(this.shiJingLv);
    }

    protected Stock(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.stockId = in.readString();
        this.stockName = in.readString();
        this.stockAllNum = in.readLong();
        this.currentPrice = in.readDouble();
        this.planSalePrice = in.readDouble();
        this.planBuyPrice = in.readDouble();
        this.nextPlanBuyPrice = in.readDouble();
        this.shiYingLv = in.readDouble();
        this.shiJingLv = in.readDouble();
    }

    public static final Creator<Stock> CREATOR = new Creator<Stock>() {
        @Override
        public Stock createFromParcel(Parcel source) {
            return new Stock(source);
        }

        @Override
        public Stock[] newArray(int size) {
            return new Stock[size];
        }
    };
}
