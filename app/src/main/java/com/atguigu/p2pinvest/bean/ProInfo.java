package com.atguigu.p2pinvest.bean;

/**
 * Created by 颜银 on 2016/11/12.
 * QQ:443098360
 * 微信：y443098360
 * 作用：
 */
public class ProInfo {

    /**
     * id : 1
     * memberNum : 100
     * minTouMoney : 100
     * money : 10
     * name : 超级新手计划
     * progress : 90
     * suodingDays : 30
     * yearRate : 8.00
     */

    private String id;
    private String memberNum;
    private String minTouMoney;
    private String money;
    private String name;
    private String progress;
    private String suodingDays;
    private String yearRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getMinTouMoney() {
        return minTouMoney;
    }

    public void setMinTouMoney(String minTouMoney) {
        this.minTouMoney = minTouMoney;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getSuodingDays() {
        return suodingDays;
    }

    public void setSuodingDays(String suodingDays) {
        this.suodingDays = suodingDays;
    }

    public String getYearRate() {
        return yearRate;
    }

    public void setYearRate(String yearRate) {
        this.yearRate = yearRate;
    }
}
