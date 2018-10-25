package androidapp.com.smartcity_gcaclient.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

/***
 * POJO for storing signUp information of the user
 */
@Getter
@Setter
@Entity(tableName = "UserProfileInfo")
public class UserProfileInfo {
    @PrimaryKey(autoGenerate = true)
    private int UserProfileID;
    @ColumnInfo(name = "EmailID")
    private String emailID;
    @ColumnInfo(name = "User Name")
    private String name;
    @ColumnInfo(name = "User Contact Number")
    private String phoneNumber;
    @ColumnInfo(name = "House address")
    private String houseAddress;
    @ColumnInfo(name = "Locality")
    private String locality;
    @ColumnInfo(name = "City")
    private String city;
    @ColumnInfo(name = "State")
    private String State;
    @ColumnInfo(name = "PinCode")
    private String pinCode;
    @ColumnInfo(name = "Contact Number")
    private String contactNumber;

    public String getHouseAddress() {
        return houseAddress;
    }

    public int getUserProfileID() {
        return UserProfileID;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return State;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setUserProfileID(int userProfileID) {
        UserProfileID = userProfileID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        State = state;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}

