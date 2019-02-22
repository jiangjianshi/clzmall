package com.clzmall.common.util.excel;

/**
 * 免息红包领取任务PO
 */
public class FreeInterestRedPacketTaskPO {

    @ColumnNameUtils("用户编号")
    private String userCode;

    @ColumnNameUtils("姓名")
    private String userName;

    @ColumnNameUtils("性别")
    private String gender;

    @ColumnNameUtils("手机号-明码")
    private String phone;

    @ColumnNameUtils("注册日期")
    private String signInTimeStr;//注册时间 字符串

    @ColumnNameUtils("点击产品")
    private String clickProductName;

    @ColumnNameUtils("点击产品时间")
    private String clickTimeStr;//点击时间

    @ColumnNameUtils("命中产品1")
    private String product1;

    @ColumnNameUtils("命中产品2")
    private String product2;

    @ColumnNameUtils("命中产品3")
    private String product3;

    @ColumnNameUtils("基本信息是否完成")
    private String basicInfo; //基本信息

    @ColumnNameUtils("实名是否完成")
    private String realName; //实名认证

    @ColumnNameUtils("补充信息是否完成")
    private String supplement; //补充信息

    @ColumnNameUtils("紧急联系人是否完成")
    private String contract; //紧急联系人

    @ColumnNameUtils("是否完成运营商")
    private String operator; //运营商


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSignInTimeStr() {
        return signInTimeStr;
    }

    public void setSignInTimeStr(String signInTimeStr) {
        this.signInTimeStr = signInTimeStr;
    }

    public String getClickProductName() {
        return clickProductName;
    }

    public void setClickProductName(String clickProductName) {
        this.clickProductName = clickProductName;
    }

    public String getClickTimeStr() {
        return clickTimeStr;
    }

    public void setClickTimeStr(String clickTimeStr) {
        this.clickTimeStr = clickTimeStr;
    }

    public String getProduct1() {
        return product1;
    }

    public void setProduct1(String product1) {
        this.product1 = product1;
    }

    public String getProduct2() {
        return product2;
    }

    public void setProduct2(String product2) {
        this.product2 = product2;
    }

    public String getProduct3() {
        return product3;
    }

    public void setProduct3(String product3) {
        this.product3 = product3;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
