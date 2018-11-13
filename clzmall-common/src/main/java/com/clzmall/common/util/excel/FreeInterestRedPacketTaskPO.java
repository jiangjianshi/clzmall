package com.clzmall.common.util.excel;

/**
 * 免息红包领取任务PO
 */
public class FreeInterestRedPacketTaskPO {

    @ColumnNameUtils("型号")
    private String className;

    @ColumnNameUtils("名称")
    private String name;

    @ColumnNameUtils("尺寸")
    private String size;

    @ColumnNameUtils("材质")
    private String caizhi;

    @ColumnNameUtils("单价")
    private String price;

    @ColumnNameUtils("图片链接")
    private String remark;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCaizhi() {
        return caizhi;
    }

    public void setCaizhi(String caizhi) {
        this.caizhi = caizhi;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
