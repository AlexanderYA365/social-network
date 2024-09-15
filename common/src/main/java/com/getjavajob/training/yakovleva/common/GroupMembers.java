package com.getjavajob.training.yakovleva.common;

import com.getjavajob.training.yakovleva.common.utilsEnum.GroupRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "group_members")
public class GroupMembers extends Common implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "id_member")
    private Account member;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private GroupRole groupRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Account getMember() {
        return member;
    }

    public void setMember(Account member) {
        this.member = member;
    }

    public GroupRole getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(GroupRole groupRole) {
        this.groupRole = groupRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembers that = (GroupMembers) o;
        return group.equals(that.group) &&
                member.equals(that.member) &&
                groupRole == that.groupRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, member, groupRole);
    }

    @Override
    public String toString() {
        return "GroupMembers{" +
                " group=" + group +
                ", member=" + member +
                ", groupRole=" + groupRole +
                '}';
    }

}