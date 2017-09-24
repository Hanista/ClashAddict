package com.hanista.clashaddict;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class ServiceSocial extends Service {

    private Handler mainhandler;
    private Handler customHandlerClash = new Handler();

    Boolean clash;
    double total, totalsec, totalmin, totalall, totalall1, totalall2;
    SharedPreferences spf;
    int f, t, tu, w, i, k, b, a, s, km;
    int x = 60;
    int y = 3600;
    double addict;

    public static int
            servicetemp1, servicetemp2,
            servicetemp3, clash1, clash2,
            clash3;

    public static String starti,
            ser1, ser2, ser3,
            packageName, clashhour,
            clashmin, clashsec, clashcheck, not, clear;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void load() {

        clashcheck = spf.getString("clash", "true");

        ser1 = spf.getString("servicehour", "0");
        ser2 = spf.getString("servicemin", "0");
        ser3 = spf.getString("servicesec", "0");

        starti = spf.getString("start", "false");

        clashhour = spf.getString("clashhour", "0");
        clashmin = spf.getString("clashmin", "0");
        clashsec = spf.getString("clashsec", "0");

        clash1 = Integer.parseInt(clashhour);
        clash2 = Integer.parseInt(clashmin);
        clash3 = Integer.parseInt(clashsec);

        servicetemp3 = Integer.parseInt(ser3);
        servicetemp2 = Integer.parseInt(ser2);
        servicetemp1 = Integer.parseInt(ser1);

    }

    // On Creating Service
    @Override
    public void onCreate() {
        spf = PreferenceManager.getDefaultSharedPreferences(this);

        not = spf.getString("not", "false");
        clear = spf.getString("clear", "false");

        if (not.isEmpty()) {
            save("not", "false");
        }

        if (clear.isEmpty()) {
            save("clear", "false");

        }

        if (!not.equals("true")) {
//			showNotification();
        }

    }

    @SuppressLint("HandlerLeak")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        spf = PreferenceManager.getDefaultSharedPreferences(this);

        super.onStartCommand(intent, flags, startId);

        // try { load(); } catch (NullPointerException nullPointerException) {}

        mainhandler = new Handler() {

            @SuppressLint("SimpleDateFormat")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                try {

                    if (clear.equals("true")) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String strDate = sdf.format(c.getTime());

                        if (strDate.equals("00:00:00")) {

                            save("clashhour", "0");
                            save("clashsec", "0");
                            save("clashmin", "0");

                            save("servicesec", "0");
                            save("servicemin", "0");
                            save("servicehour", "0");

                            save("state", "low");
                            save("total", "0");

                        }
                    }

                    load();

                    totalmin = (double) (clash2);

                    totalsec = (double) (clash3);

                    totalall1 = ((double) totalmin / (double) x);
                    totalall2 = ((double) totalsec / (double) y);

                    totalall = (double) (totalall1 + totalall2);

                    total = (double) (totalall
                            + clash1);

                    save("total", String.valueOf(total));

                    servicetemp3++;

                    if (servicetemp3 >= 60) {
                        servicetemp3 = 00;
                        servicetemp2 += 1;

                    }

                    if (servicetemp2 >= 60) {
                        servicetemp2 = 00;
                        servicetemp1 += 1;

                    }

                    save("servicesec", String.valueOf(servicetemp3));
                    save("servicemin", String.valueOf(servicetemp2));
                    save("servicehour", String.valueOf(servicetemp1));

                    addict = ((double) total / (double) servicetemp1);

                } catch (NullPointerException nullPointerException) {
                }

                if (servicetemp1 > 4) {

                    if (addict < 0.1) {

                        save("state", "low");

                    } else {

                        if (addict < 0.2 && addict > 0.1) {

                            save("state", "average");

                        } else {

                            if (addict < 0.3 && addict > 0.2) {

                                save("state", "attention");

                            } else {

                                if (addict < 0.5 && addict > 0.4) {

                                    save("state", "addicted");

                                } else {

                                    if (addict > 0.6) {

                                        save("state", "danger");

                                    }
                                }
                            }
                        }
                    }
                }

                ActivityManager am = (ActivityManager) getApplicationContext()
                        .getSystemService(Activity.ACTIVITY_SERVICE);
                packageName = getActivatePackage(am);

                if (packageName == null)
                    return;

                if (starti.equals("true")) {

                    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    if (clashcheck.equals("true")) {

                        if (packageName.equals("com.supercell.clashofclans")) {

                            if (s == 1) {

                            } else {

                                s = 1;
                                clash = true;

                                customHandlerClash.postDelayed(
                                        updateTimerThreadClash, 0);
                            }

                        } else {

                            if (s == 1) {

                                clash = false;

                                customHandlerClash
                                        .removeCallbacks(updateTimerThreadClash);
                                s = 2;
                                System.gc();

                            }
                        }
                    } else {

                        if (s == 1) {

                            clash = false;

                            customHandlerClash
                                    .removeCallbacks(updateTimerThreadClash);
                            s = 2;
                            System.gc();

                        }

                    }
                    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                }

            }

        };

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {

                        Thread.sleep(1000);
//						Log.i("ServiceSocial", "service started");
                        mainhandler.sendEmptyMessage(0);

                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }

                }

            }
        }).start();
