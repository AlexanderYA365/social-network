package com.getjavajob.training.yakovleva.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "account_photo")
public class AccountPhoto extends Common implements Serializable {
    @Id
    @Column(name = "account_id")
    private int id;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "photo_file_name")
    private String photoFileName;
    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    public AccountPhoto() {
    }

    public AccountPhoto(int id, byte[] photo, String photoFileName) {
        this.id = id;
        this.photo = photo;
        this.photoFileName = photoFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountPhoto{" +
                "id=" + id +
                ", photo=" + Arrays.toString(photo) +
                ", photoFileName='" + photoFileName + '\'' +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountPhoto that = (AccountPhoto) o;
        return id == that.id && Arrays.equals(photo, that.photo)
                && Objects.equals(photoFileName, that.photoFileName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, photoFileName);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

}