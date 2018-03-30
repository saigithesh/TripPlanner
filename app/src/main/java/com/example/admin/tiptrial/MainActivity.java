package com.example.admin.tiptrial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

/*<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>*/
public class MainActivity extends AppCompatActivity {
    private PreferenceManager prefManager;
    ImageView i1,i2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager = new PreferenceManager(this);
       /* i1 = (ImageView) findViewById(R.id.imageView);
        i2 = (ImageView) findViewById(R.id.imageView2);*/

    }
    @Override
    protected void onStart() {
        super.onStart();

        startAnimation();
        /*float b1 = getResources().getDisplayMetrics().heightPixels - (i1.getHeight());
        float b2 = getResources().getDisplayMetrics().heightPixels - (i2.getHeight());
        float b = b1 - b2;
        i1.animate()
                .translationY(b)
.setInterpolator(new AccelerateInterpolator())
                .setInterpolator(new BounceInterpolator())
                .setDuration(2000);*/
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int Permission_All = 1;

                String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
                if(!hasPermissions(MainActivity.this, Permissions)){
                    ActivityCompat.requestPermissions(MainActivity.this, Permissions, Permission_All);
                }
                if (!prefManager.isFirstTimeLaunch()) {
                    launchHomeScreen();
                    finish();
                }
                else{

                    launchNextScreen();
                }
            }
        }, 1970);




    }
    private void launchHomeScreen() {

        startActivity(new Intent(MainActivity.this, MapsActivity.class));
        finish();
    }
    private void launchNextScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        finish();
    }

    public void startAnimation() {

        final ImageView image = (ImageView)findViewById(R.id.imageView);
        image.setVisibility(View.VISIBLE);

        Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.loader_anim);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.loader_anim);
                anim.setAnimationListener(this);
                image.startAnimation(anim);

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });


        image.startAnimation(anim);
    }
    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }

}
