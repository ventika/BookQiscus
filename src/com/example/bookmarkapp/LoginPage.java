package com.example.bookmarkapp;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends Activity implements OnClickListener, android.view.View.OnClickListener {
	Button loginBtn;
	EditText user, pass;
	//EditText pesan;
	private String url = "https://www.qisc.us/users/sign_in.json?";
	private ProgressDialog loading;
//	private SharedPreferences sp;
//	private SharedPreferences.Editor spe;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		sp = getSharedPreferences("bookmarkPreference", 0);
//		spe = sp.edit();

		user = (EditText) findViewById(R.id.editUser);
		pass = (EditText) findViewById(R.id.editPass);
		//pesan = (EditText) findViewById(R.id.editPesan);
		loginBtn = (Button) findViewById(R.id.btnLogin);
		loginBtn.setOnClickListener(this);
		
//		if (sp.contains("token")){
//			GoestoNextActivity(sp.getString("token", ""));
//		}
	} 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loading = ProgressDialog.show(LoginPage.this, "", "Loading");
				new Thread(){
					public void run(){
						try {
							sleep(10000);
						}catch (Exception e){
							Log.e("tag", e.getMessage());
						}
						loading.dismiss();
					}
				}.start();
				KirimDataAsync kirimAsync = new KirimDataAsync(){
					
					
					public void respon (String result){
						
						Toast.makeText(LoginPage.this, "Login Succes", Toast.LENGTH_SHORT).show();
						
						System.out.println("kirim..");
						//pesan.setText(result);
					}
					};
				kirimAsync.execute();
			}
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}

			public abstract class KirimDataAsync extends AsyncTask<String, String, String >{
				public abstract void respon(String result);

				protected String doInBackground(String... arg0){

				
				ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
				kirimkephp.add(new BasicNameValuePair("user[email]", user.getText()
						.toString()));
				kirimkephp.add(new BasicNameValuePair("user[password]", pass.getText()
						.toString()));
				String respon = null;
				System.out.println("kirim");
				try {
					
					respon = ClientToServer.eksekusiHttpPost(url, kirimkephp);
					JSONObject objectAuth = new JSONObject(respon);
					//JSONArray arrayEvent = objectAuth.getJSONArray("listAuth");
					//for (int i = 0; i < arrayEvent.length(); i++){
					//JSONObject objectList = arrayEvent.getJSONObject(i);
					
					String statusAuth = objectAuth.getString("success");
					String tokenAuth = objectAuth.getString("token");
					
					if (statusAuth  == "true" ) {
					//	GoestoNextActivity()
						//pesan.setText("Success");
//						spe.putString("token", tokenAuth);
//						spe.commit();
						GoestoNextActivity(tokenAuth);
					}
					if (statusAuth == "false")
					{
						Toast.makeText(LoginPage.this, "Login Succes", Toast.LENGTH_SHORT).show();
						//pesan.setText("Failed");
					}
					
				
				} catch (Exception e) {

					e.printStackTrace();
				}
				return respon;
			}
			
	
			protected void onPostExecute(String result){
				super.onPostExecute(result);
				respon (result);
				}
	}
			private void GoestoNextActivity(String tokenAuth) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginPage.this, MainActivity.class);
				intent.putExtra("token", tokenAuth);
				startActivity(intent);
				finish();
			}
}