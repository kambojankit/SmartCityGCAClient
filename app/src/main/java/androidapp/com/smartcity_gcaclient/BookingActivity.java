package androidapp.com.smartcity_gcaclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidapp.com.smartcity_gcaclient.data.AppDatabase;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

public class BookingActivity extends AppCompatActivity{

    RadioButton slotOneButton;
    RadioButton slotTwoButton;
    RadioButton slotThreeButton;
    Button schedulePickupButton;
    String bookingToken;
    JSONObject userJsonObject;
    AppDatabase appDatabase;
    String serviceURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setRadioButtons();
        appDatabase = AppDatabase.getDatabaseInstance(BookingActivity.this);
        serviceURL = "http://10.0.2.2:8080/booking/get/history";
        schedulePickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingToken = getBookingToken();
                if (bookingToken == null) {
                    Toast.makeText(getApplicationContext(), "please select a slot",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //createJsonObject(bookingToken);
                    sendBookingRequest(bookingToken);
                }
            }
        });
    }

    private void setRadioButtons() {
        slotOneButton = findViewById(R.id.rb_slot_10to11);
        slotTwoButton = findViewById(R.id.rb_slot_12to1);
        slotThreeButton = findViewById(R.id.rb_slot_2to3);
        schedulePickupButton = findViewById(R.id.button_pickup_schedule);
    }

    private String getBookingToken() {
        if (slotOneButton.isChecked()) {
            return "TOKEN_1";
        }
        if (slotTwoButton.isChecked()) {
            return "TOKEN_2";
        }
        if (slotThreeButton.isChecked()) {
            return "TOKEN_3";
        }
        return null;
    }

    private void createJsonObject(String bookingToken) {
        List<UserProfileInfo> userProfileInfoList = appDatabase.getUserProfileInfoDao()
                .getUserProfiles();
        UserProfileInfo userProfileInfo = userProfileInfoList.get(0);
        userJsonObject = new JSONObject();

        // TODO: Creating a JSON Object and network call and also to provide email through intent
        try {
            userJsonObject.put("nameOfUser", userProfileInfo.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendBookingRequest(final String bookingToken) {

    }
}
