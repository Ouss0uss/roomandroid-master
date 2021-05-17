package com.example.timothee.roomexemple;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CompetenceDao {
        @Insert
        void insert(Competence competence);

        @Delete
        void deleteCompetence(Competence competence);

        @Query("DELETE FROM competence_table")
        void deleteAll();

        @Query("SELECT * from competence_table ORDER BY ordreAffiche ASC")
        LiveData<List<Competence>> getToutesCompetences();

        // @Query("SELECT MAX(ordreAffiche) from competence_table")
        // int getMaxOrdre();

        @Update
        void updateCompetence(Competence competence);

}
