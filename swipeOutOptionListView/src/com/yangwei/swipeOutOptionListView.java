package com.yangwei;


import com.yangwei.swipeoutoptionlistview.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;


public class swipeOutOptionListView extends ListView {
	private final String DEBUG_TAG = "swipeOutOptionListView";
	private State mState;
	public enum State {NORMAL, OPTIONED};
	private int optionedPosition = -1;
	private swipeOutOptionItemView optionedView;
	private GestureDetector mDetector;
	private LinearLayout optionsView;
	private Animation inAnimation;
	private Animation outAnimation;
	private Handler handler;
	private Context mContext;
	private boolean mRequestDelay;
    

	public void setOptionsViewInAnimation(Animation anim) {
    	inAnimation = anim;
    }
    
    public void setOptionsViewOutAnimation(Animation anim) {
    	outAnimation = anim;
    }
    
    public Animation getInAnimation() {
		return inAnimation;
	}
    
    public Animation getOutAnimation() {
		return outAnimation;
	}

	public swipeOutOptionListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mState = State.NORMAL;
		mDetector = new GestureDetector(context, new MySimpleOnGestureListener());
		setupOptionsView(context);
		handler = new Handler(Looper.getMainLooper());
	}
	
	private void setupOptionsView(Context context) {
		optionsView = new LinearLayout(context);
		optionsView.setOrientation(LinearLayout.HORIZONTAL);
		optionsView.setBackgroundColor(0x99FF0000);//###TO be deleted
		optionsView.setVisibility(GONE);
		
		setOptionsViewInAnimation((Animation) AnimationUtils.loadAnimation(context, R.anim.in_animation));
		setOptionsViewOutAnimation((Animation) AnimationUtils.loadAnimation(context, R.anim.out_animation));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		mDetector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(new swipeOutOptionAdapter(mContext, adapter));
	}

	
	void setState(State newstate) {
		mState = newstate;
	}
	
	void setOptionedView(swipeOutOptionItemView view) {
		optionedView = view;
	}
	
	public class MySimpleOnGestureListener extends SimpleOnGestureListener {
		
		@Override
		public boolean onDown(MotionEvent e) {
			Log.d(DEBUG_TAG, "onDown: " + e.toString());
			Log.d(DEBUG_TAG, "mState: " + mState + " optionedView: " + optionedView + " optionedPostion: " + optionedPosition);
			
			return true;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.d(DEBUG_TAG, "onFling: " + e1.toString()+ e2.toString());
			Log.d(DEBUG_TAG, "mState: " + mState + " optionedView: " + optionedView + " optionedPostion: " + optionedPosition);
			if (optionedView != null) {
				optionedView.hideOptionsView(true);
				optionedView = null;
				optionedPosition = -1;
				requestDelay();
			}
			int position = pointToPosition((int)e1.getX(), (int)e1.getY());
			optionedPosition = position;
			int index = position - getFirstVisiblePosition();
			Log.d(DEBUG_TAG, "before showoptionlist optionsView.getVisibility()" + optionsView.getVisibility() + "optionsView.getparent" + optionsView.getParent());
			optionedView = ((swipeOutOptionItemView)getChildAt(index));
			
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					optionedView.showOptionsView(true);
					cancelDelay();
				}
			},  needDelay()?outAnimation.getDuration() + 100: 0);
			
			
			return true;
		}
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.d(DEBUG_TAG, "onSingleTapUp: " + e.toString());
			int position = pointToPosition((int)e.getX(), (int)e.getY());
			if (mState == State.OPTIONED) {
				if (position != optionedPosition) {
					optionedView.hideOptionsView(true);
					optionedView = null;
					optionedPosition = -1;

				}
			}
			return true;
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d(DEBUG_TAG, "onScroll: " + e1.toString());
//			if (mState == State.OPTIONED) {
//				optionedView.hideOptionsView(true);
//				optionedView = null;
//				optionedPosition = -1;
//			}
			return true;
		}
		
	}
	

	public void setOnOptionItemOnClickListener(View view, onOptionItemClickListener listener) {
		view.setOnClickListener(new OnClickListenerWrapper(listener));
	}
	
	private void requestDelay() {
		mRequestDelay = true;
	}
	
	private void cancelDelay() {
		mRequestDelay = false;
	}
	
	private boolean needDelay() {
		Log.d(DEBUG_TAG, "needDelay: " + mRequestDelay);
		return mRequestDelay;
	}

	public void addOptionsItem(View view, onOptionItemClickListener listener) {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1);
    	optionsView.addView(view, lp);
		setOnOptionItemOnClickListener(view, listener);
	}
	
	public int getOptionedPosition() {
		return optionedPosition;
	}
	
	public static interface onOptionItemClickListener {

		public void onClick(View view, int position);
		
	}
	
	private class OnClickListenerWrapper implements OnClickListener {

		private onOptionItemClickListener wrappedListener;
		
		public OnClickListenerWrapper(onOptionItemClickListener listener) {
			super();
			wrappedListener = listener;
		}
		
		@Override
		public void onClick(View v) {
			optionedView.hideOptionsView(true);
			wrappedListener.onClick(v, optionedPosition);
			
		}
		
	}

	public LinearLayout getOptionsView() {
		return optionsView;
		
	}
	
	public void setOptionsViewBackground(Drawable background) {
		optionsView.setBackgroundDrawable(background);
	}
	
	public void setOptionsViewBackgroundColor(int color) {
		optionsView.setBackgroundColor(color);
	}
	
	public void setOptionsViewBackgroundResource(int resid) {
		optionsView.setBackgroundResource(resid);
	}
	
}
