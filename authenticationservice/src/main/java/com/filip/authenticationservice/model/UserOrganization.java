package com.filip.authenticationservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_orgs")
public class UserOrganization implements Serializable {

    @Column(name = "organization_id",nullable = false)
    private String organizationId;

    @Id
    @Column(name = "user_name", nullable = false)
    private String userName;

    public UserOrganization(){

    }

    public UserOrganization(String organizationId, String userName) {
        this.organizationId = organizationId;
        this.userName = userName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
