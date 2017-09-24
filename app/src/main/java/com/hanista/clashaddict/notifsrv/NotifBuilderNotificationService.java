package com.hanista.clashaddict.notifsrv;

import com.hanista.notificationservice.NotificationService;
import com.hanista.notificationservice.ServerNotification;
import com.hanista.clashaddict.MainActivity;
import com.hanista.clashaddict.R;

public class NotifBuilderNotificationService extends NotificationService {

	@Override
	protected int getSmallIcon() {
		return R.drawable.sm_launcher_icon;
	}

	@Override
	protected void handleGift(ServerNotification notif) {
		// nothing
	}

	@Override
	protected Class<?> getLauncherClass() {
		return MainActivity.class;
	}

}
