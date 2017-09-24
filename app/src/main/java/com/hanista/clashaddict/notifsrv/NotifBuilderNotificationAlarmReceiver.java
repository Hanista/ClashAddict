package com.hanista.clashaddict.notifsrv;

import com.hanista.notificationservice.NotificationAlarmBroadcastReceiver;
import com.hanista.notificationservice.NotificationServiceBroadcastReceiver;

public class NotifBuilderNotificationAlarmReceiver extends NotificationAlarmBroadcastReceiver {

	@Override
	public Class<? extends NotificationServiceBroadcastReceiver> getNotificationServiceReceiverClass() {
		return NotifBuilderNotifSrvBroadcastReceiver.class;
	}

}
