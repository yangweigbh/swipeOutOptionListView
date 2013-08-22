package com.yangwei.swipeoutoptionlistviewexample;

import com.yangwei.swipeOutOptionAdapter;
import com.yangwei.swipeOutOptionListView;
import com.yangwei.swipeOutOptionListView.onOptionItemClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends Activity {
	swipeOutOptionListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (swipeOutOptionListView) findViewById(R.id.list1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Cheeses.sCheeseStrings);
		listView.setAdapter(adapter);
		ImageView image1 = new ImageView(this);
		image1.setImageResource(android.R.drawable.ic_media_next);
		
		ImageView image2 = new ImageView(this);
		image2.setImageResource(android.R.drawable.ic_media_play);
		
		
		ImageView image3 = new ImageView(this);
		image3.setImageResource(android.R.drawable.ic_media_previous);
		
		listView.addOptionsItem(image1, new onOptionItemClickListener() {
			
			@Override
			public void onClick(View view, int position) {
				Log.d("Activity", "image1 clicked: " + view + " position: " + position);
				
			}
		});
		listView.addOptionsItem(image2, new onOptionItemClickListener() {
			
			@Override
			public void onClick(View view, int position) {
				Log.d("Activity", "image2 clicked "+ view + " position: " + position);
				
			}
		});

		listView.addOptionsItem(image3, new onOptionItemClickListener() {
			
			@Override
			public void onClick(View view, int position) {
				Log.d("Activity", "image3 clicked " + view + " position: " + position);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
