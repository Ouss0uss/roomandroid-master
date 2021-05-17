package com.example.timothee.roomexemple;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "competence_table")
public class Competence {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name= "nomCompetence")
    private String nomCompetence ;

    @NonNull
    @ColumnInfo(name= "ordreAffiche")
    private int ordreAffiche ;

    public Competence(String nomCompetence,int ordreAffiche) {
        this.nomCompetence = nomCompetence;
        this.ordreAffiche = ordreAffiche ;
    }

    public String getNomCompetence() {
        return nomCompetence;
    }

    public int getOrdreAffiche() {
        return ordreAffiche ;
    }

    public void setOrdreAffiche(@NonNull int ordreAffiche) {
        this.ordreAffiche = ordreAffiche;
    }

    public void setNomCompetence(@NonNull String nomCompetence) {
        this.nomCompetence = nomCompetence;
    }
}
