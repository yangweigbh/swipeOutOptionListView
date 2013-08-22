package com.yangwei;
import java.util.ArrayList;

import com.yangwei.swipeOutOptionListView.State;
import com.yangwei.swipeoutoptionlistview.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class swipeOutOptionItemView extends FrameLayout {
	private final String DEBUG_TAG = "swipeOutOptionItemView";
	private Context mContext;
	private View mContentView;
	
    
    public swipeOutOptionItemView(Context context) {
    	super(context);
    	init(context, null);
	}

	public swipeOutOptionItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	private void init(Context context, AttributeSet attrs) {
		mContext = context;
	}

	public void setContentView(View contentView) {
		mContentView = contentView;
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(contentView, lp);
	}

	public void removeContentView() {
		removeView(mContentView);
		
	}
	
	public void showOptionsView(boolean showAnimation) {
		showOptionsView(showAnimation, null);
	}
	
	public void showOptionsView(boolean showAnimation, ViewGroup parent) {
		Log.d(DEBUG_TAG, "this: " + this + " showOptionsView called");
		swipeOutOptionListView listView;
		if (parent == null) {
			listView = (swipeOutOptionListView) swipeOutOptionItemView.this.getParent();
		} else {
			listView = (swipeOutOptionListView)parent;
		}
		LinearLayout optionsView = listView.getOptionsView();
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(optionsView, lp);
		if (optionsView.getVisibility() == VISIBLE) {
			return;
		}
		if (showAnimation) {
			optionsView.startAnimation(listView.getInAnimation());
		}
		optionsView.setVisibility(VISIBLE);
		for (int i = 0; i < optionsView.getChildCount(); i++) {
			Animation anim = (Animation) AnimationUtils.loadAnimation(mContext, R.anim.scale_anim);
			anim.setInterpolator(new AccelerateInterpolator());
			optionsView.getChildAt(i).startAnimation(anim);
		}
		listView.setState(State.OPTIONED);
		listView.setOptionedView(swipeOutOptionItemView.this);
		
	}

	public void hideOptionsView(boolean showAnimation) {
		hideOptionsView(showAnimation, null);
	}

	public void hideOptionsView(boolean showAnimation, ViewGroup parent) {
		Log.d(DEBUG_TAG, "this: " + this + " hideOptionsView called");
		
		swipeOutOptionListView listView;
		if (parent == null) {
			listView = (swipeOutOptionListView) swipeOutOptionItemView.this.getParent();
		} else {
			listView = (swipeOutOptionListView)parent;
		}
		Log.d(DEBUG_TAG, "listview: " + listView);
		LinearLayout optionsView = listView.getOptionsView();
		
		if (optionsView.getVisibility() == GONE) {
			return;
		}
		if (showAnimation) {
			optionsView.startAnimation(listView.getOutAnimation());
		}
		optionsView.setVisibility(GONE);
		removeView(optionsView);
		Log.d(DEBUG_TAG, "this: " + this + " removeView called");
		
		listView.setState(State.NORMAL);
		
	}

	
	
}
