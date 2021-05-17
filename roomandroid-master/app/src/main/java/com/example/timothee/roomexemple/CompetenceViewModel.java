package com.example.timothee.roomexemple;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CompetenceViewModel extends AndroidViewModel {

    private CompetenceRepository monRepository;

    private LiveData<List<Competence>> mesCompetences;

    /**
     * Constructeur qui va permettre d'initialiser le repository
     * et la liste de compétences
     * @param application
     */
    public CompetenceViewModel (Application application) {
        super(application);
        monRepository = new CompetenceRepository(application);
        mesCompetences = monRepository.getMesCompetences();
    }

    /**
     * accesseur: renvoie la liste des competences, observées via LiveData
     * @return
     */

    LiveData<List<Competence>> getMesCompetences() { return mesCompetences; }

    public void insert(Competence uneCompetence) { monRepository.insert(uneCompetence); }

    public void update(Competence uneCompetence) {monRepository.updateCompetence(uneCompetence);}

    public void deleteCompetence(Competence competenceASupprimer){
        monRepository.deleteCompetence(competenceASupprimer);
    }

    public void supprimeTout(){
        monRepository.supprimeTout();
    }

}
