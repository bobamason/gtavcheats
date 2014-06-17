package com.bobamason.gtavcheats;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ActionBarActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

	private ViewPager mViewPager;
	private FragmentManager fmanager;
	private ViewPagerAdapter pagerAdapter;
	public PlayerFragment playerFrag;
	public AllFragment allFrag;
	public WeaponFragment weaponFrag;
	public VehicleFragment vehicleFrag;
	private ActionBar actionBar;
	private static final String[] consoles = { "PS3", "Xbox 360" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		fmanager = getSupportFragmentManager();
		pagerAdapter = new ViewPagerAdapter(fmanager);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(4);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, consoles),
				new ActionBar.OnNavigationListener() {

					@Override
					public boolean onNavigationItemSelected(int pos, long arg1) {
						if (pos == 0) {
							return true;
						} else if (pos == 1) {
							return true;
						} else {
							return false;
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {

	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;

			switch (arg0) {
			case 0:
				allFrag = new AllFragment();
				fragment = allFrag;
				break;
			case 1:
				playerFrag = new PlayerFragment();
				fragment = playerFrag;
				break;
			case 2:
				weaponFrag = new WeaponFragment();
				fragment = weaponFrag;
				break;
			case 3:
				vehicleFrag = new VehicleFragment();
				fragment = vehicleFrag;
				break;
			default:
				break;
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
