package jp.akaiosorani.android.sandlot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PreferencesSettings extends PreferenceActivity {

	private static final String DEFAULT_COUNT = "20";
	
	private static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public static int getCount(Context context) {
		String value = getSharedPreferences(context).getString(context.getString(R.string.pref_key_count), DEFAULT_COUNT);
		return Integer.parseInt(value);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.setting);
	}
	
}
