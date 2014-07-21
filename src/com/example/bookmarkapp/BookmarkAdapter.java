package com.example.bookmarkapp;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BookmarkAdapter extends ArrayAdapter<Bookmark> {

	private Context context;
	private Bookmark bookmark;
	private int resourceLayout;
	private OnDeleteListener onDeleteListener;
	private int p;
	
	public void getOneWeekfromNow() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		System.out.println(now);
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		java.util.Date OneWeekFromNow = calendar.getTime();
		System.out.println(OneWeekFromNow);
	}

	public BookmarkAdapter(Context context, int resource, List<Bookmark> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.resourceLayout = resource;
		this.onDeleteListener = (OnDeleteListener) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		bookmark = getItem(position);
		p=position;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(resourceLayout, parent, false);

		// TextView textTopik = (TextView)
		// convertView.findViewById(R.id.TextTopik);
		// TextView textRoom = (TextView)
		// convertView.findViewById(R.id.TextRoom);
		 TextView textTopik = (TextView)
		 convertView.findViewById(R.id.topic);
		// TextView textEmail = (TextView)
		// convertView.findViewById(R.id.TextEmail);
		TextView textName = (TextView) convertView.findViewById(R.id.TextName);
		//TextView textMessage = (TextView) convertView
		//		.findViewById(R.id.textMessage);
		TextView textExpired = (TextView) convertView
				.findViewById(R.id.TextExpired);
		TextView textUpdated_at = (TextView) convertView
				.findViewById(R.id.updated_at);
		
//		ImageButton buttondelete = (ImageButton) convertView
//				.findViewById(R.id.delete);
//
//		buttondelete.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				System.out.println("delete");
//			}
//		});
		Button delete = (Button) convertView.findViewById(R.id.button1);
		delete.setTag(position);
		delete.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				Log.w("get", "get " + view.getTag());
				
				onDeleteListener.OnDelete(Integer.valueOf(view.getTag().toString()));
			}
		});

		// textTopik.setText(bookmark.getTopic_id());
		// textRoom.setText(bookmark.getRoom_id());
		 textTopik.setText(bookmark.getRoom_id());
		// textEmail.setText(bookmark.getCommenter_email());
		textName.setText(bookmark.getCommenter_name());
		//textMessage.setText(bookmark.getMessage());
		textUpdated_at.setText(bookmark.getUpdated_at());
		String Expired = String.valueOf(bookmark.getExpired());
		textExpired.setText(Expired);
		return convertView;

	}

}
