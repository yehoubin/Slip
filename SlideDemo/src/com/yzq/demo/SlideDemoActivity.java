package com.yzq.demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author yzq demo说明：第一个自动往下滑，第4个也就是第二排第二个可以上滑也可以下滑 代码全在下面 有问题欢迎加Q
 *         454079657一起讨论
 * 
 */
public class SlideDemoActivity extends Activity implements
		cn.aedu.scrolllayout.OnViewChangeListener, OnTouchListener {

	private int begin_x = 0, end_x = 0, begin_y = 0, end_y = 0;
	private RelativeLayout rl04;
	private TextView tv1, tv4;
	private cn.aedu.scrolllayout.MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private LinearLayout pointLLayout;
	private ImageView b1,  b4;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// mGestureDetector = new GestureDetector(this);
		initView();

		new getDemoTask().execute();
	}

	private void initView() {
		mScrollLayout = (cn.aedu.scrolllayout.MyScrollLayout) findViewById(R.id.ScrollLayout_main);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout_main);

		tv1 = (TextView) findViewById(R.id.tv_main_01_01);
		tv4 = (TextView) findViewById(R.id.tv_main_01_04);

		rl04 = (RelativeLayout) findViewById(R.id.rl_main_01_04);
		rl04.setOnTouchListener(this);
		rl04.setClickable(true);

		b1 = (ImageView) findViewById(R.id.btn_main_01_01);
		b4 = (ImageView) findViewById(R.id.btn_main_01_04);
		b4.setOnTouchListener(this);
		b4.setClickable(true);

		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(SlideDemoActivity.this);
		
	}

	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	private void setcurrentPoint(int position) {
		Button page = (Button) findViewById(R.id.btn_main_page);
		Button page2 = (Button) findViewById(R.id.btn_main_page2);
		Animation mianAnimation2 = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.main_toptobottom);
		Animation mianAnimation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.main_toptobottom1);
		if (position == 0) {
			page.setVisibility(View.VISIBLE);
			page2.setAnimation(mianAnimation);
			page.setAnimation(mianAnimation2);
			page2.setVisibility(View.INVISIBLE);
		}
		if (position == 1) {
			page2.setVisibility(View.VISIBLE);
			page.setAnimation(mianAnimation);
			page2.setAnimation(mianAnimation2);
			page.setVisibility(View.INVISIBLE);
		}
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}

	private class getDemoTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPostExecute(String result) {

			if (result != null) {
				if (result.length() > 18) {
					result = result.substring(0, 15) + "...";
				}

				RelativeLayout rl01 = (RelativeLayout) findViewById(R.id.rl_main_01_01);
				Animation mianAnimation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.main_toptobottom1);
				b1.setAnimation(mianAnimation);
				b1.setVisibility(View.INVISIBLE);
				Animation mianAnimation2 = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.main_toptobottom);
				rl01.setAnimation(mianAnimation2);
				tv1.setText("最新资讯:" + result);
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			/*
			 * 网络获取数据的耗时操作
			 */
			String s = "这只是一个Demo";
			return s;
		}

	}

	private class getDemo2Task extends AsyncTask<String, Void, String> {

		@Override
		protected void onPostExecute(String result) {

			if (result != null) {

				tv4.setText(result);
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			tv4.setText("加载中...");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {

			/*
			 * 网络获取数据的耗时操作
			 */
			String s = "这真的只是一个Demo";
			return s;
		}

	}

	public boolean onTouch(View v, MotionEvent event) {

		mScrollLayout.baseAnimation(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			begin_x = (int) event.getRawX();
			begin_y = (int) event.getRawY();
			return false;
		case MotionEvent.ACTION_MOVE:
			end_x = (int) event.getRawX() - begin_x;
			end_y = (int) event.getRawY() - begin_y;
			return false;
		case MotionEvent.ACTION_UP:
			if (end_y > 20) {
				switch (v.getId()) {
				case R.id.btn_main_01_04:
					Animation mianAnimation = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.main_toptobottom1);
					b4.setAnimation(mianAnimation);
					b4.setVisibility(View.INVISIBLE);
					Animation mianAnimation2 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.main_toptobottom);
					rl04.setVisibility(View.VISIBLE);
					rl04.setAnimation(mianAnimation2);
					new getDemo2Task().execute();
					break;
				default:
					break;
				}

			} else if (end_x < -20 || end_x > 20) {
			} else if (end_y < -20) {
				switch (v.getId()) {

				case R.id.rl_main_01_04:
					Animation mianAnimation = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.main_bottomtotop);
					Animation mianAnimation2 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.main_bottomtotop1);
					b4.setVisibility(View.VISIBLE);
					rl04.setAnimation(mianAnimation2);
					rl04.setVisibility(View.INVISIBLE);
					b4.setAnimation(mianAnimation);
					break;
				default:
					break;
				}
			}
			return false;

		default:
			break;
		}

		return false;
	}

}