package com.zgli.drools.model;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    public OrderItem() {
        this.adjustmentTotalPrice = 0d;
        this.adjustmentList = new ArrayList<>();
    }

    public OrderItem(int seqId, int oId, Double paidPrice, Double price,  String pId, String skuId, int quantity) {
        this.seqId = seqId;
        this.oId = oId;
        this.paidPrice = paidPrice;
        this.price = price;
        this.totalPrice = price * quantity;
        this.pId = pId;
        this.skuId = skuId;
        this.quantity = quantity;
        this.adjustmentTotalPrice = 0d;
        this.adjustmentList = new ArrayList<>();
    }

     // 品类
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    //组织id，即店铺id
    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    // 订单条目的 顺序号
    private  int seqId;
    // 订单号
    private  int oId;
    // 实际付款金额
    private Double  paidPrice;
    //单价
    private Double  price;
    // 总金额
    private Double  totalPrice;
    // 产品id
    private  String  pId;
    // skuid
    private  String  skuId;
    // 产品个数
    private  int quantity;
    // 优惠总金额
    private  double adjustmentTotalPrice;
    // 订单调整详情
    private List<OrderAdjustment> adjustmentList;

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public int getoId() {
        return oId;
    }

    public void setoId(int oId) {
        this.oId = oId;
    }

    public Double getPaidPrice() {
        return paidPrice;
    }

    public void setPaidPrice(Double paidPrice) {
        this.paidPrice = paidPrice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAdjustmentTotalPrice() {
        return adjustmentTotalPrice;
    }

    public void setAdjustmentTotalPrice(double adjustmentTotalPrice) {
        this.adjustmentTotalPrice = adjustmentTotalPrice;
    }

    public List<OrderAdjustment> getAdjustmentList() {
        return adjustmentList;
    }

    public void setAdjustmentList(List<OrderAdjustment> adjustmentList) {
        this.adjustmentList = adjustmentList;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "seqId=" + seqId +
                ", oId=" + oId +
                ", paidPrice=" + paidPrice +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", pId='" + pId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", quantity=" + quantity +
                "\n, adjustmentTotalPrice=" + adjustmentTotalPrice +
                "\n, adjustmentList=" + adjustmentList +
                '}';
    }
}
