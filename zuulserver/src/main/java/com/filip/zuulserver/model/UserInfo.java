package com.filip.zuulserver.model;

public class UserInfo {

    private String organizationId;
    private String userId;

    public UserInfo(){

    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
