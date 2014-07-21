package com.example.bookmarkapp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnDeleteListener {

	private ListView ListViewBookmark;
	private ArrayList<Bookmark> ListBookmark = new ArrayList<Bookmark>();
	private BookmarkAdapter bookmarkAdapter;
	private String myToken;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookmark);
		myToken = getIntent().getStringExtra("token");
		getOverflowMenu();
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#71CD9E"));     
        ab.setBackgroundDrawable(colorDrawable);
        
		ListViewBookmark = (ListView) findViewById(R.id.listView1);

		 ListViewBookmark
		 .setOnItemLongClickListener(new OnItemLongClickListener() {
		
			 //buat extend
		 @Override
		 public boolean onItemLongClick(AdapterView<?> arg0,
		 View arg1, int arg2, long arg3) {
		 // TODO Auto-generated method stub
		 Bookmark b = (Bookmark) ListViewBookmark
		 .getItemAtPosition(arg2);
		 // Toast.makeText(MainActivity.this, b.message,
		 // Toast.LENGTH_LONG).show();
		 longclick(b);
		 return true;
		 }
		
		 });
		
		 //buat delete
//			ListViewBookmark.findViewById(R.id.button1);
//			
//			ListViewBookmark.setOnItemClickListener(new OnItemClickListener() {
//				
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3){
//					// TODO Auto-generated method stub
//					Bookmark d = (Bookmark) ListViewBookmark
//						.getItemAtPosition(arg2);
//
//					//shortclick(c);
//					System.out.println("get");
//				}
//			});
		 
		 
		 //buat next page
		 ListViewBookmark = (ListView) findViewById(R.id.listView1);
		
		ListViewBookmark.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
//			 TODO Auto-generated method stub
        		Bookmark c = (Bookmark) ListViewBookmark.getItemAtPosition(arg2);
        		
        		goesToNextActivity(c, c.room_name,c.topic_title,c.commenter_name,c.message, c.updated_at,c.commenter_email, c.id);
				System.out.println("detail2");
				//return;
       	}
		});

		bookmarkAdapter = new BookmarkAdapter(this, R.layout.item_bookmark,
				ListBookmark);
		ListViewBookmark.setAdapter(bookmarkAdapter);

		GetBookmark getBookmark = new GetBookmark() {

			@Override
			public void respon(String respons) {
				
				// TODO Auto-generated method stub
				Log.w("json", respons);

				try {
					JSONArray myFiles = new JSONArray(respons);

					// JSONObject objBookmark=new JSONObject(respons);
					// JSONArray
					// arrayBookmark=objBookmark.getJSONArray("listBookmark");

					for (int i = 0; i < myFiles.length(); i++) {
						JSONObject objectBookmark = myFiles.getJSONObject(i);

						String id = objectBookmark.getString("id");
						String topik = objectBookmark.getString("topic_title");
						
						String room = objectBookmark.getString("room_name");
						String date = objectBookmark.getString("updated_at");
						String expired_in = objectBookmark.getString("expired_in");
						String email = objectBookmark
								.getString("commenter_email");
						String name = objectBookmark
								.getString("commenter_name");
						String message = objectBookmark.getString("message");

						Bookmark singleBookmark = new Bookmark(id, topik, room,
								date, email, name, message, expired_in);
						ListBookmark.add(singleBookmark);
					}
					bookmarkAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		getBookmark
				.execute("https://www.qisc.us/api/v1/mobile/getBookmarks?token="
						+ myToken);
	}

	//method extend
	public void longclick(final Bookmark b) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Extend expiry date")
				.setMessage("Want to extend your expiry date?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// Toast.makeText(MainActivity.this,
								// "Yes button pressed",
								// Toast.LENGTH_SHORT).show();
								// ExtendExpiry(b);
								UpdateDateAsync UpdateDateAsync = new UpdateDateAsync(b) {
									@Override
									public void respon(String result) {
										System.out.println(result);
									}
								};
								UpdateDateAsync.execute();
								Intent intent = getIntent();
							    finish();
							    startActivity(intent);
							}
							
						}).setNegativeButton("No", null).show();
	}
	
	//buat delete button
		public void deleteBookmark(final Bookmark d) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Delete Bookmark")
					.setMessage("Are you sure want to delete this bookmark?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									
									UpdateBookmarkAsync UpdateBookmarkAsync = new UpdateBookmarkAsync(d) {
										@Override
										public void respon(String result) {
											System.out.println(result);
										}
									};
									UpdateBookmarkAsync.execute();
									Intent intent = getIntent();
								    finish();
								    startActivity(intent);
								}
								
							}).setNegativeButton("No", null).show();
		}


	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		 
	    switch (item.getItemId()) {
	 
	        case R.id.refresh:
	            showToast("Refresh was clicked.");
	            Intent intent = getIntent();
			    finish();
			    startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	            
	    }
	}


	private void showToast(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void getOverflowMenu() {
		 
	    try {
	 
	       ViewConfiguration config = ViewConfiguration.get(this);
	       java.lang.reflect.Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	       if(menuKeyField != null) {
	           menuKeyField.setAccessible(true);
	           menuKeyField.setBoolean(config, false);
	       }
	       
	   } catch (Exception e) {
	       e.printStackTrace();
	   }
	 
	}
	
	//abstract delete
	abstract class UpdateBookmarkAsync extends AsyncTask<String, String, String> {
		private Bookmark d;
		public abstract void respon(String result);
		
		UpdateBookmarkAsync(Bookmark d){
			this.d=d;
		}
		
		protected String doInBackground(String... arg0) {
			
			System.out.println(d.id);
			ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
			kirimkephp.add(new BasicNameValuePair("token", myToken.toString()));
			kirimkephp.add(new BasicNameValuePair("id", d.id.toString()));
			String respon = null;
			System.out.println("kirim");
			try {

				respon = ClientToServer.eksekusiHttpPost(
						"https://www.qisc.us/api/v1/mobile/deleteBookmark?",
						kirimkephp);
				JSONObject objectAuth = new JSONObject(respon);
				System.out.println("respon");
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			return respon;
			
		}
	}
	
	
	
	
//abstract extend
abstract class UpdateDateAsync extends AsyncTask<String, String, String> {
	private Bookmark b;
	public abstract void respon(String result);
	
	UpdateDateAsync(Bookmark b){
		this.b=b;
	}
	
	//asyntask extend
	protected String doInBackground(String... arg0) {

		ArrayList<NameValuePair> kirimkephp = new ArrayList<NameValuePair>();
		kirimkephp.add(new BasicNameValuePair("token", myToken.toString()));
		kirimkephp.add(new BasicNameValuePair("id", b.id.toString()));
		String respon = null;
		System.out.println("kirim");
		try {

			respon = ClientToServer.eksekusiHttpPost(
					"https://www.qisc.us/api/v1/mobile/addBookmark?",
					kirimkephp);
			JSONObject objectAuth = new JSONObject(respon);
			System.out.println("respon");
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return respon;
		
	}
		
}
	
//method next page
public void goesToNextActivity(Bookmark c,String room,String topic,String commenter_name, String message,String update_at,String email,String id){
	Intent intent = new Intent(MainActivity.this,Detail.class);
	intent.putExtra("room_name",room);
	intent.putExtra("topic_title",topic);
	intent.putExtra("commenter_name",commenter_name);
	intent.putExtra("message",message);
	intent.putExtra("updated_at",update_at);
	intent.putExtra("commenter_email", email);
	intent.putExtra("token", myToken);
	intent.putExtra("id",id);	
	startActivity(intent);
	System.out.println("detail");
	//finish();
}

@Override
public void OnDelete(int posisi) {
	// TODO Auto-generated method stub
	Log.w("posisi >>", posisi + "");
	deleteBookmark(ListBookmark.get(posisi));
	System.out.println("delete");
}

}
