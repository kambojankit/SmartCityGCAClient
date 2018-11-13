package androidapp.com.smartcity_gcaclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import androidapp.com.smartcity_gcaclient.data.AppDatabase;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

public class MainActivity extends AppCompatActivity {

    private SignInButton googleSignInUpButton;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "MainActivity";
    private Button createAccountButton;

    private EditText userEmailId;
    private EditText userPassword;
    private Button userSignIn;

    private static final int RC_SIGN_IN = 234;
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIElements();

        googleSignInUpButton = findViewById(R.id.google_sign_in_button);
        createAccountButton = findViewById(R.id.bt_create_account);
        mFirebaseAuth = FirebaseAuth.getInstance();
        database = AppDatabase.getDatabaseInstance(MainActivity.this);
        mGoogleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions);

        googleSignInUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithFirebase();
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(accountIntent);
            }
        });

        userSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmailId.getText().toString() == ""
                        || userPassword.getText().toString() == "") {
                    Toast.makeText(MainActivity.this, "Enter Valid credentials",
                            Toast.LENGTH_SHORT).show();
                } else {
                    signInWithUserPass(userEmailId.getText().toString(),
                            userPassword.getText().toString());
                }
            }
        });
    }

    private void initUIElements(){
        userEmailId = findViewById(R.id.et_user_name);
        userPassword = findViewById(R.id.et_password);
        userSignIn = findViewById(R.id.bt_signin);
    }

    /***
     * signIn user with its google credentials
     */
    private void signInWithFirebase() {
        //getting the google signInWithFirebase intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, user.getDisplayName() +
                                    " Signed In ", Toast.LENGTH_SHORT).show();
                            if (isRegistered()) {
                                // if registered is false then we go for registration otherwise for time slots
                                Intent intentRegister = new Intent(MainActivity.this,
                                        RegistrationPage.class);
                                intentRegister.putExtra("email",user.getEmail());
                                startActivity(intentRegister);
                            } else {
                                // call for booking activity
                                Intent intentBooking = new Intent(MainActivity.this,
                                        BookingActivity.class);
                                startActivity(intentBooking);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure",
                                    task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void signInWithUserPass(String email, String password){
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            if (isRegistered()) {
                                // if registered is false then we go for registration otherwise for time slots
                                Intent intentRegister = new Intent(MainActivity.this,
                                        RegistrationPage.class);
                                intentRegister.putExtra("email",user.getEmail());
                                startActivity(intentRegister);
                            } else {
                                // call for booking activity
                                Intent intentBooking = new Intent(MainActivity.this,
                                        BookingActivity.class);
                                startActivity(intentBooking);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }




    /***
     * to check if user has registered his pickup details or not
     * @return boolean
     */
    private boolean isRegistered() {
        List<UserProfileInfo> userProfileInfoList = database.getUserProfileInfoDao()
                .getUserProfiles();
        boolean userProfileInfo = userProfileInfoList.isEmpty();
        return userProfileInfo;
    }
}
