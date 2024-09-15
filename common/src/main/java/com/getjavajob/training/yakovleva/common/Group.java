package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "group_social")
public class Group extends Common implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "group_name")
    private String groupName;
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Column(name = "info")
    private String info;
    @Column(name = "id_group_creator")
    private int idGroupCreator;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIdGroupCreator() {
        return idGroupCreator;
    }

    public void setIdGroupCreator(int idGroupCreator) {
        this.idGroupCreator = idGroupCreator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId && idGroupCreator == group.idGroupCreator && Objects.equals(groupName, group.groupName) && Arrays.equals(logo, group.logo) && Objects.equals(info, group.info);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(groupId, groupName, info, idGroupCreator);
        result = 31 * result + Arrays.hashCode(logo);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", info='" + info + '\'' +
                ", idGroupCreator=" + idGroupCreator +
                '}';
    }
}