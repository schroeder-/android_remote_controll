package org.con.remote;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.content.BroadcastReceiver;
import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RemoteActivity extends Activity {
	private String defaultIP = "192.168.78.40";
	private int defaultPort = 8080;
	private RemoteActivity thiss = this;
	private ConnectivityManager conMan;
	private State wifi;
	private String tag = "JsonSender";
    private MessageSender sender;
	private Buttonhandel bthandel;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button bt0 = (Button) findViewById(R.id.bt0);
        final Button bt1 = (Button) findViewById(R.id.bt1);
        final Button bt2 = (Button) findViewById(R.id.bt2);
        final Button bt3 = (Button) findViewById(R.id.bt3);
        final Button bt4 = (Button) findViewById(R.id.bt4);
        final Button bt5 = (Button) findViewById(R.id.bt5);
        final Button bt6 = (Button) findViewById(R.id.bt6);
        final Button bt7 = (Button) findViewById(R.id.bt7);
        final Button bt8 = (Button) findViewById(R.id.bt8);
        final Button bt9 = (Button) findViewById(R.id.bt9);
        final Button btpwr = (Button) findViewById(R.id.btpwr);
        final Button btch1 = (Button) findViewById(R.id.btch1);
        final Button btch2 = (Button) findViewById(R.id.btch2);
        final Button btvol1 = (Button) findViewById(R.id.btvol1);
        final Button btvol2 = (Button) findViewById(R.id.btvol2);
        bthandel = new Buttonhandel();
        bt0.setOnClickListener(bthandel);
        bt1.setOnClickListener(bthandel);
        bt2.setOnClickListener(bthandel);
        bt3.setOnClickListener(bthandel);
        bt4.setOnClickListener(bthandel);
        bt5.setOnClickListener(bthandel);
        bt6.setOnClickListener(bthandel);
        bt7.setOnClickListener(bthandel);
        bt8.setOnClickListener(bthandel);
        bt9.setOnClickListener(bthandel);
        btpwr.setOnClickListener(bthandel);
        btch1.setOnClickListener(bthandel);
        btch2.setOnClickListener(bthandel);
        btvol1.setOnClickListener(bthandel);
        btvol2.setOnClickListener(bthandel);
        conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        sender = new MessageSender(defaultIP, defaultPort, this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.config:
            optionDialog(); 
            return true;
        case R.id.tvtime:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    private class Buttonhandel implements View.OnClickListener{
		public void onClick(View v) {
			String para = "";
			String func = "";
			Integer i = 9;
			boolean end = false;
			if (!get_wifi()){
				Toast.makeText(thiss, "No wifi connection", Toast.LENGTH_SHORT).show();
				return;
			}
			switch(v.getId()){
			case R.id.bt0:
				i--;
			case R.id.bt1:
				i--;
			case R.id.bt2:
				i--;
			case R.id.bt3:
				i--;
			case R.id.bt4:
				i--;
			case R.id.bt5:
				i--;
			case R.id.bt6:
				i--;
			case R.id.bt7:
				i--;
			case R.id.bt8:
				i--;
			case R.id.bt9:
				para = i.toString();
				func = "num_key";
				break;
			case R.id.btch1:
        		para = null;
        		func = "ch_plus";
        		break;
			case R.id.btch2:
				para = null;
        		func = "ch_minus";
        		break;
			case R.id.btpwr:
        		para = null;
        		func = "power_on";
        		break;
			case R.id.btvol1:
				para = null;
        		func = "vol_plus";
        		break;
			case R.id.btvol2:
				para = null;
        		func = "vol_minus";
        		break;
            /* unused functions
        		para = null;
        		func = "fullscreen";
        		
        		para = null;
        		func = "mute";
        		*/
			default: end = true;
			} 
			if (end == true){
				return;
			}
			sender.send(func, para);
		}
    }
    private boolean get_wifi(){
    	if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) 
    		return true;
    	return false;
    }
    private void optionDialog(){
    	String message = "Bitte IP Adresse oder Hostname eingeben";
    	final EditText input = new EditText(this);
    	input.setText(sender.get_IP(), TextView.BufferType.EDITABLE);
    	new AlertDialog.Builder(this)
        .setTitle("Einstellungen")
        .setMessage(message)
        .setView(input)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable value = input.getText();
                sender.set_IP(value.toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
        
    }
}