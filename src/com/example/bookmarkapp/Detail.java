package com.example.bookmarkapp;

//import java.util.ArrayList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;


@SuppressLint("NewApi")
public class Detail extends Activity {
	
	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.detail_page);
      
      ActionBar ab = getActionBar(); 
      ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#71CD9E"));     
      ab.setBackgroundDrawable(colorDrawable);
      
      		String room = getIntent().getStringExtra("room_name");
      		String topic = getIntent().getStringExtra("topic_title");
      		String commenter_name = getIntent().getStringExtra("commenter_name");
				//String id = getIntent().getStringExtra("id");
				String message = getIntent().getStringExtra("message");
				String update_at = getIntent().getStringExtra("updated_at");
				String email = getIntent().getStringExtra("commenter_email");
				
				TextView textroom = (TextView) findViewById(R.id.room);
				TextView texttopic = (TextView) findViewById(R.id.topic);
				TextView textname = (TextView) findViewById(R.id.name);
				TextView textmessage = (TextView) findViewById(R.id.message);
				TextView textupdate = (TextView) findViewById(R.id.update);
				TextView textexpired = (TextView) findViewById(R.id.expired);
				TextView textemail = (TextView) findViewById(R.id.email);
				
				textroom.setText(room);
				texttopic.setText(topic);
				textname.setText(commenter_name);
				textmessage.setText(message);
				textupdate.setText(update_at);
			
				String Expired = String.valueOf(getExpired());
				textexpired.setText(Expired);
				textemail.setText(email);
				
	}
	
	public String getExpired(){
		String updatedAt = getIntent().getStringExtra("updated_at");
		String modifiedUpdatedAt = updatedAt.replace("T", " ");
		String modifiedUpdatedAt2 = modifiedUpdatedAt.replace("Z", "");
		System.out.println("updateAT : " + modifiedUpdatedAt2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dt = new Date();
		String Keterangan = " ";
		long lama = -1;
		long Expire = -1;
		String Expired = null;
		try {
			
			Date tgl = sdf.parse(modifiedUpdatedAt2);
			System.out.println("parse "+tgl);
			//Date dt = sdf.parse(dt);
			//Date c1 = (Date) sdf.parse(c1);
			//int days = Days.daysBetween(tgl, dt).getDays();
			lama = (int) Math.round((dt.getTime() - tgl.getTime()));
			if (lama  > 518400000){
				//perjam
				//jika lebih dari 6 hari berarti expirednya tinggal 1 hari maka dibagi 3600000 biar dapetinnya per jam
				// 168 itu 7 x 24 jam 
				lama = (int) Math.round((dt.getTime() - tgl.getTime())/ (double) 3600000);
				Expire = 168 - lama;
				Keterangan = " hours left";
				Expired = Expire+Keterangan;
				
			}
			else if (lama > 601200000){
				//permenit
				//jika lebih dari 6 hari 23 jam 
				// 167 jam ke milisecond = 601,200,000
				// dibagi 60000 biar dapetinnya per menit
				//10080 itu 167 jam x 60 menit
				lama = (int) Math.round((dt.getTime() - tgl.getTime()) / (double)60000);
				Expire = 10080 - lama;
				Keterangan = " minutes left";
				Expired = Expire+Keterangan;
			}
			else if (lama >604740000){
				//per second
				lama = (int) Math.round((dt.getTime() - tgl.getTime()) / (double)1000);
				Expire = 604800 - lama;
				Keterangan = " seconds left";
				Expired = Expire+Keterangan;
			}
			else
				lama = (int) Math.round((dt.getTime() - tgl.getTime()) / (double)86400000);
				Expire = 7 - lama;
				Keterangan = " days left";
				Expired = Expire+Keterangan;

			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Expired;
		
		
	}
	
	

	
	
}