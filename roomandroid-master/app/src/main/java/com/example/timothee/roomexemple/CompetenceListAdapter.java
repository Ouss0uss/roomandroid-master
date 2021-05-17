package com.example.timothee.roomexemple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CompetenceListAdapter extends RecyclerView.Adapter<CompetenceListAdapter.CompetenceViewHolder> {
    class CompetenceViewHolder extends RecyclerView.ViewHolder {
        private final TextView competenceItemView;
        private CompetenceViewHolder(View competenceView) {
            super(competenceView);
            competenceItemView = competenceView.findViewById(R.id.textView);
        }
    }
    private final LayoutInflater monInflater;
    private List<Competence> mesCompetences; //Competences en cache

    /**
     * Constructeur pour mon adapteur <BR>
     *
     * @param context
     */
    CompetenceListAdapter(Context context) {
        monInflater = LayoutInflater.from(context);
    }
    @Override
    public CompetenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = monInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CompetenceViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(CompetenceViewHolder holder, int position) {
        if (mesCompetences != null) {
            Competence competenceCourante = mesCompetences.get(position);
            holder.competenceItemView.setText(competenceCourante.getNomCompetence());
        } else { // pas de donnée à aficher
            holder.competenceItemView.setText("Aucune compétence à afficher"); }
    }
    void setMesCompetences(List<Competence> competences){
        mesCompetences = competences;
        notifyDataSetChanged();
    }


    public void moveItem(int oldPos, int newPos) {
        Competence uneCompetence = mesCompetences.get(oldPos);
        mesCompetences.remove(oldPos);
        mesCompetences.add(newPos, uneCompetence);
        notifyItemMoved(oldPos, newPos);
    }

    public Competence getCompetenceALaPosition(int position){
        return mesCompetences.get(position);
    }


    // gere le cas où competences pas encore chargées.
    @Override
    public int getItemCount() {
        if (mesCompetences != null)
            return mesCompetences.size();
        else return 0;
    }
}