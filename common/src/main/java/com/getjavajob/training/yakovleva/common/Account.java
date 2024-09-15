package com.getjavajob.training.yakovleva.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.getjavajob.training.yakovleva.common.utilsEnum.Role;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "account")
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account extends Common implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column(name = "account_id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private Role role;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    @PrimaryKeyJoinColumn
    @XmlTransient
    private AccountDetails accountDetails;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account")
    @PrimaryKeyJoinColumn
    @XmlTransient
    private AccountPhoto accountPhoto;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Phone> phones;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @XmlTransient
    private List<Relations> relations;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @XmlTransient
    private List<Message> message;

    public Account(int id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Account(String username, String password, Role role, List<Relations> relations,
                   List<Message> message) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.relations = relations;
        this.message = message;
    }

    public Account() {
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> messages) {
        this.message = messages;
    }

    public List<Relations> getRelations() {
        return relations;
    }

    public void setRelations(List<Relations> relations) {
        this.relations = relations;
    }

    public int getRole() {
        return role.getStatus();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRole(int status) {
        this.role = Role.values()[status];
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonManagedReference
    public List<Phone> getPhones() {
        return phones;
    }

    @JsonManagedReference
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
        this.accountDetails.setAccount(this);
    }

    public AccountPhoto getAccountPhoto() {
        return accountPhoto;
    }

    public void setAccountPhoto(AccountPhoto accountPhoto) {
        this.accountPhoto = accountPhoto;
        this.accountPhoto.setAccount(this);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountDetails='" + this.getAccountDetails() + '\'' +
                ", phones='" + this.getPhones() + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(phones, account.phones) &&
                Objects.equals(username, account.username) &&
                Objects.equals(password, account.password) &&
                role == account.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phones, username, password, role);
    }

}