//		Log.i("ServiceSocial", "service initialized");
        startForeground(1363, showNotification());
        return START_STICKY;
    }


    @SuppressLint("InlinedApi")
    private Notification showNotification() {

        Intent deleteIntent = new Intent(this, close.class);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(this, 0,
                deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent settingIntent = new Intent(this, settinggg.class);
        PendingIntent pendingIntentCancel1 = PendingIntent.getBroadcast(this,
                0, settingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Intent reprt = new Intent(this, report.class);
        // PendingIntent pendingIntentCancel2 = PendingIntent.getBroadcast(this,
        // 0, reprt, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);
        mBuilder.setContentTitle(getString(R.string.clashaddict_app_name));
        mBuilder.setContentText(getString(R.string.service_is_on));
        mBuilder.setTicker(getString(R.string.clashaddict_app_name));
        mBuilder.setSmallIcon(R.drawable.ic_notificaiton);
        mBuilder.addAction(0, getString(R.string.stop_service),
                pendingIntentCancel);
        mBuilder.addAction(0, getString(R.string.action_settings),
                pendingIntentCancel1);
        // mBuilder.addAction(0, "Usage", pendingIntentCancel2);

        mBuilder.setOngoing(true);

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        return mBuilder.build();
//		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		mNotificationManager.notify(2, mBuilder.build());


    }

    public void onDestroy() {
        super.onDestroy();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    public void save(String key, String value) {
        Editor edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }

    public String getActivatePackage(ActivityManager activityManager) {
        String[] activePackages;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            activePackages = getActivePackages(activityManager);
        } else {
            activePackages = getActivePackagesCompat(activityManager);
        }
        if (activePackages != null && activePackages.length > 0) {
            return activePackages[0];
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private String[] getActivePackagesCompat(ActivityManager activityManager) {
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager
                .getRunningTasks(1);
        ComponentName componentName = taskInfo.get(0).topActivity;
        String[] activePackages = new String[1];
        if (componentName != null) {
            activePackages[0] = componentName.getPackageName();
        }
        return activePackages;
    }

    @SuppressLint("NewApi")
    private String[] getActivePackages(ActivityManager activityManager) {
        // final List<ActivityManager.RunningAppProcessInfo> processInfos =
        // activityManager.getRunningAppProcesses();
        // for (ActivityManager.RunningAppProcessInfo processInfo :
        // processInfos) {
        // if (processInfo.importance ==
        // ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
        // activePackages.addAll(Arrays.asList(processInfo.pkgList));
        // }
        // }
        // return activePackages.toArray(new String[activePackages.size()]);
        // UsageStatsManager mUsageStatsManager = (UsageStatsManager)
        // getSystemService("usagestats");
        // long time = System.currentTimeMillis();
        // // We get usage stats for the last 10 seconds
        // List<UsageStats> stats = mUsageStatsManager.queryUsageStats(
        // UsageStatsManager.INTERVAL_DAILY, 0, time);
        // // Sort the stats by the last time used
        // if (stats != null) {
        // SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long,
        // UsageStats>();
        // for (UsageStats usageStats : stats) {
        // mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
        // }
        // if (mySortedMap != null && !mySortedMap.isEmpty()) {
        // activePackages.add(mySortedMap.get(mySortedMap.lastKey())
        // .getPackageName());
        // }
        // }

        final int START_TASK_TO_FRONT = 2;
        RunningAppProcessInfo currentInfo = null;
        Field field = null;
        try {
            field = RunningAppProcessInfo.class
                    .getDeclaredField("processState");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
        for (RunningAppProcessInfo app : appList) {
            if (app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                Integer state = null;
                try {
                    state = field.getInt(app);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return null;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return null;
                }
                if (state != null && state == START_TASK_TO_FRONT) {
                    currentInfo = app;
                    break;
                }
            }
        }

        if (currentInfo == null)
            return null;

        return currentInfo.pkgList;
    }


    private Runnable updateTimerThreadClash = new Runnable() {

        public void run() {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (clash = true) {

                clash3++;

                if (clash3 >= 60) {
                    clash3 = 0;
                    clash2 += 1;

                }
                if (clash2 >= 60) {
                    clash2 = 0;
                    clash1 += 1;
                }

                save("clashsec", String.valueOf(clash3));
                save("clashmin", String.valueOf(clash2));
                save("clashhour", String.valueOf(clash1));

                customHandlerClash.postDelayed(this, 0);

            }
        }

    };

};