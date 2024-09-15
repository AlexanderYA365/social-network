package com.getjavajob.training.yakovleva.common.utilsEnum;

public enum MessageType {
    WALL(0),
    PRIVATE(1),
    GROUP(2);

    private final int status;

    MessageType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}