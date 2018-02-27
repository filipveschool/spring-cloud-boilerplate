package com.filip.notificationservice.service;

import com.filip.notificationservice.client.AccountServiceClient;
import com.filip.notificationservice.model.NotificationType;
import com.filip.notificationservice.model.Recipient;
import com.google.common.collect.ImmutableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import static org.mockito.MockitoAnnotations.initMocks;

public class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private RecipientService recipientService;

    @Mock
    private AccountServiceClient client;

    @Mock
    private EmailService emailService;


    @Before
    public void setUp() throws Exception {

        initMocks(this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sendBackupNotifications() throws IOException, MessagingException {

        final String attachment = "json";

        Recipient withError = new Recipient();
        withError.setAccountName("with-error");

        Recipient withNoError = new Recipient();
        withNoError.setAccountName("with-no-error");

        when(client.getAccount(withError.getAccountName())).thenThrow(new RuntimeException());
        when(client.getAccount(withNoError.getAccountName())).thenReturn(attachment);

        when(recipientService.findReadyToNotify(NotificationType.BACKUP)).thenReturn(ImmutableList.of(withNoError, withError));

        notificationService.sendBackupNotifications();

        // TODO test concurrent code in a right way

        verify(emailService, timeout(100)).send(NotificationType.BACKUP, withNoError, attachment);
        verify(recipientService, timeout(100)).markNotified(NotificationType.BACKUP, withNoError);

        verify(recipientService, never()).markNotified(NotificationType.BACKUP, withError);

    }

    @Test
    public void sendRemindNotifications() throws IOException, MessagingException {

        final String attachment = "json";

        Recipient withError = new Recipient();
        withError.setAccountName("with-error");

        Recipient withNoError = new Recipient();
        withNoError.setAccountName("with-no-error");

        when(recipientService.findReadyToNotify(NotificationType.REMIND)).thenReturn(ImmutableList.of(withNoError, withError));
        doThrow(new RuntimeException()).when(emailService).send(NotificationType.REMIND, withError, null);

        notificationService.sendRemindNotifications();

        // TODO test concurrent code in a right way

        verify(emailService, timeout(100)).send(NotificationType.REMIND, withNoError, null);
        verify(recipientService, timeout(100)).markNotified(NotificationType.REMIND, withNoError);

        verify(recipientService, never()).markNotified(NotificationType.REMIND, withError);

    }
}