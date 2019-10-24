package com.zgli.drools.model;

public class OrderAdjustment {
    public OrderAdjustment(int type, double discount, double percent, String ruleName, String oid, int seqId) {
        this.type = type;
        this.discount = discount;
        this.percent = percent;
        this.ruleName = ruleName;
        this.seqId = seqId;
        this.oid = oid;
    }
    private String oid;
    private int seqId;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    // 优惠类型
    private  int type;
    //优惠金额
    private  double discount;
    // 优惠百分比，可能一个优惠卷分摊到多个订单条目上面
    private  double percent;
    // 优惠券 规则名称
    private  String ruleName;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "OrderAdjustment{" +
                "oid='" + oid + '\'' +
                ", seqId=" + seqId +
                ", type=" + type +
                ", discount=" + discount +
                ", percent=" + percent +
                ", ruleName='" + ruleName + '\'' +
                '}';
    }
}
