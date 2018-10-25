package androidapp.com.smartcity_gcaclient.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import androidapp.com.smartcity_gcaclient.dao.UserProfileInfoDao;
import androidapp.com.smartcity_gcaclient.pojo.UserProfileInfo;

@Database(entities = {UserProfileInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract UserProfileInfoDao getUserProfileInfoDao();

    public static AppDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =// using inMemoryDatabaseBuilder here, incorrect
                    Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "user_profile")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
