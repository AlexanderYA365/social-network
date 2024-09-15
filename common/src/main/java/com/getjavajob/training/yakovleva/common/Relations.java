package com.getjavajob.training.yakovleva.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(RelationId.class)
@Table(name = "relations")
public class Relations extends Common implements Serializable {
    @Id
    @Column(name = "account_id")
    private int accountId;
    @Id
    @Column(name = "friend_id")
    private int friendId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;

    public Relations() {
    }

    public Relations(int accountId, int friendId) {
        this.accountId = accountId;
        this.friendId = friendId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "Relations{" +
                "account_id=" + accountId +
                ", friend_id=" + friendId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relations relations = (Relations) o;
        return accountId == relations.accountId &&
                friendId == relations.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, friendId);
    }

}

@Embeddable
class RelationId implements Serializable {
    private int accountId;
    private int friendId;

    public RelationId() {
    }

    public RelationId(int accountId, int friendId) {
        this.accountId = accountId;
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationId that = (RelationId) o;
        return accountId == that.accountId &&
                friendId == that.friendId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, friendId);
    }

}