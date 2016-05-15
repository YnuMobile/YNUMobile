package com.imooc.YnuMobile.RollViewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imooc.YnuMobile.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class RollViewPager2 extends ViewPager {

	private String TAG = "RollViewPager";
	private Context context;
	private int currentItem;
	private ArrayList<String> uriList;
	private ArrayList<View> dots;
	private TextView title;
	private String[] titles;
	private int[] resImageIds;
	private int dot_focus_resId;
	private int dot_normal_resId;
	private OnPagerClickCallback onPagerClickCallback;
	private boolean isShowResImage = false;
	MyOnTouchListener myOnTouchListener;
	ViewPagerTask viewPagerTask;

	private long start = 0;

	public RollViewPager2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		viewPagerTask = new ViewPagerTask();
		myOnTouchListener = new MyOnTouchListener();
	}

	public RollViewPager2(Context context) {
		super(context);
		this.context = context;

		viewPagerTask = new ViewPagerTask();
		myOnTouchListener = new MyOnTouchListener();
	}

	public class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				start = System.currentTimeMillis();
				handler.removeCallbacksAndMessages(null);
				break;
			case MotionEvent.ACTION_MOVE:
				handler.removeCallbacks(viewPagerTask);
				break;
			case MotionEvent.ACTION_CANCEL:
				startRoll();
				break;
			case MotionEvent.ACTION_UP:
				long duration = System.currentTimeMillis() - start;
				if (duration <= 400) {
					if (onPagerClickCallback != null)
						onPagerClickCallback.onPagerClick(currentItem);
				}
				startRoll();
				break;
			}
			return true;
		}
	}

	public class ViewPagerTask implements Runnable {
		@Override
		public void run() {
			currentItem = (currentItem + 1)
					% (isShowResImage ? resImageIds.length : uriList.size());
			handler.obtainMessage().sendToTarget();
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setCurrentItem(currentItem);
			startRoll();
		}
	};

	public void setDot(ArrayList<View> dots, int dot_focus_resId,
			int dot_normal_resId) {
		this.dots = dots;
		this.dot_focus_resId = dot_focus_resId;
		this.dot_normal_resId = dot_normal_resId;
	}

	public void setPagerCallback(OnPagerClickCallback onPagerClickCallback) {
		this.onPagerClickCallback = onPagerClickCallback;
	}

	public void setUriList(ArrayList<String> uriList) {
		isShowResImage = false;
		this.uriList = uriList;
	}

	public void setResImageIds(int[] resImageIds) {
		isShowResImage = true;
		this.resImageIds = resImageIds;
	}

	public void setTitle(TextView title, String[] titles) {
		this.title = title;
		this.titles = titles;
		if (title != null && titles != null && titles.length > 0)
			title.setText(titles[0]);
	}

	private boolean hasSetAdapter = false;
	private final int SWITCH_DURATION = 3500;

	public void startRoll() {
		if (!hasSetAdapter) {
			hasSetAdapter = true;
			this.setOnPageChangeListener(new MyOnPageChangeListener());
			this.setAdapter(new ViewPagerAdapter());
		}
		handler.postDelayed(viewPagerTask, SWITCH_DURATION);
	}

	class MyOnPageChangeListener implements OnPageChangeListener {
		int oldPosition = 0;

		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			if (title != null)
				title.setText(titles[position]);
			if (dots != null && dots.size() > 0) {
				dots.get(position).setBackgroundResource(dot_focus_resId);
				dots.get(oldPosition).setBackgroundResource(dot_normal_resId);
			}
			oldPosition = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	class ViewPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return isShowResImage ? resImageIds.length : uriList.size();
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			View view = View.inflate(context, R.layout.viewpager_item, null);
			((ViewPager) container).addView(view);
			view.setOnTouchListener(myOnTouchListener);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			if (isShowResImage) {
				imageView.setImageResource(resImageIds[position]);
			} else {
				ImageLoader imageLoader=ImageLoader.getInstance();
				imageLoader.init(ImageLoaderConfiguration.createDefault(context));
				ImageLoader.getInstance().displayImage(uriList.get(position),
						imageView, ImageCacheUtil.OPTIONS.default_options);
			}
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		handler.removeCallbacksAndMessages(null);
		super.onDetachedFromWindow();
	}

	interface OnPagerClickCallback {
		public abstract void onPagerClick(int position);
	}

	/*
	*
	* imageLoader.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
	* */
}
