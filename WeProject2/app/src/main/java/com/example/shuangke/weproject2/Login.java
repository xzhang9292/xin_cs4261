package com.example.shuangke.weproject2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null) {
            finish();
            startActivity(new Intent(getApplicationContext(),HomePage.class));
        }


    }
    public void gotoHomePage(View view){
        signIn();
    }

    private void signIn() {
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if(task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(),HomePage.class));
                        }
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login.this,"Username or password incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            // mStatusTextView.setText("auth failed");
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }



}
