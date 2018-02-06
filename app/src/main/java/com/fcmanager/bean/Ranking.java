package com.fcmanager.bean;

/**
 * Created by Administrator on 2017/4/19.
 */
public class Ranking {
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 用户名
     */
    private String userName;
    /**
     * 分数
     */
    private String Fraction;
    /**
     * 排名
     */
    private String Ranking;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFraction() {
        return Fraction;
    }

    public void setFraction(String fraction) {
        Fraction = fraction;
    }

    public String getRanking() {
        return Ranking;
    }

    public void setRanking(String ranking) {
        Ranking = ranking;
    }
}
