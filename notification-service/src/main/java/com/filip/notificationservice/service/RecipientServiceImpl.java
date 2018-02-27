package com.filip.notificationservice.service;

import com.filip.notificationservice.model.NotificationType;
import com.filip.notificationservice.model.Recipient;
import com.filip.notificationservice.repository.RecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {

    private final Logger logger = LoggerFactory.getLogger(RecipientServiceImpl.class);

    @Autowired
    private RecipientRepository recipientRepository;

    @Override
    public Recipient findByAccountName(String accountName) {
        Assert.hasLength(accountName);
        return recipientRepository.findByAccountName(accountName);
    }

    @Override
    public List<Recipient> findReadyToNotify(NotificationType type) {
        switch (type) {
            case BACKUP:
                return recipientRepository.findReadyForBackup();
            case REMIND:
                return recipientRepository.findReadyForRemind();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Recipient save(String accountName, Recipient recipient) {

        recipient.setAccountName(accountName);
        recipient.getScheduledNotifications().values().forEach(settings -> {
            if (settings.getLastNotified() == null) {
                settings.setLastNotified(new Date());
            }
        });

        recipientRepository.save(recipient);

        logger.info("recipient {} settings has been updated", recipient);

        return recipient;
    }

    @Override
    public void markNotified(NotificationType type, Recipient recipient) {
        recipient.getScheduledNotifications().get(type).setLastNotified(new Date());
        recipientRepository.save(recipient);
    }
}
