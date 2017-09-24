package com.hanista.clashaddict;

import java.text.DecimalFormat;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hanista.market.MarketUtils;
import com.hanista.clashaddict.notifsrv.NotifBuilderNotificationAlarmReceiver;
import com.hanista.util.HanistaUtils;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

    TextView state, service3, service2, service1,
            totalhour, usage;

    String servicehour, servicesec, servicemin,
            starti, states, total, ser1, ser2, ser3, yy,
            clashsec, clashhour, clashmin, bootchk, firstboot;

    SharedPreferences spf;
    CompoundButton start;
    Double x, y, y1, usaget;
    int z;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Changes the compound button according to the API
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            start = (Switch) findViewById(R.id.start);

        } else {
            start = (CheckBox) findViewById(R.id.start);
        }

        // Text views, many text views
        totalhour = (TextView) findViewById(R.id.total_value);

        state = (TextView) findViewById(R.id.addiction_state);
        usage = (TextView) findViewById(R.id.usage_rate_value);

		/*
         * Start the tracking service here
		 */

        TextView btnShare = (TextView) findViewById(R.id.btn_share);
        TextView btnSetting = (TextView) findViewById(R.id.btn_setting);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareDialog();
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Setting.class));
            }
        });

        new NotifBuilderNotificationAlarmReceiver().scheduleAlarm(this);
    }

    private void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setTitle(R.string.share);
        CharSequence[] items = new CharSequence[]{
                getString(R.string.share_total),
                getString(R.string.share_usage_and_addiction)};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getString(R.string.clashaddict_app_name_tag) + "\n"
                            + getString(R.string.share_total_message, totalhour.getText().toString()) + "\n"
                            + getString(R.string.share_app_message) + "\n"
                            + "http://cafebazaar.ir/app/" + getPackageName();

                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));

                } else if (i == 1) {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = getString(R.string.clashaddict_app_name_tag) + "\n"
                            + getString(R.string.share_usage_messsage, usage.getText().toString()) + "\n"
                            + getString(R.string.share_addiction_messsage, state.getText().toString()) + "\n"
                            + getString(R.string.share_app_message) + "\n"
                            + "http://cafebazaar.ir/app/" + getPackageName();

                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)));
                }
            }
        });
        builder.create().show();
    }

    /*
     *
     * The method that save the shared prefrences as strings
     */
    public void save(String key, String value) {
        spf = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = spf.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void load() {

        spf = PreferenceManager.getDefaultSharedPreferences(this);

        String clashCheck = spf.getString("clash", "true");

        starti = spf.getString("start", "false");
        states = spf.getString("state", "low");
        total = spf.getString("total", "0");
        bootchk = spf.getString("boot", "true");
        firstboot = spf.getString("first", "true");

        ser1 = spf.getString("servicehour", "0");
        ser2 = spf.getString("servicemin", "0");
        ser3 = spf.getString("servicesec", "0");


        if (firstboot.isEmpty() || firstboot.equals("true")) {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.clashaddict_app_name)
                    .setMessage(R.string.welcome_message)
                    .setPositiveButton(R.string.confirm,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                    .setIcon(R.drawable.sm_launcher_icon).show();
            startService(new Intent(MainActivity.this,
                    ServiceSocial.class));

            save("start", "true");
            starti = "true";
            save("first", "false");

        }

        if (clashCheck.isEmpty()) {
            save("clash", "true");
        }

        if (ser1.isEmpty()) {
            save("servicehour", "0");
        } else {
            z = Integer.parseInt(ser1);
        }

        if (ser2.isEmpty()) {
            save("servicemin", "0");
        }

        if (ser3.isEmpty()) {
            save("servicesec", "0");
        }

        if (total.isEmpty()) {
            save("total", "0 Hours");
        } else {
            DecimalFormat df = new DecimalFormat("#.##");

            x = Double.parseDouble(total);

//			totalhour.setText(df.format(x) + " " + getString(R.string.hour));

            if (z > 24) {
                y = ((double) z / 24.0);
                yy = df.format(y);
                y1 = Double.parseDouble(yy);
                usaget = (x / y1);
                usage.setText(df.format(usaget) + " " + getString(R.string.hour_per_day));
            } else {
                usage.setText("0 " + getString(R.string.hour_per_day));
            }
        }

        if (states.isEmpty()) {
            save("state", "low");
        }

        states = spf.getString("state", "low");

        if (states.equals("low")) {

            state.setText(R.string.low);
            state.setTextColor(Color.parseColor("#32CD32"));

        } else if (states.equals("average")) {

            state.setText(R.string.average);
            state.setTextColor(Color.parseColor("#32CD32"));

        } else if (states.equals("attention")) {

            state.setText(R.string.attention);
            state.setTextColor(Color.parseColor("#fcce1c"));

        } else if (states.equals("addicted")) {

            state.setText(R.string.addicted);
            state.setTextColor(Color.parseColor("#FF9933"));

        } else if (states.equals("danger")) {

            state.setText(R.string.danger);
            state.setTextColor(Color.parseColor("#CC0000"));

        } else {
            state.setText(R.string.low);
            state.setTextColor(Color.parseColor("#32CD32"));
        }


        if (starti.equals("true") && isMyServiceRunning(ServiceSocial.class)) {
            start.setChecked(true);
        } else {
            start.setChecked(false);

        }

        start.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                if (arg1) {
                    startService(new Intent(MainActivity.this,
                            ServiceSocial.class));

                    HanistaUtils.showToast(MainActivity.this,
                            R.string.service_started, Toast.LENGTH_SHORT);
                    save("start", "true");
                } else {
                    stopService(new Intent(MainActivity.this,
                            ServiceSocial.class));
                    save("start", "false");
                }
            }
        });


        clashmin = spf.getString("clashmin", "0");
        if (clashmin.isEmpty()) {
            save("clashmin", "0");
            clashmin = "0";
        }

        clashsec = spf.getString("clashsec", "0");
        if (clashsec.isEmpty()) {
            save("clashsec", "0");
            clashsec = "0";
        }

        clashhour = spf.getString("clashhour", "0");
        if (clashhour.isEmpty()) {
            save("clashhour", "0");
            clashhour = "0";
        }

        totalhour.setText(clashhour + ":" + clashmin + ":" + clashsec);
/////////////////////////////////////////////////
    }


    // Check if the service is running to set the checkbox state
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (!Boolean.parseBoolean(spf.getString("commented", "false"))) {
            showCommentConfirmDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showCommentConfirmDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.clashaddict_app_name).setMessage(
                R.string.comment_message);
        dialogBuilder.setPositiveButton(R.string.set_comment,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        save("commented", "true");
                        MarketUtils.gotoAppComments(MainActivity.this);
                        finish();
                    }
                });
        dialogBuilder.setNegativeButton(R.string.exit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        dialogBuilder.create().show();

    }

}
