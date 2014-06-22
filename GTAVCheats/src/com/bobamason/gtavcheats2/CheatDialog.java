package com.bobamason.gtavcheats2;

import com.bobamason.gtavcheats.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CheatDialog extends DialogFragment {

	private Button button;

	private GridLayout grid;

	private String title;

	private String cheatCode;

	private boolean isPS3;

	private boolean saved;

	private CheckBox checkBox;

	private CheatDialog.OnFavoriteChangedListener favoritesChangeListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle args = getArguments();
		title = args.getString(Keys.DIALOG_TITLE);
		cheatCode = args.getString(Keys.DIALOG_CHEAT);
		isPS3 = args.getBoolean(Keys.IS_PS3);
		try {
			saved = ((MainActivity) getActivity()).getDbAdapter().saved(
					cheatCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().setCancelable(true);

		View view = inflater.inflate(R.layout.dialog_layout, null);

		((TextView) view.findViewById(R.id.dialog_title)).setText(title);

		checkBox = (CheckBox) view.findViewById(R.id.fav_checkbox);
		checkBox.setChecked(saved);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton button, boolean checked) {
				if (!saved && checked) {
					FavoriteTask task = new FavoriteTask();
					task.execute(true);
					saved = true;
				} else if (saved && !checked) {
					FavoriteTask task = new FavoriteTask();
					task.execute(false);
					saved = false;
				}
			}
		});

		button = (Button) view.findViewById(R.id.dialog_ok_button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		grid = (GridLayout) view.findViewById(R.id.dialog_grid);
		populateGrid();

		return view;
	}

	@SuppressLint("DefaultLocale")
	private void populateGrid() {
		String[] s = cheatCode.split(", ");
		for (int i = 0; i < s.length; i++) {
			ImageView image = null;
			if (isPS3) {
				if (s[i].toUpperCase().equals("CIRCLE"))
					image = img(R.drawable.ps3_circle_button);

				else if (s[i].toUpperCase().equals("DOWN"))
					image = img(R.drawable.ps3_down_button);

				else if (s[i].toUpperCase().equals("L1"))
					image = img(R.drawable.ps3_l1_button);

				else if (s[i].toUpperCase().equals("L2"))
					image = img(R.drawable.ps3_l2_button);

				else if (s[i].toUpperCase().equals("LEFT"))
					image = img(R.drawable.ps3_left_button);

				else if (s[i].toUpperCase().equals("R1"))
					image = img(R.drawable.ps3_r1_button);

				else if (s[i].toUpperCase().equals("R2"))
					image = img(R.drawable.ps3_r2_button);

				else if (s[i].toUpperCase().equals("RIGHT"))
					image = img(R.drawable.ps3_right_button);

				else if (s[i].toUpperCase().equals("SQUARE"))
					image = img(R.drawable.ps3_square_button);

				else if (s[i].toUpperCase().equals("TRIANGLE"))
					image = img(R.drawable.ps3_triangle_button);

				else if (s[i].toUpperCase().equals("UP"))
					image = img(R.drawable.ps3_up_button);

				else if (s[i].toUpperCase().equals("X"))
					image = img(R.drawable.ps3_x_button);

			} else {
				if (s[i].toUpperCase().equals("A"))
					image = img(R.drawable.xbox_a_button);

				else if (s[i].toUpperCase().equals("B"))
					image = img(R.drawable.xbox_b_button);

				else if (s[i].toUpperCase().equals("DOWN"))
					image = img(R.drawable.xbox_down_button);

				else if (s[i].toUpperCase().equals("LB"))
					image = img(R.drawable.xbox_lb_button);

				else if (s[i].toUpperCase().equals("LEFT"))
					image = img(R.drawable.xbox_left_button);

				else if (s[i].toUpperCase().equals("LT"))
					image = img(R.drawable.xbox_lt_button);

				else if (s[i].toUpperCase().equals("RB"))
					image = img(R.drawable.xbox_rb_button);

				else if (s[i].toUpperCase().equals("RIGHT"))
					image = img(R.drawable.xbox_right_button);

				else if (s[i].toUpperCase().equals("RT"))
					image = img(R.drawable.xbox_rt_button);

				else if (s[i].toUpperCase().equals("UP"))
					image = img(R.drawable.xbox_up_button);

				else if (s[i].toUpperCase().equals("X"))
					image = img(R.drawable.xbox_x_button);

				else if (s[i].toUpperCase().equals("Y"))
					image = img(R.drawable.xbox_y_button);

			}
			if (image == null) {
				TextView t = new TextView(getActivity());
				t.setText(s[i]);
				t.setGravity(Gravity.CENTER);
				grid.addView(t);
			} else {
				image.setPadding(10, 10, 10, 10);
				grid.addView(image);
			}
		}
	}

	private ImageView img(int res) {
		ImageView i = new ImageView(getActivity());
		i.setImageResource(res);
		return i;
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	private class FavoriteTask extends AsyncTask<Boolean, Void, Void> {

		private DatabaseAdapter dbAdapter;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dbAdapter = ((MainActivity) getActivity()).getDbAdapter();
		}

		@Override
		protected Void doInBackground(Boolean... isSaves) {
			try {
				if (isSaves[0]) {
					dbAdapter.insert(title, cheatCode, isPS3);
				} else {
					dbAdapter.deleteEntry(cheatCode);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (favoritesChangeListener != null) {
				favoritesChangeListener.onFavoriteChanged(CheatDialog.this);
			}
		}
	}

	public void setOnFavoriteChangedListener(
			CheatDialog.OnFavoriteChangedListener listener) {
		favoritesChangeListener = listener;
	}

	public static abstract class OnFavoriteChangedListener {

		public OnFavoriteChangedListener() {
		}

		public abstract void onFavoriteChanged(CheatDialog d);
	}
}
