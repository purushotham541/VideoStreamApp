package com.easycoding4all.videostreamapp.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.easycoding4all.videostreamapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SigninActivity extends AppCompatActivity
{
    SignInButton signInButton;
    GoogleSignInClient googleSignInClient;
    String TAG="MainActivity";
    int RC_SIGN_IN=111;
    ProgressBar progressBar;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        toolbar=findViewById(R.id.tbr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign in");
        signInButton=findViewById(R.id.sign_in);
        progressBar=findViewById(R.id.pgbr);
        progressBar.setVisibility(View.INVISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(this,gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progressBar.setVisibility(View.INVISIBLE);

            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent=new Intent(SigninActivity.this,MainActivity.class);

            startActivity(intent);
            progressBar.setVisibility(View.INVISIBLE);

            // Signed in successfully, show authenticated UI.
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount!=null)
        {
            Intent intent=new Intent(SigninActivity.this,MainActivity.class);
            startActivity(intent);
            progressBar.setVisibility(View.INVISIBLE);


        }
    }
}
