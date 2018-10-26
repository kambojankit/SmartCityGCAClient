package androidapp.com.smartcity_gcaclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import androidapp.com.smartcity_gcaclient.data.AppDatabase;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

public class EditInfoActivity extends AppCompatActivity {

    private EditText nameETEditInfo;
    private EditText mobileNoETEditInfo;
    private EditText houseAddETEditInfo;
    private EditText localityETEditInfo;
    private EditText cityETEditInfo;
    private EditText stateETEditInfo;
    private EditText pinCodeETEditInfo;
    private AppDatabase appDatabase;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_acitivity);
        getUserInfo();
        getDatabaseInstance();
        setCurrentInfo();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileInfo userProfileInfo = getUserProfileInfo();
                appDatabase.getUserProfileInfoDao().update(userProfileInfo);
            }
        });
    }

    private void getUserInfo() {
        nameETEditInfo = findViewById(R.id.edit_info_et_user_name);
        mobileNoETEditInfo = findViewById(R.id.edit_info_et_mobile_number);
        houseAddETEditInfo = findViewById(R.id.edit_info_et_house_address);
        localityETEditInfo = findViewById(R.id.edit_info_et_house_address);
        cityETEditInfo = findViewById(R.id.edit_info_et_city);
        stateETEditInfo = findViewById(R.id.edit_info_et_state);
        pinCodeETEditInfo = findViewById(R.id.edit_info_et_pin_code);
        saveButton = findViewById(R.id.save_button);
    }

    private void getDatabaseInstance(){
        appDatabase = AppDatabase.getDatabaseInstance(EditInfoActivity.this);
    }

    private void setCurrentInfo() {
        List<UserProfileInfo> userProfileInfoList = appDatabase.getUserProfileInfoDao()
                .getUserProfiles();
        UserProfileInfo userProfileInfo = userProfileInfoList.get(0);
        nameETEditInfo.setText(userProfileInfo.getName(), TextView.BufferType.EDITABLE);
        mobileNoETEditInfo.setText(userProfileInfo.getPhoneNumber(),TextView.BufferType.EDITABLE);
        houseAddETEditInfo.setText(userProfileInfo.getHouseAddress(), TextView.BufferType.EDITABLE);
        localityETEditInfo.setText(userProfileInfo.getLocality(), TextView.BufferType.EDITABLE);
        cityETEditInfo.setText(userProfileInfo.getCity(),TextView.BufferType.EDITABLE);
        stateETEditInfo.setText(userProfileInfo.getState(),TextView.BufferType.EDITABLE);
        pinCodeETEditInfo.setText(userProfileInfo.getPinCode(), TextView.BufferType.EDITABLE);
    }

    private UserProfileInfo getUserProfileInfo() {
        UserProfileInfo userProfileInfo = appDatabase.getUserProfileInfoDao()
                .getUserProfiles().get(0);
        userProfileInfo.setName(nameETEditInfo.getText().toString());
        userProfileInfo.setPhoneNumber(mobileNoETEditInfo.getText().toString());
        userProfileInfo.setHouseAddress(houseAddETEditInfo.getText().toString());
        userProfileInfo.setCity(cityETEditInfo.getText().toString());
        userProfileInfo.setState(stateETEditInfo.getText().toString());
        userProfileInfo.setPinCode(pinCodeETEditInfo.getText().toString());
        return userProfileInfo;
    }
}
