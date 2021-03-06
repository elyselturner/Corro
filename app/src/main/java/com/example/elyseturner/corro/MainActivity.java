package com.example.elyseturner.corro;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.plus.Plus;

/**
 * Created by elyseturner on 6/19/15.
 */
public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;
    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;

    private SignInButton btnSignIn;
    private Button pastSession, checkWeather, newRun;
    private LinearLayout llMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        settingLoginContentView();
        apiBuilder();
    }

    private void settingLoginContentView() {
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        llMainNav = (LinearLayout) findViewById(R.id.llMainNav);
        checkWeather = (Button) findViewById(R.id.btnWeather);
        newRun = (Button) findViewById(R.id.btnNewRun);
        pastSession = (Button) findViewById(R.id.btnPastRuns);


        btnSignIn.setOnClickListener(this);
        pastSession.setOnClickListener(this);
        newRun.setOnClickListener(this);
        checkWeather.setOnClickListener(this);

    }

    private void apiBuilder() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addApi(Fitness.SENSORS_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.SESSIONS_API)
                .addApi(Fitness.BLE_API)
                .addApi(Fitness.CONFIG_API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                signInWithGplus();
                break;
            case R.id.btnWeather:
                //call logic
                //on connection success switch to new activity
                //for now let's just let us switch to the new activity
                Intent a = new Intent( MainActivity.this, HourlyWeather.class);
                startActivity(a);
                break;
            case R.id.btnNewRun:
                //session logic
                //on success switch to new activity
                //for now let's just let us switch to the new activity
                Intent b = new Intent( MainActivity.this, NewRun.class);
                startActivity(b);
                break;
            case R.id.btnPastRuns:
                //session logic
                //on success switch to new activity
                //for now let's just let us switch to the new activity
                Intent c = new Intent( MainActivity.this, RunningHistory.class);
                startActivity(c);
                break;
        }
    }

    private void getHourlyWeather() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_SHORT).show();
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            llMainNav.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            llMainNav.setVisibility(View.GONE);
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
}
