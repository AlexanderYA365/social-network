package com.getjavajob.training.yakovleva.common;

import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "application")
public class Application extends Common implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "application_type")
    private ApplicationType applicationType;
    @Column(name = "applicant_id")
    private int applicantId;
    @Column(name = "recipient_id")
    private int recipientId;
    @Column(name = "status")
    private ApplicationStatusType status;

    public Application() {
    }

    public Application(int id, ApplicationType applicationType, int applicantId, int recipientId, ApplicationStatusType status) {
        this.id = id;
        this.applicationType = applicationType;
        this.applicantId = applicantId;
        this.recipientId = recipientId;
        this.status = status;
    }

    public Application(ApplicationType applicationType, int applicantId, int recipientId, ApplicationStatusType status) {
        this.applicationType = applicationType;
        this.applicantId = applicantId;
        this.recipientId = recipientId;
        this.status = status;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationType() {
        return applicationType.getStatus();
    }

    public void setApplicationType(int status) {
        this.applicationType = ApplicationType.values()[status];
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    public int getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(int applicantId) {
        this.applicantId = applicantId;
    }

    public int getStatus() {
        return status.getStatus();
    }

    public void setStatus(int status) {
        this.status = ApplicationStatusType.values()[status];
    }

    public void setStatus(ApplicationStatusType applicationStatusType) {
        this.status = applicationStatusType;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicationType=" + applicationType +
                ", applicantId=" + applicantId +
                ", recipientId=" + recipientId +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return id == that.id &&
                applicantId == that.applicantId &&
                recipientId == that.recipientId &&
                applicationType == that.applicationType &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationType, applicantId, recipientId, status);
    }

}