package com.hanista.clashaddict;

import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class report extends BroadcastReceiver {
	SharedPreferences spf;
	String total,
			states, UR, ser1, yy, telegram1, telegram2,
			telegram3;

	int z;
	double y1, y, usaget;

	@SuppressLint("InlinedApi")
	@Override
	public void onReceive(Context context, Intent intent) {

		Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		context.sendBroadcast(it);

		spf = PreferenceManager.getDefaultSharedPreferences(context);
		states = spf.getString("state", "low");

		total = spf.getString("total", "0");
		DecimalFormat df = new DecimalFormat("#.##");

		Double x = Double.parseDouble(total);
		ser1 = spf.getString("servicehour", "0");

		telegram1 = spf.getString("clashhour", "0");
		telegram2 = spf.getString("clashmin", "0");
		telegram3 = spf.getString("clashsec", "0");

		String Total1 = context.getString(R.string.clashofclans) + ":" + telegram1
				+ ":" + telegram2 + ":" + telegram3;

		z = Integer.parseInt(ser1);

		if (z > 24) {

			usaget = (x / 24.0);

			y = ((double) z / 24.0);

			yy = df.format(y);

			y1 = Double.parseDouble(yy);

			usaget = (x / y1);

			UR = Double.toString(usaget);

		} else {

			UR = "0 Hour/Day";
		}

		Toast.makeText(
				context,
				"گزارش اعتیادسنج کلش\n------------------------------------------------\n"
						+ "آمار استفاده :\n\n"
						+ "     "
						+ Total1
						+ "\n"
						+ "Total consumption :    "
						+ df.format(x)
						+ " hours"
						+ "\n\n\n "
						+ context.getString(R.string.addiction_state)
						+ " :    "
						+ states
						+ "\n\n\n "
						+ context.getString(R.string.usage_rate)
						+ "   "
						+ df.format(usaget)
						+ "  "
						+ context.getString(R.string.hour_per_day),
				Toast.LENGTH_LONG).show();

	}
}
