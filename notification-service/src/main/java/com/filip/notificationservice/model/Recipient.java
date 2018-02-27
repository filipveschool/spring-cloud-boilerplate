package com.filip.notificationservice.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@Table(name = "recipients")
public class Recipient {

    @Id
    private String accountName;

    @NotNull
    @Email
    private String email;

    @Valid
    private Map<NotificationType, NotificationSettings> scheduledNotifications;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<NotificationType, NotificationSettings> getScheduledNotifications() {
        return scheduledNotifications;
    }

    public void setScheduledNotifications(Map<NotificationType, NotificationSettings> scheduledNotifications) {
        this.scheduledNotifications = scheduledNotifications;
    }


    @Override
    public String toString() {
        return "Recipient{" +
                "accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
