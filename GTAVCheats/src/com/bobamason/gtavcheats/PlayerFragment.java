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

public class PlayerFragment extends Fragment {

	private ListView listView;

	public PlayerFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cheat_list_layout, container, false);
		listView = (ListView) getActivity().findViewById(R.id.listView);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView.setAdapter(new MyAdapter(getActivity()));
	}

	private class MyAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private String[] titles;
		private String[] ps3cheats;
		private String[] xboxCheats;
		private String[] currentCheats;

		public MyAdapter(Context context) {
			inflater = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			titles = context.getResources().getStringArray(
					R.array.player_titles);
			ps3cheats = context.getResources().getStringArray(
					R.array.ps3_player_codes);
			xboxCheats = context.getResources().getStringArray(
					R.array.xbox_player_codes);
			// ----------TODO----------------
			currentCheats = ps3cheats;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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
			holder.cheat.setText(currentCheats[position]);

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
