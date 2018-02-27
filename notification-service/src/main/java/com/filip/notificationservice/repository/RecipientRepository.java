package com.filip.notificationservice.repository;

import com.filip.notificationservice.model.Recipient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends CrudRepository<Recipient, String> {

    Recipient findByAccountName(String name);

    @Query("{ $and: [ {scheduledNotifications.BACKUP.active: true }, { $where: 'this.scheduledNotifications.BACKUP.lastNotified < " +
            "new Date(new Date().setDate(new Date().getDate() - this.scheduledNotifications.BACKUP.frequency ))' }] }")
    List<Recipient> findReadyForBackup();

    @Query("{ $and: [ {scheduledNotifications.REMIND.active: true }, { $where: 'this.scheduledNotifications.REMIND.lastNotified < " +
            "new Date(new Date().setDate(new Date().getDate() - this.scheduledNotifications.REMIND.frequency ))' }] }")
    List<Recipient> findReadyForRemind();

}
