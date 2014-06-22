package com.bobamason.gtavcheats2;

import com.bobamason.gtavcheats.R;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CheatsFragment extends Fragment implements
		AdapterView.OnItemClickListener {

	private ListView listView;

	private FragmentActivity activity;

	private Bundle args;

	private boolean isPS3;

	public CheatsFragment() {
	}

	// must pass arguments for:
	//
	// background image id
	// title array id
	// cheats array id
	//

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cheats_fragment_layout, container,
				false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		args = getArguments();
		isPS3 = args.getBoolean(Keys.IS_PS3);
		((ImageView) activity.findViewById(R.id.bg_imageview))
				.setImageResource(args.getInt(Keys.BG_RESID));
		listView = (ListView) getActivity().findViewById(R.id.listView);
		listView.setAdapter(new MyAdapter(activity));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CheatDialog dialog = new CheatDialog();
		Bundle dialogArgs = new Bundle();
		dialogArgs.putString(Keys.DIALOG_TITLE, ((TextView) view
				.findViewById(R.id.title_text)).getText().toString());
		dialogArgs.putString(Keys.DIALOG_CHEAT, ((TextView) view
				.findViewById(R.id.cheat_text)).getText().toString());
		dialogArgs.putBoolean(Keys.IS_PS3, isPS3);
		dialog.setArguments(dialogArgs);
		dialog.show(activity.getSupportFragmentManager(), "dialog");
	}

	private class MyAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		private String[] titles;

		private String[] cheatCodes;

		public MyAdapter(Context context) {
			inflater = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			titles = context.getResources().getStringArray(
					args.getInt(Keys.TITLES_RESID));
			cheatCodes = context.getResources().getStringArray(
					args.getInt(Keys.CHEATS_RESID));
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			ViewHolder holder = null;
			if (row == null) {
				row = inflater.inflate(R.layout.cheat_row_item, parent, false);
				holder = new ViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}

			holder.title.setText(titles[position]);
			holder.cheat.setText(cheatCodes[position]);

			return row;
		}

		private class ViewHolder {
			public TextView title;
			public TextView cheat;

			ViewHolder(View v) {
				title = (TextView) v.findViewById(R.id.title_text);
				cheat = (TextView) v.findViewById(R.id.cheat_text);

			}
		}
	}
}
