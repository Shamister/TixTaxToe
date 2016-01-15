package com.revauthore.tixtaxtoe.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.revauthore.tixtaxtoe.Game;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // setup the initialisation
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        // Create the libgdx View
        View gameView = initializeForView(new Game(), config);

        // Create and setup the Admob view
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Game.AD_UNIT_ID);

        // create and ad request
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        // load the ad
        adView.loadAd(adRequestBuilder.build());

        // Add the libgdx view
        layout.addView(gameView);

        //==================== ADMOB ===================
        // Add the AdMob view
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        layout.addView(adView, adParams);

        setContentView(layout);
    }
}
