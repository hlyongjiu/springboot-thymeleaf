package com.learn.springboot.virtual.pojo;

public class Coin {
    private int id;
    private long sell;
    private long residue;
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSell() {
        return sell;
    }

    public void setSell(long sell) {
        this.sell = sell;
    }

    public long getResidue() {
        return residue;
    }

    public void setResidue(long residue) {
        this.residue = residue;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
