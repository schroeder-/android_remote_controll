package org.con.remote;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.widget.Toast;


public class Settings extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	private String tag = "Prefrences";
	//private String backIP;
	private String backPort;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		//backIP = sp.getString("IP", null);
		backPort = sp.getString("PORT", null);
	    sp.registerOnSharedPreferenceChangeListener(this);
	}
	@Override
	protected void onRestoreInstanceState(Bundle state){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		//backIP = preferences.getString("IP", null);
		backPort = preferences.getString("PORT", null);		
		super.onRestoreInstanceState(state);
	}
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
//		   if (key.equals("IP")) {
//
// Search for a valid mail pattern
//		        String pattern = "mailpattern";
//		        String value = sharedPreferences.getString(key, null);
//		        if (!Pattern.matches(pattern, value)) {
//		            // The value is not a valid email address.
//		            // Do anything like advice the user or change the value
//		        }
//		   }        		
		   if(key.equals("PORT")){
			   Integer val = Integer.valueOf(sharedPreferences.getString("PORT", null));
			   if(val != null || val < 1000){
				 backPort = val.toString();
			   }
			   else{
				   Toast.makeText(this, "Wrong Portnumber or no number", Toast.LENGTH_LONG).show();
				   val = Integer.valueOf(backPort);
			   }
			   sharedPreferences.edit().putInt("PORT", val);
		   }
	}
	
	
}
