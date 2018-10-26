package androidapp.com.smartcity_gcaclient.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

@Dao
public interface UserProfileInfoDao {
    @Insert
    void insert(UserProfileInfo userProfileInfos);

    @Update
    int update(UserProfileInfo userProfileInfo);

    @Query("SELECT * FROM UserProfileInfo")
    List<UserProfileInfo> getUserProfiles();
}
