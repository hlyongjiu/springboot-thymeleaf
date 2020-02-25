package com.learn.springboot.virtual.pojo;

import java.util.Date;

public class Record {
    private int id;
    private int uid;
    private String uname;
    private int typeOne;
    private int typeTwo;
    private long amount;
    private Date createDate;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getTypeOne() {
        return typeOne;
    }

    public void setTypeOne(int typeOne) {
        this.typeOne = typeOne;
    }

    public int getTypeTwo() {
        return typeTwo;
    }

    public void setTypeTwo(int typeTwo) {
        this.typeTwo = typeTwo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
