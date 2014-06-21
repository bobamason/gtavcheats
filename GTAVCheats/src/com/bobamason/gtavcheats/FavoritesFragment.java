package com.bobamason.gtavcheats;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.*;
import android.widget.*;
import android.content.*;
import android.widget.AdapterView.*;
import android.view.*;
import android.database.*;
import android.os.*;

public class FavoritesFragment extends Fragment implements AdapterView.OnItemClickListener {

	private ListView listView;

	private FragmentActivity activity;

	private static final String PS3_LABEL = "PS3";

	private static final String XBox_LABEL = "XBox 360";

	private DatabaseAdapter dbAdapter;

	public FavoritesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cheats_fragment_layout, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		dbAdapter = ((MainActivity)activity).getDbAdapter();
		((ImageView) activity.findViewById(R.id.bg_imageview)).setImageResource(R.drawable.world_bg);
		listView = (ListView) getActivity().findViewById(R.id.listView);
		GetAllTask task = new GetAllTask();
		task.execute(true);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		CheatDialog dialog = new CheatDialog();
		Bundle dialogArgs = new Bundle();
		dialogArgs.putString(Keys.DIALOG_TITLE, ((TextView)view.findViewById(R.id.title_text_fav)).getText().toString());
		dialogArgs.putString(Keys.DIALOG_CHEAT, ((TextView)view.findViewById(R.id.cheat_text_fav)).getText().toString());
		dialogArgs.putBoolean(Keys.IS_PS3, ((TextView)view.findViewById(R.id.console_text)).getText().toString().equals(PS3_LABEL));
		dialog.setArguments(dialogArgs);
		dialog.show(activity.getSupportFragmentManager(), "dialog");
		dialog.setOnFavoriteChangedListener(new CheatDialog.OnFavoriteChangedListener(){

				@Override
				public void onFavoriteChanged(CheatDialog d) {
					GetAllTask task = new GetAllTask();
					task.execute(false);
					d.dismiss();
				}
			});
	}

	private class FavoritesAdapter extends CursorAdapter {

		private LayoutInflater inflater;

		FavoritesAdapter(Context context, Cursor cursor) {
			super(context, cursor, false);
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void update(Cursor all) {
			swapCursor(all);
			notifyDataSetChanged();
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return inflater.inflate(R.layout.favorite_row_item, parent, false);
		}

		@Override
		public void bindView(View v, Context context, Cursor cursor) {
			boolean isPS = (cursor.getInt(cursor.getColumnIndex(DatabaseAdapter.SQLHelper.COLUMN_CONSOLE)) == 1) ? true : false;
			TextView consoleText = ((TextView)v.findViewById(R.id.console_text));
			consoleText.setTextColor(activity.getResources().getColor((isPS ? R.color.ps3_text_color : R.color.xbox_text_color)));
			consoleText.setText(isPS ? PS3_LABEL : XBox_LABEL);
			TextView title = (TextView) v.findViewById(R.id.title_text_fav);
			title.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.SQLHelper.COLUMN_TITLE)));
			TextView cheat = (TextView) v.findViewById(R.id.cheat_text_fav);
			cheat.setText(cursor.getString(cursor.getColumnIndex(DatabaseAdapter.SQLHelper.COLUMN_CHEAT)));
		}
	}

	private class GetAllTask extends AsyncTask<Boolean, Void, Cursor> {

		private boolean isFirstTime;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Cursor doInBackground(Boolean... args) {
			this.isFirstTime = args[0].booleanValue();
			Cursor c = dbAdapter.getAll();
			return c;
		}

		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);
			if (isFirstTime) {
				listView.setAdapter(new FavoritesAdapter(activity, result));
			} else {
				((FavoritesAdapter)listView.getAdapter()).update(result);
			}
		}
	}
}
