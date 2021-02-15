package com.example.android.newsapi.roomdatabase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;
import android.util.Log;

import com.example.android.newsapi.model.Article;
import com.example.android.newsapi.model.Favorite;
import com.example.android.newsapi.model.Sources;

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

//Updated room database from 1 to 2.
//https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929

//https://stackoverflow.com/questions/45594432/android-room-sqlite-error-no-such-table

@Database(entities = {Article.class, Favorite.class, Sources.class,}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class NewsRoomDatabase extends RoomDatabase {

    private static final String LOG_TAG = NewsRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static NewsRoomDatabase INSTANCE;

    //https://stackoverflow.com/questions/62932204/room-databse-loses-data-on-restart-application

    //https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929

    public static NewsRoomDatabase getInstance(Context context)  {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NewsRoomDatabase.class, "news_database")
                        //.allowMainThreadQueries()
                        //.fallbackToDestructiveMigration()
                        //.addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return INSTANCE;
    }

    public abstract NewsDao newsDao();

    public abstract FavoritesDao favoritesDao();

    public abstract SourcesDao sourcesDao();

    //https://medium.com/@b.chandrasaimohan/mvvm-with-room-db-and-retrofit-7d822ec9f71a

    //private static Callback sRoomDatabaseCallback =
            //new Callback(){

                //@Override
                //public void onOpen (@NonNull SupportSQLiteDatabase db){
                    //super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                //}
            //};

    //public static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        //private final FavoritesDao mDao;

        //PopulateDbAsync(NewsRoomDatabase db) {
            //mDao = db.favoritesDao();
        //}

        //@Override
        //protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();
            //Favorite favorite = new Favorite(0);
            //mDao.insert(favorite);
            //return null;
        //}
    //}

}

