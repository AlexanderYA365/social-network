package com.getjavajob.training.yakovleva.common.utilsEnum;

public enum ApplicationType {
    GROUP(0),
    USER(1);

    private final int status;

    ApplicationType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}