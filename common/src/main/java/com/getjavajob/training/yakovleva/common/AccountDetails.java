package com.getjavajob.training.yakovleva.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account_details")
public class AccountDetails extends Common implements Serializable {
    @Id
    @Column(name = "account_id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "icq")
    private int icq;
    @Column(name = "address_home")
    private String addressHome;
    @Column(name = "address_job")
    private String addressJob;
    @Column(name = "email")
    private String email;
    @Column(name = "about_me")
    private String aboutMe;
    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    public AccountDetails() {
    }

    public AccountDetails(int id, String name, String surname, String lastName, Date date,
                          int icq, String addressHome, String addressJob, String email, String aboutMe) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.date = date;
        this.icq = icq;
        this.addressHome = addressHome;
        this.addressJob = addressJob;
        this.email = email;
        this.aboutMe = aboutMe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIcq() {
        return icq;
    }

    public void setIcq(int icq) {
        this.icq = icq;
    }

    public String getAddressHome() {
        return addressHome;
    }

    public void setAddressHome(String addressHome) {
        this.addressHome = addressHome;
    }

    public String getAddressJob() {
        return addressJob;
    }

    public void setAddressJob(String addressJob) {
        this.addressJob = addressJob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", date=" + date +
                ", icq=" + icq +
                ", addressHome='" + addressHome + '\'' +
                ", addressJob='" + addressJob + '\'' +
                ", email='" + email + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails that = (AccountDetails) o;
        return id == that.id && icq == that.icq && Objects.equals(name, that.name)
                && Objects.equals(surname, that.surname)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(date, that.date) && Objects.equals(addressHome, that.addressHome)
                && Objects.equals(addressJob, that.addressJob) && Objects.equals(email, that.email)
                && Objects.equals(aboutMe, that.aboutMe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, lastName, date, icq, addressHome, addressJob, email, aboutMe);
    }

}