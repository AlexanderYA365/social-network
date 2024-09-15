package com.getjavajob.training.yakovleva.common.utilsEnum;

public enum Role {
    ROLE_USER(0),
    ROLE_ADMIN(1);

    private final int status;

    Role(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

}