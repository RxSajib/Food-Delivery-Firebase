package com.example.mealmonkey.Network.RoomDatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mealmonkey.Data.Model.CartModel;
import com.example.mealmonkey.Data.Model.PaymentCardModel;

@androidx.room.Database(entities = {PaymentCardModel.class, CartModel.class}, version = 8)
public abstract class Database extends RoomDatabase {

    public abstract Dao cardDao();

    public static volatile Database INSTANT;

    public static Database getdatabase(Context context){
        if(INSTANT == null){
            synchronized (Database.class){
                if(INSTANT == null){
                    INSTANT = Room.databaseBuilder(context.getApplicationContext()
                            , Database.class, "RoomDB")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }

            }
        }

        return INSTANT;
    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new RemoveCallback(INSTANT).execute();
        }
    };

   static class RemoveCallback extends AsyncTask<Void, Void, Void>{

        private Dao cardDao;
        public RemoveCallback(Database database){
            cardDao = database.cardDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cardDao.DeleteCard();
            return null;
        }
    }
}
