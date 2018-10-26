package androidapp.com.smartcity_gcaclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidapp.com.smartcity_gcaclient.data.AppDatabase;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

public class RegistrationPage extends AppCompatActivity {

    private Button registerButton;
    private EditText nameET;
    private EditText emailET;
    private EditText mobileNoET;
    private EditText houseAddET;
    private EditText localityET;
    private EditText cityET;
    private EditText stateET;
    private EditText pinCodeET;


    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        registerButton = findViewById(R.id.register_button);
        appDatabase = AppDatabase.getDatabaseInstance(RegistrationPage.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
                savingUserInfo();
                Intent intent = new Intent(RegistrationPage.this,
                        BookingActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    private void getUserInfo(){
        nameET = findViewById(R.id.et_user_name);
        mobileNoET = findViewById(R.id.et_mobile_number);
        houseAddET = findViewById(R.id.et_house_address);
        localityET = findViewById(R.id.et_locality);
        cityET = findViewById(R.id.et_city);
        stateET = findViewById(R.id.et_state);
        pinCodeET = findViewById(R.id.et_pin_code);

    }

    private void savingUserInfo(){
        UserProfileInfo userProfileInfo = new UserProfileInfo();
        userProfileInfo.setName(nameET.getText().toString());
        userProfileInfo.setPhoneNumber(mobileNoET.getText().toString());
        userProfileInfo.setCity(cityET.getText().toString());
        userProfileInfo.setHouseAddress(houseAddET.getText().toString());
        userProfileInfo.setPinCode(pinCodeET.getText().toString());
        userProfileInfo.setLocality(localityET.getText().toString());
        userProfileInfo.setState(stateET.getText().toString());
        appDatabase.getUserProfileInfoDao().insert(userProfileInfo);
        Toast.makeText(getApplicationContext(), "registered", Toast.LENGTH_SHORT).show();
    }
}
