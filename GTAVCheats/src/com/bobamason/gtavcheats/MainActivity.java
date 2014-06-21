package com.bobamason.gtavcheats;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements
		AdapterView.OnItemClickListener {

	private FragmentManager fmanager;

	private ActionBar actionBar;

	DrawerLayout drawLayout;

	private SharedPreferences prefs;

	private ActionBarDrawerToggle drawListener;

	private ListView drawerListPS3;

	private ListView drawerListXBox;

	private static final String[] DRAWER_LABELS = { "All Cheats",
			"Player Cheats", "Weapons Cheats", "Vehicle Cheats", "World Cheats" };

	private static final String DEVICE_ID = "C9605E52BE66F514E81CC5AF54C72EBE";

	private DatabaseAdapter dbAdapter;

	private Button favoritesButton, psButton, xbButton;

	private int currentSelection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		dbAdapter = new DatabaseAdapter(this);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		currentSelection = prefs.getInt(Keys.SELECTION, 1);
		fmanager = getSupportFragmentManager();

		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(DEVICE_ID)
				.build();
		adView.loadAd(adRequest);

		drawLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout1);
		drawListener = new ActionBarDrawerToggle(this, drawLayout,
				R.drawable.ic_drawer, R.string.drawer_opened,
				R.string.drawer_closed) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		favoritesButton = (Button) findViewById(R.id.fav_button);
		favoritesButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				actionBar.setTitle("Favorites");
				FragmentTransaction transaction = fmanager.beginTransaction();
				transaction.setCustomAnimations(android.R.anim.fade_in,
						android.R.anim.fade_out);
				transaction.replace(R.id.fragment_holder,
						new FavoritesFragment());
				transaction.commit();
				drawLayout.closeDrawers();
				currentSelection = 0;
			}
		});

		drawerListPS3 = (ListView) findViewById(R.id.drawerListViewPS3);
		drawerListXBox = (ListView) findViewById(R.id.drawerListViewXbox);
		drawerListPS3.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, DRAWER_LABELS));
		drawerListPS3.setOnItemClickListener(this);
		drawerListXBox.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, DRAWER_LABELS));
		drawerListXBox.setOnItemClickListener(this);

		drawLayout.setDrawerListener(drawListener);

		psButton = (Button) findViewById(R.id.ps3_button);
		psButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (drawerListPS3.getVisibility() == View.GONE) {
					drawerListPS3.setVisibility(View.VISIBLE);
					psButton.setBackgroundResource(R.drawable.collapse_button);
				} else {
					drawerListPS3.setVisibility(View.GONE);
					psButton.setBackgroundResource(R.drawable.expand_button);
				}
			}
		});

		xbButton = (Button) findViewById(R.id.xbox_button);
		xbButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (drawerListXBox.getVisibility() == View.GONE) {
					drawerListXBox.setVisibility(View.VISIBLE);
					xbButton.setBackgroundResource(R.drawable.collapse_button);
				} else {
					drawerListXBox.setVisibility(View.GONE);
					xbButton.setBackgroundResource(R.drawable.expand_button);
				}
			}
		});

		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.getId() == R.id.drawerListViewPS3) {
			switch (position) {
			case 0:
				replaceFragment(position, R.drawable.main_menu_bg,
						R.array.all_titles, R.array.ps3_all_codes, true);
				currentSelection = 1;
				break;
			case 1:
				replaceFragment(position, R.drawable.player_bg,
						R.array.player_titles, R.array.ps3_player_codes, true);
				currentSelection = 2;
				break;
			case 2:
				replaceFragment(position, R.drawable.weapons_bg,
						R.array.weapons_titles, R.array.ps3_weapon_codes, true);
				currentSelection = 3;
				break;
			case 3:
				replaceFragment(position, R.drawable.vehicles_bg,
						R.array.vehicle_titles, R.array.ps3_vehicle_codes, true);
				currentSelection = 4;
				break;
			case 4:
				replaceFragment(position, R.drawable.world_bg,
						R.array.world_titles, R.array.ps3_world_codes, true);
				currentSelection = 5;
				break;
			default:
				break;
			}
		} else if (parent.getId() == R.id.drawerListViewXbox) {
			switch (position) {
			case 0:
				replaceFragment(position, R.drawable.main_menu_bg,
						R.array.all_titles, R.array.xbox_all_codes, false);
				currentSelection = 6;
				break;
			case 1:
				replaceFragment(position, R.drawable.player_bg,
						R.array.player_titles, R.array.xbox_player_codes, false);
				currentSelection = 7;
				break;
			case 2:
				replaceFragment(position, R.drawable.weapons_bg,
						R.array.weapons_titles, R.array.xbox_weapon_codes,
						false);
				currentSelection = 8;
				break;
			case 3:
				replaceFragment(position, R.drawable.vehicles_bg,
						R.array.vehicle_titles, R.array.xbox_vehicle_codes,
						false);
				currentSelection = 9;
				break;
			case 4:
				replaceFragment(position, R.drawable.world_bg,
						R.array.world_titles, R.array.xbox_world_codes, false);
				currentSelection = 10;
				break;
			default:
				break;
			}
		}
	}

	private void addFragment(int bgResId, int titlesId, int cheatsId,
			boolean isPS3) {
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

	private void replaceFragment(int position, int bgResId, int titlesId,
			int cheatsId, boolean isPS3) {
		actionBar
				.setTitle((isPS3 ? "PS3 " : "XBox ") + DRAWER_LABELS[position]);
		Bundle b = new Bundle();
		b.putInt(Keys.BG_RESID, bgResId);
		b.putInt(Keys.TITLES_RESID, titlesId);
		b.putInt(Keys.CHEATS_RESID, cheatsId);
		b.putBoolean(Keys.IS_PS3, isPS3);
		CheatsFragment frag = new CheatsFragment();
		frag.setArguments(b);
		FragmentTransaction transaction = fmanager.beginTransaction();
		transaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
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
		if (item.getItemId() == R.id.exit) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Editor editor = prefs.edit();
		editor.putInt(Keys.SELECTION, currentSelection);
		editor.apply();
	}

	private void setFirstFragment(int selection) {
		switch (selection) {
		case 0:
			FragmentTransaction transaction = fmanager.beginTransaction();
			transaction.setCustomAnimations(android.R.anim.fade_in,
					android.R.anim.fade_out);
			transaction.replace(R.id.fragment_holder, new FavoritesFragment());
			transaction.commit();
			break;
		case 1:
			addFragment(R.drawable.main_menu_bg, R.array.all_titles,
					R.array.ps3_all_codes, true);
			break;
		case 2:
			addFragment(R.drawable.player_bg, R.array.player_titles,
					R.array.ps3_player_codes, true);
			break;
		case 3:
			addFragment(R.drawable.weapons_bg, R.array.weapons_titles,
					R.array.ps3_weapon_codes, true);
			break;
		case 4:
			addFragment(R.drawable.vehicles_bg, R.array.vehicle_titles,
					R.array.ps3_vehicle_codes, true);
			break;
		case 5:
			addFragment(R.drawable.world_bg, R.array.world_titles,
					R.array.ps3_world_codes, true);
			break;
		case 6:
			addFragment(R.drawable.main_menu_bg, R.array.all_titles,
					R.array.xbox_all_codes, false);
			break;
		case 7:
			addFragment(R.drawable.player_bg, R.array.player_titles,
					R.array.xbox_player_codes, false);
			break;
		case 8:
			addFragment(R.drawable.weapons_bg, R.array.weapons_titles,
					R.array.xbox_weapon_codes, false);
			break;
		case 9:
			addFragment(R.drawable.vehicles_bg, R.array.vehicle_titles,
					R.array.xbox_vehicle_codes, false);
			break;
		case 10:
			addFragment(R.drawable.world_bg, R.array.world_titles,
					R.array.xbox_world_codes, false);
			break;
		default:
			break;
		}
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

	public DatabaseAdapter getDbAdapter() {
		return dbAdapter;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		setFirstFragment(currentSelection);
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
