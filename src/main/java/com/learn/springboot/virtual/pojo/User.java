package com.learn.springboot.virtual.pojo;

public class User {
    private int id;
    private String userName;
    private String pwd;
    private long rmb;
    private long coin;
    private long coinResidue;
    private long coinSell;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCoinResidue() {
        return coinResidue;
    }

    public void setCoinResidue(long coinResidue) {
        this.coinResidue = coinResidue;
    }

    public long getCoinSell() {
        return coinSell;
    }

    public void setCoinSell(long coinSell) {
        this.coinSell = coinSell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getRmb() {
        return rmb;
    }

    public void setRmb(long rmb) {
        this.rmb = rmb;
    }

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
