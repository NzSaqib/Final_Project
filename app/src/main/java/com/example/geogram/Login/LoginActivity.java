package com.example.geogram.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geogram.R;
import com.example.geogram.homee.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    // Initialize Firebase Auth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private ProgressBar mProgressBar;
    private EditText mEmail, mPassword;
    //private TextView mPleaseWait;
    private Button btnLogin;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar) findViewById(R.id.login_progressbar);
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        //mPleaseWait = (TextView) findViewById(R.id.pleaseWait);

        Log.d(TAG, "onCreate: started...");

        //mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        setupFirebaseAuth();
        init();
    }

    private boolean isStringNull(String string) {
        Log.d(TAG, "isStringNull: checking string if null.");

        if (string.equals("")) {
            return true;
        } else {
            return false;
        }
    }


    /*
   ----------------------------firebase-----------------------
    */
    private void init() {
        //initial the button for logging in
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (isStringNull(email) && isStringNull(password)) {
                    Toast.makeText(LoginActivity.this, "Fill all the fields...", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    //mPleaseWait.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "onComplete: one Complete: " + task.isSuccessful());

                                    FirebaseUser user = mAuth.getCurrentUser();
                                   /* try{
                                        if(user.isEmailVerified()){
                                            Log.d(TAG, "onComplete: email is verified...");

                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        }else {
                                            Toast.makeText(mContext, "Email is not verified \n check your email inbox", Toast.LENGTH_SHORT).show();
                                            mProgressBar.setVisibility(View.GONE);
                                            mPleaseWait.setVisibility(View.GONE);
                                            mAuth.signOut();
                                        }

                                    }catch (NullPointerException e){
                                        Log.e(TAG, "onComplete: NullPointerException : " + e.getMessage());
                                    }*/
                                    /**
                                     * if sign in fails , display a message to the user. If sign in succeeds
                                     * the auth state listener will be notified and logic
                                     * to handle the signed in user can be handled in the listener.
                                     */
                                    if (task.isSuccessful()) {
                                        Log.w(TAG, "onComplete: failed", task.getException());
                                        Toast.makeText(LoginActivity.this, "Successfully logged in...", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                        mProgressBar.setVisibility(View.GONE);
                                        //mPleaseWait.setVisibility(View.GONE);

                                    } else {
                                       /* Log.d(TAG, "onComplete: successful login...");
                                        Toast.makeText(LoginActivity.this, "Failed....", Toast.LENGTH_SHORT).show();
                                        mProgressBar.setVisibility(View.GONE);
                                        mPleaseWait.setVisibility(View.GONE);*/

                                        try{
                                            if(user.isEmailVerified()){
                                                Log.d(TAG, "onComplete: email is verified...");

                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            }else {
                                                Toast.makeText(mContext, "Email is not verified \n check your email inbox", Toast.LENGTH_SHORT).show();
                                                mProgressBar.setVisibility(View.GONE);
                                                //mPleaseWait.setVisibility(View.GONE);
                                                //mAuth.signOut();
                                            }

                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException : " + e.getMessage());
                                        }
                                    }
                                }
                            });
                }
            }
        });
        /**
         *
         */
        TextView linkSignUp = (TextView) findViewById(R.id.link_signup);
        linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        /**
         * if the user is logged in then navigate to MainActivity and finish it
         */
        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * Setting up Firebase Autentication object
     */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth...");
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //user signed in
                    Log.d(TAG, "onAuthStateChanged: signed in." + user.getUid());
                } else {
                    //user signed out
                    Log.d(TAG, "onAuthStateChanged: signed out");
                }
            }
        };
    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
