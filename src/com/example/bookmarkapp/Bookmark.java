package com.example.bookmarkapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bookmark {
	public String topic_title;
	public String room_name;
	public String updated_at;
	public String commenter_email;
	public String commenter_name;
	public String message;
	public String id;
	public String  expired_in;

	
	public Bookmark(String id, String topic_title, String room_name, String updated_at,
			String commenter_email, String commenter_name, String message,String  expired_in) {
		super();
		this.topic_title = topic_title;
		this.room_name = room_name;
		this.updated_at = updated_at;
		this.commenter_email = commenter_email;
		this.commenter_name = commenter_name;
		this.message = message;
		this.id= id;
		this.expired_in = expired_in;
	}

	public String getid() {
		return id;
	}
	
	public String getTopic_id() {
		return topic_title;
	}

	public String getRoom_id() {
		return room_name;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public String getCommenter_email() {
		return commenter_email;
	}

	public String getCommenter_name() {
		return commenter_name;
	}
	
	public String getMessage() {
		return message;
	}
	public String getexpired_in() {
		return expired_in;
	}

	public CharSequence getOneWeekFromNow() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getExpired(){
		String expired_in = getexpired_in();
		String modifiedExpired_in = expired_in.replace("T", " ");
		String modifiedExpired_in2 = modifiedExpired_in.replace("Z", "");
		System.out.println("updateAT : " + modifiedExpired_in2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date dt = new Date();
		String Keterangan = " ";
		long lama = -1;
		long Expire = -1;
		String Expired = null;
		try {
			
			Date tgl = sdf.parse(modifiedExpired_in2);
			System.out.println("parse "+tgl);
			//Date dt = sdf.parse(dt);
			//Date c1 = (Date) sdf.parse(c1);
			//int days = Days.daysBetween(tgl, dt).getDays();
			lama = (int) Math.round((tgl.getTime()- dt.getTime()+25200000));
			//Expire = lama; 
			System.out.println("hitung");
			System.out.println(dt);
			System.out.println(lama);
			
			if (lama > 86400000){
				
				lama = (int) Math.round((tgl.getTime()-dt.getTime()+25200000) / (double)86400000);
				Expire = lama;
				Keterangan = " days left";
				Expired = Expire+Keterangan;
				System.out.println("hari");
			}
			
			else if (lama  > 3600000){
				//perjam
				//jika lebih dari 6 hari berarti expirednya tinggal 1 hari maka dibagi 3600000 biar dapetinnya per jam
				// 168 itu 7 x 24 jam 
				lama = (int) Math.round((tgl.getTime()-dt.getTime()+25200000)/ (double) 3600000);
				Expire =  lama;
				Keterangan = " hours left";
				Expired = Expire+Keterangan;
				System.out.println("jam");
			}
			else if (lama > 60000){
				//permenit
				//jika lebih dari 6 hari 23 jam 
				// 167 jam ke milisecond = 601,200,000
				// dibagi 60000 biar dapetinnya per menit
				//10080 itu 167 jam x 60 menit
				lama = (int) Math.round((tgl.getTime()-dt.getTime()) / (double)60000);
				Expire =  lama;
				Keterangan = " minutes left";
				Expired = Expire+Keterangan;
				System.out.println("menit");
			}
			
			else {
				//per second
				lama = (int) Math.round((tgl.getTime()-dt.getTime()+25200000) / (double)1000);
				Expire =  lama;
				System.out.println("lama" +lama);
				System.out.println("expire" + Expire);
				Keterangan = " seconds left";
				Expired = Expire+Keterangan;
				System.out.println("detik");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Expired;
		
	}
}
