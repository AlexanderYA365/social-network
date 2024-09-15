package com.getjavajob.training.yakovleva.common.utilsEnum;

public enum ApplicationStatusType {
    ACCEPTED(0),
    CONFIRMATION(1),
    REJECTED(2);

    private final int status;

    ApplicationStatusType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}