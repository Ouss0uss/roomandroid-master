package com.example.timothee.roomexemple;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CompetenceRepository {
    private CompetenceDao maCompetenceDao ;
    private LiveData<List<Competence>> mesCompetences ;

    CompetenceRepository(Application application){
        CompetenceRoomDatabase database = CompetenceRoomDatabase.getDatabase(application);
        maCompetenceDao = database.competenceDao() ;
        mesCompetences = maCompetenceDao.getToutesCompetences() ;
    }
    LiveData<List<Competence>> getMesCompetences(){
        return mesCompetences ;
    }


    /**
     * Insere une nouvelle Competence passee en parametre par le ViewModel <BR>
     *     Lance une tâche asynchrone en background qui fait l'insertion en utilisant la DAO <BR>
     *         Pas de retour
     * @param uneCompetence
     */
    public void insert (Competence uneCompetence){
        new insertAsyncTask(maCompetenceDao).execute(uneCompetence);
    }

    /***
     * La carrément ça détruit tout <BR>
     *
     *     C'est pour ça qu'on le cache dans un menu
     *
     */
    public void deleteCompetence (Competence uneCompetence){
        new deleteCompetenceAsyncTask(maCompetenceDao).execute(uneCompetence);
    }

    public void supprimeTout(){
        new supprimeToutesCompetencesAsyncTask(maCompetenceDao).execute();
    }

    public void updateCompetence (Competence uneCompetence){
        new updateCompetenceAsyncTask(maCompetenceDao).execute(uneCompetence);
    }

    /***
     * Tache asynchrone de suppression d'une competence <BR>
     *     prend en paramètre une compétence <BR>
     *     pas de valeur de retour
     */

    private static class insertAsyncTask extends AsyncTask<Competence, Void, Void> {

        private CompetenceDao maTacheDao;

        insertAsyncTask(CompetenceDao dao) {
            maTacheDao = dao;
        }

        @Override
        protected Void doInBackground(final Competence... params) {
            maTacheDao.insert(params[0]);
            return null;
        }
    }


    /***
     * La carrément ça détruit tout <BR>
     *
     *     C'est pour ça qu'on le cache dans un menu
     *
     */
    private static class supprimeToutesCompetencesAsyncTask extends AsyncTask<Void,Void,Void>{
        private CompetenceDao maTacheDao;

        supprimeToutesCompetencesAsyncTask(CompetenceDao uneDao){
            maTacheDao = uneDao ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            maTacheDao.deleteAll();
            return null;
        }
    }

    private static class deleteCompetenceAsyncTask extends AsyncTask<Competence,Void,Void>{
        private CompetenceDao maTacheDao;

        deleteCompetenceAsyncTask(CompetenceDao dao){
            maTacheDao = dao;
        }

        @Override
        protected Void doInBackground(final Competence... params) {
            maTacheDao.deleteCompetence(params[0]);
            return null;
        }
    }

    private static class updateCompetenceAsyncTask extends AsyncTask<Competence,Void,Void>{
        private CompetenceDao monDao;
        updateCompetenceAsyncTask(CompetenceDao dao){
            monDao = dao;
        }

        @Override
        protected Void doInBackground(Competence... params) {
            monDao.updateCompetence(params[0]);
            return null;
        }
    }

}
