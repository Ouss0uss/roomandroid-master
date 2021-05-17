package com.example.timothee.roomexemple;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CompetenceViewModel uneCompetenceViewModel;
    public static final int NEW_COMPETENCE_ACTIVITY_REQUEST_CODE = 1;
    public static final String EXTRA_MESSAGE = "passe a ton voisin";

    public CompetenceListAdapter adapter;

    /***
     * On construit tout dans le onCreate:
     * <OL>
     *     <LI> L'activité principale</LI>
     *     <LI> Le FAB: ajout Listener dessus </LI>
     *     <LI> On recupere notre RecyclerView</LI>
     *     <LI> On recupere notre ViewModel et on y associe un observateur</LI>
     *     <LI> On utilise un ItemTouchHelper pour suivre le Drag & Drop et le Swipe</LI>
     * </OL>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NouvelleCompetence.class);
                startActivityForResult(intent, NEW_COMPETENCE_ACTIVITY_REQUEST_CODE);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new CompetenceListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // Decoration et division des items
        //RecyclerView.ItemDecoration delimiteur = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(delimiteur);

        uneCompetenceViewModel = ViewModelProviders.of(this).get(CompetenceViewModel.class);

        uneCompetenceViewModel.getMesCompetences().observe(this, new Observer<List<Competence>>() {
            @Override
            public void onChanged(@Nullable List<Competence> competences) {
                adapter.setMesCompetences(competences);
            }
        });

        ItemTouchHelper monHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder origine,
                                  @NonNull RecyclerView.ViewHolder destination) {
                int position_origin = origine.getAdapterPosition();
                int position_destination = destination.getAdapterPosition();
                adapter.moveItem(position_origin, position_destination);
                //adapter.notifyItemMoved(position_origin,position_destination);
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Competence maCompetence = adapter.getCompetenceALaPosition(position);

                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(MainActivity.this, "Suppression de " +
                            maCompetence.getNomCompetence(), Toast.LENGTH_LONG).show();

                    uneCompetenceViewModel.deleteCompetence(maCompetence);
                }
                else if (direction == ItemTouchHelper.RIGHT) {

                    String nomCompetence = maCompetence.getNomCompetence() ;

                    Toast.makeText(MainActivity.this, "Tu brûles coco " +
                            nomCompetence , Toast.LENGTH_LONG).show();

                    if (nomCompetence.equalsIgnoreCase("SLAM3")) {
                        Intent intentmessage = new Intent(MainActivity.this,
                                AfficheMatiereActivity.class);
                        // intent.putExtra(MainActivity.EXTRA_MESSAGE,nomMatiere.getText().toString()) ;
                        intentmessage.putExtra(MainActivity.EXTRA_MESSAGE,nomCompetence) ;
                        adapter.notifyDataSetChanged();
                        startActivity(intentmessage);
                    }

                    else {
                        Intent intentmessage = new Intent(MainActivity.this,
                                AfficheMatiereWebActivity.class);
                        // intent.putExtra(MainActivity.EXTRA_MESSAGE,nomMatiere.getText().toString()) ;
                        intentmessage.putExtra(MainActivity.EXTRA_MESSAGE,nomCompetence) ;
                        adapter.notifyDataSetChanged();
                        startActivity(intentmessage);
                    }

                }

            }
        });

        monHelper.attachToRecyclerView(recyclerView);
    }

    /****
     * C'est dans la méthode onStop qu'on sauvegarde notre liste de
     * compétences dans le bon ordre, qui est celui de l'affichage.
     */
    @Override
    protected void onStop() {
        super.onStop();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            Competence compASauvegarder = adapter.getCompetenceALaPosition(i);
            compASauvegarder.setOrdreAffiche(i);
            uneCompetenceViewModel.update(compASauvegarder);
        }

    }

    /***
     * Dans cette methode on capture le retour de l'activité d'ajout de nouvelle compétence<BR>
     *     si ça se passe bien, on insere la nouvelle competence
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_COMPETENCE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Competence competence = new Competence(data.getStringExtra(NouvelleCompetence.EXTRA_REPLY),
                    adapter.getItemCount() + 1);
            uneCompetenceViewModel.insert(competence);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /***
     * Methode redefinie pour pouvoir supprimer toutes les compétences d'un coup
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.supprime_toutes_competences) {
            Toast.makeText(this, "Attention on supprime tout nous ...",
                    Toast.LENGTH_SHORT).show();
            uneCompetenceViewModel.supprimeTout();
            return true;
        }
        else if (id == R.id.modificationDeCompetence) {
            Toast.makeText(this, "Vous allez pouvoir modifiez le texte",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
