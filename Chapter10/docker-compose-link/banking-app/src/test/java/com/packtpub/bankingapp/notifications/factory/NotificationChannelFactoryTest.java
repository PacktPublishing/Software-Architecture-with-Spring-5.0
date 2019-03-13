package com.packtpub.bankingapp.notifications.factory;

import com.packtpub.bankingapp.notifications.channels.impl.EmailNotificationChannel;
import com.packtpub.bankingapp.notifications.channels.impl.FaxNotificationChannel;
import com.packtpub.bankingapp.notifications.domain.NotificationType;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class NotificationChannelFactoryTest {

    @Test
    public void givenANameWhenTheNotificationChannelIsRequestedThenTheChannelIsReturned() throws Exception {

        EmailNotificationChannel email = mock(EmailNotificationChannel.class);
        FaxNotificationChannel fax = mock(FaxNotificationChannel.class);

        NotificationChannelFactory notificationChannelFactory = new NotificationChannelFactory(email, fax);

        Assert.assertEquals(email, notificationChannelFactory.getNotificationChannel(NotificationType.EMAIL));
        Assert.assertEquals(fax, notificationChannelFactory.getNotificationChannel(NotificationType.FAX));
    }
}
