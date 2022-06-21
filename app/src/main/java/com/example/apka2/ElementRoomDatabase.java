package com.example.apka2;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Element.class}, version = 1)
public abstract class ElementRoomDatabase extends RoomDatabase {

    public abstract ElementDao elementDao();
    private static volatile ElementRoomDatabase INSTANCE;

    static ElementRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ElementRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ElementRoomDatabase.class, "baza2")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return  INSTANCE;
    }

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ElementDao elementDao;

        private PopulateDbAsyncTask(ElementRoomDatabase db) {
            this.elementDao = db.elementDao();
        }

        @Override
        protected Void doInBackground(Void ... voids) {
            elementDao.insert(new Element("Samsung","A7", "Lollipop 5.0","samsung.com"));
            elementDao.insert(new Element("Samsung","A8", "Lollipop 6.0","samsung.com"));
            elementDao.insert(new Element("Xiaomi","Note 8 Pro", "Lollipop 11.0","xiaomi.com"));
            return null;
        }
    }



}
