package com.example.timothee.roomexemple;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Competence.class}, version = 1)
public abstract class CompetenceRoomDatabase extends RoomDatabase {

    public abstract CompetenceDao competenceDao() ;
    private static volatile CompetenceRoomDatabase INSTANCE ;

    static CompetenceRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (CompetenceRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            //CompetenceRoomDatabase.class,"competence_database").addCallback(sRoomDatabaseCallback).build();
                            CompetenceRoomDatabase.class,"competence_database").build();
                    // .fallbackToDestructiveMigration()
                }
            }
        }
        return INSTANCE ;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PeuplementDbAsync(INSTANCE).execute();
                }
            };

    private static class PeuplementDbAsync extends AsyncTask<Void, Void, Void> {

        private final CompetenceDao mDao;

        PeuplementDbAsync(CompetenceRoomDatabase db) {

            mDao = db.competenceDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Competence uneNouvelleCompetence = new Competence("Bonjour les newbies",0);
            mDao.insert(uneNouvelleCompetence);
            uneNouvelleCompetence = new Competence("Au boulot !",1);
            mDao.insert(uneNouvelleCompetence);
            return null;
        }
    }
}
