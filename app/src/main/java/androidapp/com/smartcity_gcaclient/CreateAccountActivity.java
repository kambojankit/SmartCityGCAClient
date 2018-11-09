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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText userEmailID;
    private EditText password;
    private Button createAccountButton;
    private static final String TAG = "CreateAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        initUi();
        mAuth = FirebaseAuth.getInstance();
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmailID.getText().toString() == "" || password.getText().toString() == "") {
                    Toast.makeText(CreateAccountActivity.this, "enter correct Email " +
                            "or Password", Toast.LENGTH_SHORT).show();
                } else {
                    createUserAccount(userEmailID.getText().toString(), password.getText().toString());
                }
            }
        });
    }

    private void initUi(){
        userEmailID = findViewById(R.id.et_ca_emailid);
        password = findViewById(R.id.et_ca_password);
        createAccountButton = findViewById(R.id.bt_ca_create_account);
    }

    private void createUserAccount(String userName, String password) {
        mAuth.createUserWithEmailAndPassword(userName,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(CreateAccountActivity.this, "Account Created Successfully"
                                    , Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
