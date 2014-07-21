package com.example.bookmarkapp;

import android.os.AsyncTask;

public abstract class GetBookmark extends AsyncTask<String, String, String> {

	public abstract void respon(String respon);
	
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url=arg0[0];
		String respon=null;
		
		try {
			respon=ClientToServer.eksekusiHttpGet(url);
			System.out.println(respon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respon;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		respon(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
