package com.yangwei;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.commonsware.cwac.adapter.AdapterWrapper;


public class swipeOutOptionAdapter extends AdapterWrapper{

	private Context mContext;
	
	public swipeOutOptionAdapter(Context context, ListAdapter wrapped) {
		super(wrapped);
		mContext = context;
	}
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		swipeOutOptionItemView view;
		
		
		if (convertView  == null) {
			view = new swipeOutOptionItemView(mContext);
			View contentView = super.getView(position, null, view);
			view.setTag(contentView);
			view.setContentView(contentView);
		} else {
			view = (swipeOutOptionItemView) convertView;
			view.removeContentView();
			//view.hideOptionsView(false, parent);
			view.setContentView(super.getView(position, (View)view.getTag(), view));
		}
		
		
		
		return view;
	}
}
