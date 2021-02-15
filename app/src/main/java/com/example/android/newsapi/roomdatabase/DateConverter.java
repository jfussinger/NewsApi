package com.example.android.newsapi.roomdatabase;

import androidx.room.TypeConverter;
import java.sql.Timestamp;

//SQL cannot store data types like Date by default.
//That’s why we need a way to convert it into a compatible data type to store it in the database.

//https://github.com/googlecodelabs/android-build-an-app-architecture-components/blob/arch-training-steps/app/src/main/java/com/example/android/sunshine/data/database/DateConverter.java

public class DateConverter {
    @TypeConverter
    public static Timestamp toJavaTimestamp(Long timestamp) {
        return timestamp == null ? null : new Timestamp(timestamp);
    }

    @TypeConverter
    public static Long toDatabaseTimestamp(Timestamp timestamp) {
        return timestamp == null ? new Timestamp(System.currentTimeMillis()).getTime() : timestamp.getTime();
    }
}

