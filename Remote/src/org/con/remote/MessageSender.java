package org.con.remote;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class MessageSender {
	private String serverIp = "192.168.178.40";
	private int serverPort = 8080;
	private final String tag = "MessageSender";
	private int id = 0;
	private Activity view;
	private int idNext(){
		  	return id + 1;
	}
	public MessageSender(String Ip, int Port, Activity ac){
		serverIp = Ip;
		serverPort = Port;
		view = ac;
	}
	public String get_IP(){
		return serverIp;
	}
	public int get_Port(){
		return serverPort;
	}
	public void set_IP(String ip){
		serverIp = ip;
	}
	public void set_Port(int port){
		serverPort = port;
	}
	public void send(String function, String parameter){
		String[] params = new String[2];
		params[0] = function;
		params[1] = parameter;
	    new SendCommand().execute(params);
	}
	private class SendCommand extends AsyncTask< String[], Integer, Boolean> {
		 	private Socket client = null;
	  		private DataOutputStream out = null;
	  		private DataInputStream in = null;

	  		@Override
			protected Boolean doInBackground(String[]... params) {
				client = null;
		    	out = null;
		    	in = null;
		        try {
						client = new Socket(serverIp, serverPort);
						out = new DataOutputStream(client.getOutputStream());
						in = new DataInputStream(client.getInputStream());
				} catch (UnknownHostException e) {
						Log.e(tag, e.toString());
						return false;
				} catch (IOException e) {
						Log.e(tag, e.toString());
						return false; 
				}
		        for(int i = 0;i < params.length; i++){
		        	String func = params[i][0]; 
		        	String para = params[i][1];
		        	if (!exec(func, para)){
		        		close();
		        		return false;
		        	}
		        }
		        close();
				return true;
			}
	  		@Override
	  		protected void onCancelled(){
	  			close();
	  		}
	  		private boolean exec(String func, String para) {
		    	String inpt;

		    	JSONObject js = new JSONObject();
		    	Log.v(tag, "Thread start");
		    	try {
					js.put("method", func);
					if (para == ""){
						para = null;
					}
			    	js.put("params", para);
			    	js.put("id", idNext());
				} catch (JSONException e1) {
					Log.e(tag, e1.toString());
					return false; 
				}
	  
	            try {
	            	Log.i(tag, js.toString());
	            	out.writeBytes(js.toString() + "\n");
					inpt = in.readLine();
					Log.i(tag, inpt.toString());
					out.writeBytes("END\n");
				} catch (IOException e) {
					Log.e(tag, e.toString());
					return false; 
				}
	            try {
					js = new JSONObject(new JSONTokener(inpt));
					if(js.get("error").toString() != "null"){
						Log.e(tag, "error: "+ js.get("error").toString());
					}
				} catch (JSONException e) {
					Log.e(tag, e.toString()); 
				}
	            Log.v(tag, "End of Thread");
	            return true;
			}   
	  		
		    private void close(){
		    	try {
		    		if (out != null){
			    	out.close();
		    		}
		    		if (in != null){
			    	in.close();
		    		}
		    		if (client != null){
			    	 client.close();
		    		}
				} catch (IOException e) {
					Log.e(tag, e.toString());
				}
		    	finally{
					client = null;
					out = null;
					in = null;
		    	}
		    }

		     protected void onPostExecute(Boolean result) {
		         if(result == false){
		        	 Toast.makeText(view, "No host connection", Toast.LENGTH_SHORT).show();
		         }
		     }
	    
	    }
}
