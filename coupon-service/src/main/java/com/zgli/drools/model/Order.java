package com.zgli.drools.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String  oId;
    // 总金额
    private Double paidPrice;
    // 产品个数
    private  int quantity;
    // 优惠总金额
    private  double adjustmentTotal;
    // 订单调整详情,用以记录该订单所使用过的所有调整
    private List<OrderAdjustment> adjustmentList;
    // 订单中包含的单个产品列表
    private List<OrderItem> itemList;

    public Order() {
        this.adjustmentList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.oId = "1101";
        this.paidPrice = 299d;
        this.quantity = 1;
        this.adjustmentTotal = 0d;
    }

    public Order(String oId, Double totalFee, int quantity, double adjustmentTotal) {
        this.oId = oId;
        this.paidPrice = totalFee;
        this.quantity = quantity;
        this.adjustmentTotal = adjustmentTotal;
        this.adjustmentList = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Order{" +
                "oId='" + oId + '\'' +
                ", totalFee=" + paidPrice +
                ", quantity=" + quantity +
                "\n, adjustmentTotal=" + adjustmentTotal +
                "\n, adjustmentList=" + adjustmentList +
                "\n, itemList=" + itemList +
                '}';
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public Double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAdjustmentTotal() {
        return adjustmentTotal;
    }

    public void setAdjustmentTotal(double adjustmentTotal) {
        this.adjustmentTotal = adjustmentTotal;
    }

    public List<OrderAdjustment> getAdjustmentList() {
        return adjustmentList;
    }

    public void setAdjustmentList(List<OrderAdjustment> adjustmentList) {
        this.adjustmentList = adjustmentList;
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
        this.itemList = itemList;
    }
}
