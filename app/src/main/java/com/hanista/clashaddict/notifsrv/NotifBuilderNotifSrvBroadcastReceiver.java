package com.hanista.clashaddict.notifsrv;

import com.hanista.notificationservice.NotificationService;
import com.hanista.notificationservice.NotificationServiceBroadcastReceiver;

public class NotifBuilderNotifSrvBroadcastReceiver extends NotificationServiceBroadcastReceiver {

	@Override
	protected Class<? extends NotificationService> getNotificationServiceClass() {
		return NotifBuilderNotificationService.class;
	}


}
