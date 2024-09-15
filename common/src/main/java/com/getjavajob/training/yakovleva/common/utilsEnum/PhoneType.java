package com.getjavajob.training.yakovleva.common.utilsEnum;

public enum PhoneType {
    HOME(0),
    WORK(1),
    ADDITIONAL(2);

    private final int status;

    PhoneType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}