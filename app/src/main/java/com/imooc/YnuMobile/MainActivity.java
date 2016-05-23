package com.imooc.YnuMobile;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.imooc.YnuMobile.Fragment.Found;
import com.imooc.YnuMobile.Fragment.Home;
import com.imooc.YnuMobile.Fragment.User;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends FragmentActivity {

	ViewPager viewPager;
	CoordinatorLayout myCoordinator;

	private BottomBar mBottomBar;
	private List<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager= (ViewPager) findViewById(R.id.viewPager);
		viewPager.setOffscreenPageLimit(2);
		myCoordinator= (CoordinatorLayout) findViewById(R.id.mCoordinatorLayout);
		initViewPager();
		createBottomBar(savedInstanceState);
	}

	private void createBottomBar(Bundle savedInstanceState) {
		mBottomBar = BottomBar.attachShy(myCoordinator,viewPager, savedInstanceState);
		mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
			@Override
			public void onMenuTabSelected(@IdRes int menuItemId) {
				switch (menuItemId) {
					case R.id.bb_menu_recents:
						viewPager.setCurrentItem(0);
						break;
					case R.id.bb_menu_favorites:
						viewPager.setCurrentItem(1);
						break;
					case R.id.bb_menu_nearby:
						viewPager.setCurrentItem(2);
						break;
				}
			}

			@Override
			public void onMenuTabReSelected(@IdRes int menuItemId) {

			}
		});

		// Setting colors for different tabs when there's more than three of them.
		// You can set colors for tabs in three different ways as shown below.
		mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
		mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
		mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.colorAccent));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Necessary to restore the BottomBar's state, otherwise we would
		// lose the current tab on orientation change.
		mBottomBar.onSaveInstanceState(outState);
	}

	private void initViewPager() {
		fragmentList = new ArrayList<>();
		fragmentList.add(new Home());
		fragmentList.add(new Found());
		fragmentList.add(new User());
		viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return fragmentList.get(position);
			}

			@Override
			public int getCount() {
				return fragmentList.size();
			}
		});
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				mBottomBar.selectTabAtPosition(position, true);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			SweetAlertDialog exitDialog=new SweetAlertDialog(MainActivity.this,SweetAlertDialog.NORMAL_TYPE);
			exitDialog.setTitleText("退出智慧云大？");
			exitDialog.setCancelable(true);
			exitDialog.setCancelText("No");
			exitDialog.setConfirmText("Yes");
			exitDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
				@Override
				public void onClick(SweetAlertDialog sweetAlertDialog) {
					finish();
					System.exit(0);
				}
			});
			exitDialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}
}
