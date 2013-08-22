#SwipeOutOptionListView

An android custom listview which looks like the notes list in Evernote (for iOS).


##Dependency
this project depend on [CWAC AdapterWrapper](https://github.com/yangweigbh/cwac-adapter)


##Usage

If you want to use this library you must before all indicate to your application
that you want to use it by launching the following command from the root
directory of your application


To setup the listview:

``` java
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
```


##TODO



##Author

**yangweigbh**

I'm glad to make friends with the people who persist in faith and follow their dreams.

Please let me know you are around.






