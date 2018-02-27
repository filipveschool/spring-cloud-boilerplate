package com.filip.notificationservice.service;

import com.filip.notificationservice.model.NotificationType;
import com.filip.notificationservice.model.Recipient;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void send(NotificationType type, Recipient recipient, String attachment) throws MessagingException, IOException;

}
