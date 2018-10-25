package androidapp.com.smartcity_gcaclient.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

@Dao
public interface UserProfileInfoDao {
    @Insert
    void insert(UserProfileInfo userProfileInfos);

    @Query("SELECT * FROM UserProfileInfo")
    List<UserProfileInfo> getUserProfiles();
}
