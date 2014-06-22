package com.bobamason.gtavcheats2;

import com.bobamason.gtavcheats.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

//import com.google.ads.AdRequest;
//import com.google.ads.AdView;

public class PSPlayerActivity extends ActionBarActivity {

	//AdView ad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cheats_fragment_layout);

		//ad = (AdView) findViewById(R.id.adView);

		//AdRequest adRequest = new AdRequest();
		//adRequest.addTestDevice("0335EA6EA16C9CD2466FFFEF37BFF7D6");
		//ad.loadAd(adRequest);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//if (ad != null) {
		//	ad.destroy();
		//}
		super.onDestroy();
	}

}
