package com.bobamason.gtavcheats;
import android.support.v4.app.*;
import android.os.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import android.graphics.*;
import com.bobamason.gtavcheats.CheatDialog.*;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle args = getArguments();
		title = args.getString(Keys.DIALOG_TITLE);
		cheatCode = args.getString(Keys.DIALOG_CHEAT);
		isPS3 = args.getBoolean(Keys.IS_PS3);
		saved = ((MainActivity)getActivity()).getDbAdapter().saved(cheatCode);

		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getDialog().setCancelable(true);

		View view = inflater.inflate(R.layout.dialog_layout, null);

		((TextView)view.findViewById(R.id.dialog_title)).setText(title);

		checkBox = (CheckBox) view.findViewById(R.id.fav_checkbox);
		checkBox.setChecked(saved);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

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
		button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

		grid = (GridLayout) view.findViewById(R.id.dialog_grid);
		populateGrid();

		return view;
	}

	private void populateGrid() {
		String[] s = cheatCode.split(", ");
		for (int i = 0; i < s.length; i++) {
			ImageView image = null;
			if (isPS3) {
				switch (s[i].toUpperCase()) {
					case "CIRCLE":
						image = img(R.drawable.ps3_circle_button);
						break;
					case "DOWN":
						image = img(R.drawable.ps3_down_button);
						break;
					case "L1":
						image = img(R.drawable.ps3_l1_button);
						break;
					case "L2":
						image = img(R.drawable.ps3_l2_button);
						break;
					case "LEFT":
						image = img(R.drawable.ps3_left_button);
						break;
					case "R1":
						image = img(R.drawable.ps3_r1_button);
						break;
					case "R2":
						image = img(R.drawable.ps3_r2_button);
						break;
					case "RIGHT":
						image = img(R.drawable.ps3_right_button);
						break;
					case "SQUARE":
						image = img(R.drawable.ps3_square_button);
						break;
					case "TRIANGLE":
						image = img(R.drawable.ps3_triangle_button);
						break;
					case "UP":
						image = img(R.drawable.ps3_up_button);
						break;
					case "X":
						image = img(R.drawable.ps3_x_button);
						break;
					default:
						break;
				}
			} else {
				switch (s[i].toUpperCase()) {
					case "A":
						image = img(R.drawable.xbox_a_button);
						break;
					case "B":
						image = img(R.drawable.xbox_b_button);
						break;
					case "DOWN":
						image = img(R.drawable.xbox_down_button);
						break;
					case "LB":
						image = img(R.drawable.xbox_lb_button);
						break;
					case "LEFT":
						image = img(R.drawable.xbox_left_button);
						break;
					case "LT":
						image = img(R.drawable.xbox_lt_button);
						break;
					case "RB":
						image = img(R.drawable.xbox_rb_button);
						break;
					case "RIGHT":
						image = img(R.drawable.xbox_right_button);
						break;
					case "RT":
						image = img(R.drawable.xbox_rt_button);
						break;
					case "UP":
						image = img(R.drawable.xbox_up_button);
						break;
					case "X":
						image = img(R.drawable.xbox_x_button);
						break;
					case "Y":
						image = img(R.drawable.xbox_y_button);
						break;
					default:
						break;
				}
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
			dbAdapter = ((MainActivity)getActivity()).getDbAdapter();
		}

		@Override
		protected Void doInBackground(Boolean... isSaves) {
			if (isSaves[0]) {
				dbAdapter.insert(title, cheatCode, isPS3);
			} else {
				dbAdapter.deleteEntry(cheatCode);
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

	public void setOnFavoriteChangedListener(CheatDialog.OnFavoriteChangedListener listener) {
		favoritesChangeListener = listener;
	}

	public static abstract class OnFavoriteChangedListener {

		public OnFavoriteChangedListener() {}

		public abstract void onFavoriteChanged(CheatDialog d);	
	}
}
