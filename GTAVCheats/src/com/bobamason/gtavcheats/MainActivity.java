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
import android.preference.*;
import android.content.SharedPreferences.*;
import android.support.v4.widget.*;
import android.content.res.*;
import android.support.v4.widget.DrawerLayout.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

	private FragmentManager fmanager;

	private ActionBar actionBar;

	DrawerLayout drawLayout;

	private SharedPreferences prefs;

	private ActionBarDrawerToggle drawListener;

	private ListView drawerListPS3;

	private ListView drawerListXBox;

	private static final String[] DRAWER_LABELS = {"All Cheats", "Player Cheats", "Weapons Cheats", "Vehicle Cheats", "World Cheats"};

	private DatabaseAdapter dbAdapter;

	private Button favoritesButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		dbAdapter = new DatabaseAdapter(this);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		fmanager = getSupportFragmentManager();

		addFragment(R.drawable.main_menu_bg, R.array.all_titles, R.array.ps3_all_codes, true);

		drawLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout1);
		drawListener = new ActionBarDrawerToggle(this, drawLayout, R.drawable.ic_drawer, 
												 R.string.drawer_opened, R.string.drawer_closed){
			public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
		};
		
		favoritesButton = (Button) findViewById(R.id.fav_button);
		favoritesButton.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					actionBar.setTitle("Favorites");
					FragmentTransaction transaction = fmanager.beginTransaction();
					transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
					transaction.replace(R.id.fragment_holder, new FavoritesFragment());
					transaction.commit();
					drawLayout.closeDrawers();
				}
			});
		
		drawerListPS3 = (ListView) findViewById(R.id.drawerListViewPS3);
		drawerListXBox = (ListView) findViewById(R.id.drawerListViewXbox);
		drawerListPS3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
														  DRAWER_LABELS));
		drawerListPS3.setOnItemClickListener(this);
		drawerListXBox.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 
														   DRAWER_LABELS));
		drawerListXBox.setOnItemClickListener(this);
		
		drawLayout.setDrawerListener(drawListener);
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.drawerListViewPS3) {
			switch (position) {
				case 0:
					replaceFragment(position, R.drawable.main_menu_bg, R.array.all_titles, R.array.ps3_all_codes, true);
					break;
				case 1:
					replaceFragment(position, R.drawable.player_bg, R.array.player_titles, R.array.ps3_player_codes, true);
					break;
				case 2:
					replaceFragment(position, R.drawable.weapons_bg, R.array.weapons_titles, R.array.ps3_weapon_codes, true);
					break;
				case 3:
					replaceFragment(position, R.drawable.vehicles_bg, R.array.vehicle_titles, R.array.ps3_vehicle_codes, true);
					break;
				case 4:
					replaceFragment(position, R.drawable.world_bg, R.array.world_titles, R.array.ps3_world_codes, true);
					break;
				default:
					break;
			}
		} else if (parent.getId() == R.id.drawerListViewXbox) {
			switch (position) {
				case 0:
					replaceFragment(position, R.drawable.main_menu_bg, R.array.all_titles, R.array.xbox_all_codes, false);
					break;
				case 1:
					replaceFragment(position, R.drawable.player_bg, R.array.player_titles, R.array.xbox_player_codes, false);
					break;
				case 2:
					replaceFragment(position, R.drawable.weapons_bg, R.array.weapons_titles, R.array.xbox_weapon_codes, false);
					break;
				case 3:
					replaceFragment(position, R.drawable.vehicles_bg, R.array.vehicle_titles, R.array.xbox_vehicle_codes, false);
					break;
				case 4:
					replaceFragment(position, R.drawable.world_bg, R.array.world_titles, R.array.xbox_world_codes, false);
					break;
				default:
					break;
			}
		}
	}

	private void addFragment(int bgResId, int titlesId, int cheatsId, boolean isPS3) {

		Bundle b = new Bundle();
		b.putInt(Keys.BG_RESID, bgResId);
		b.putInt(Keys.TITLES_RESID, titlesId);
		b.putInt(Keys.CHEATS_RESID, cheatsId);
		b.putBoolean(Keys.IS_PS3, isPS3);
		CheatsFragment frag = new CheatsFragment();
		frag.setArguments(b);
		FragmentTransaction transaction = fmanager.beginTransaction();
		transaction.add(R.id.fragment_holder, frag);
		transaction.commit();
	}

	private void replaceFragment(int position, int bgResId, int titlesId, int cheatsId, boolean isPS3) {
		drawerListPS3.setItemChecked(position, true);
		actionBar.setTitle((isPS3 ? "PS3 " : "XBox ") + DRAWER_LABELS[position]);
		Bundle b = new Bundle();
		b.putInt(Keys.BG_RESID, bgResId);
		b.putInt(Keys.TITLES_RESID, titlesId);
		b.putInt(Keys.CHEATS_RESID, cheatsId);
		b.putBoolean(Keys.IS_PS3, isPS3);
		CheatsFragment frag = new CheatsFragment();
		frag.setArguments(b);
		FragmentTransaction transaction = fmanager.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		transaction.replace(R.id.fragment_holder, frag);
		transaction.commit();
		drawLayout.closeDrawers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawListener.onOptionsItemSelected(item)) {
			return true;
		}
		if(item.getItemId() == R.id.exit){
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onPostCreate(savedInstanceState);
		drawListener.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO: Implement this method
		super.onConfigurationChanged(newConfig);
		drawListener.onConfigurationChanged(newConfig);
	}
	
	public DatabaseAdapter getDbAdapter(){
		return dbAdapter;
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
