package com.getjavajob.training.yakovleva.web.controllers.utils;

import com.getjavajob.training.yakovleva.common.Account;

import java.util.List;

public class FriendTableUtils {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private List<Account> data;

    public FriendTableUtils(int draw, long recordsTotal, long recordsFiltered, List<Account> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Account> getData() {
        return data;
    }

    public void setData(List<Account> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FriendTableUtils{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }

}