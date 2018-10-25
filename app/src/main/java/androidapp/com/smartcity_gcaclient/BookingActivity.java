package androidapp.com.smartcity_gcaclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidapp.com.smartcity_gcaclient.data.AppDatabase;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

public class BookingActivity extends AppCompatActivity{

    RadioButton slotOneButton;
    RadioButton slotTwoButton;
    RadioButton slotThreeButton;
    Button schedulePickupButton;
    String bookingToken;
    AppDatabase appDatabase;
    String serviceURLPost;
    String serviceURLGet;
    String email;
    Date cDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setRadioButtons();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        cDate = new Date();
        appDatabase = AppDatabase.getDatabaseInstance(BookingActivity.this);
        serviceURLPost = "http://10.0.2.2:8080/booking/create";
        serviceURLGet =  "http://10.0.2.2:8080/booking/get/history";
        schedulePickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingToken = getBookingToken();
                if (bookingToken == null) {
                    Toast.makeText(getApplicationContext(), "please select a slot",
                            Toast.LENGTH_SHORT).show();
                } else {
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

    private void sendBookingRequest(final String bookingToken) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        List<UserProfileInfo> userProfileInfoList = appDatabase.getUserProfileInfoDao()
                .getUserProfiles();
        UserProfileInfo userProfileInfo = userProfileInfoList.get(0);
        Map<String, String> params = new HashMap<>();
        params.put("address",userProfileInfo.getHouseAddress());
        params.put("userName", userProfileInfo.getName());
        params.put("city", userProfileInfo.getCity());
        params.put("mobileNumber", userProfileInfo.getPhoneNumber());
        params.put("tokenNumber", bookingToken);
        params.put("zipCode", userProfileInfo.getPinCode());
        params.put("dateOfRequest", new SimpleDateFormat("yyyyMMdd").format(cDate));
        params.put("email", email);
        params.put("state", userProfileInfo.getState());
        params.put("subRegion", userProfileInfo.getLocality());



        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, serviceURLPost,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Toast.makeText(BookingActivity.this, "Slot booked with id"
                                            + response.getInt("bookingId"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                Log.d("BookingActivity", error.toString());
                Toast.makeText(BookingActivity.this, "Already Booked slot for the day"
                        , Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        requestQueue.add(req);
    }

    private void getBookingHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, serviceURLGet,null,
               new Response.Listener<JSONArray>() {
                   @Override
                   public void onResponse(JSONArray response) {
                       try {
                           JSONObject jsonObject = response.getJSONObject(0);
                           VolleyLog.v("Response ", jsonObject.toString());
                           Log.d("BookingActivity", jsonObject.toString());
                       } catch (JSONException ex) {
                           ex.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               VolleyLog.e("Error: ", error.getMessage());
               Log.d("BookingActivity", error.toString());
               error.printStackTrace();
           }
       });
        requestQueue.add(req);
    }
}
