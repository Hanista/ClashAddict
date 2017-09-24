package com.hanista.clashaddict;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.hanista.market.MarketUtils;
import com.hanista.clashaddict.R;
import com.hanista.util.HanistaUtils;

public class Setting extends ActionBarActivity {

    CheckBox boot, telegrambox, notbox,
            clearbox;

    Button about, clear, products, shareapp, website;

    String bootchk, telegramcheck, not, clear1;

    SharedPreferences spfs;

    android.support.v7.app.ActionBar actionBar;

    ColorDrawable cd;

    private Button comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        about = (Button) findViewById(R.id.aboutapp);
        clear = (Button) findViewById(R.id.clear);
        products = (Button) findViewById(R.id.products);
        comment = (Button) findViewById(R.id.comment);


        telegrambox = (CheckBox) findViewById(R.id.telegrambox);
        boot = (CheckBox) findViewById(R.id.checkBox1);
        notbox = (CheckBox) findViewById(R.id.checkBox2);
        clearbox = (CheckBox) findViewById(R.id.checkBox3);

        spfs = PreferenceManager.getDefaultSharedPreferences(this);

        cd = new ColorDrawable(getResources().getColor(
                android.R.color.transparent));
        cd.setBounds(0, 0, 0, 0);

//        actionBar = getSupportActionBar();
//        actionBar.setIcon(cd);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setBackgroundDrawable(new ColorDrawable(0xff01579b));
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(true);

        load();

        boot.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                if (arg1 == true) {
                    save("boot", "true");
                } else {
                    save("boot", "false");
                }

            }
        });


        clearbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                if (arg1 == true) {

                    save("clear", "true");

                } else {

                    save("clear", "false");

                }

            }
        });

        notbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                if (arg1) {

                    save("not", "true");

                } else {

                    save("not", "false");

                }

            }
        });

        telegrambox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

                if (arg1 == true) {

                    save("clash", "true");

                } else {
                    save("clash", "false");

                }

            }
        });

        about.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.about_about)
                        .setMessage(getString(R.string.about, HanistaUtils.getAppVersionName(Setting.this)))
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                })

                        .setIcon(R.drawable.sm_launcher_icon).show();
            }
        });

        products.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MarketUtils.gotoHanistaApps(Setting.this);
            }
        });

        comment.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                MarketUtils.gotoAppComments(Setting.this);
            }
        });


        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.clear_all)
                        .setMessage(
                                R.string.clear_statisticts_confirm)
                        .setPositiveButton(R.string.confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        save("facebooksec", "0");
                                        save("facebookmin", "0");
                                        save("facebookhour", "0");

                                        save("twittersec", "0");
                                        save("twittermin", "0");
                                        save("twitterhour", "0");

                                        save("whatsappsec", "0");
                                        save("whatsappmin", "0");
                                        save("whatsapphour", "0");

                                        save("vibersec", "0");
                                        save("vibermin", "0");
                                        save("viberhour", "0");

                                        save("instagramsec", "0");
                                        save("instagrammin", "0");
                                        save("instagramhour", "0");

                                        save("beetalksec", "0");
                                        save("beetalkmin", "0");
                                        save("beetalkhour", "0");

                                        save("linesec", "0");
                                        save("linemin", "0");
                                        save("linehour", "0");

                                        save("kiksec", "0");
                                        save("kikmin", "0");
                                        save("kikhour", "0");

                                        save("clashhour", "0");
                                        save("clashsec", "0");
                                        save("clashmin", "0");

                                        save("tangomin", "0");
                                        save("tangosec", "0");
                                        save("tangohour", "0");

                                        save("servicesec", "0");
                                        save("servicemin", "0");
                                        save("servicehour", "0");

                                        save("state", "low");
                                        save("total", "0");

                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.clear_statisticts_success,
                                                Toast.LENGTH_SHORT).show();

                                    }
                                })

                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).setIcon(R.drawable.sm_launcher_icon).show();
            }
        });

    }

    public void save(String key, String value) {
        spfs = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = spfs.edit();
        edit.putString(key, value);
        edit.commit();

    }

    public void load() {

        bootchk = spfs.getString("boot", "true");

        if (bootchk.isEmpty()) {

        } else {
            if (bootchk.equals("true")) {
                boot.setChecked(true);
            } else {
                boot.setChecked(false);
            }
        }

        not = spfs.getString("not", "false");

        if (not.isEmpty()) {

        } else {
            if (not.equals("true")) {
                notbox.setChecked(true);
            } else {
                notbox.setChecked(false);
            }
        }

        clear1 = spfs.getString("clear", "false");

        if (clear1.isEmpty()) {

        } else {
            if (clear1.equals("true")) {
                clearbox.setChecked(true);
            } else {
                clearbox.setChecked(false);
            }
        }


        telegramcheck = spfs.getString("clash", "true");

        if (telegramcheck.isEmpty()) {

        } else {
            if (telegramcheck.equals("true")) {
                telegrambox.setChecked(true);
            } else {
                telegrambox.setChecked(false);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    public boolean onOptionsItemSelected(Menu menu) {
        onBackPressed();
        return true;
    }
}