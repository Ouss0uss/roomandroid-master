package com.example.timothee.roomexemple;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;

public class AfficheMatiereWebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affiche_matiere_web);

        Intent intent = getIntent();

        String messageRecu = intent.getStringExtra(MainActivity.EXTRA_MESSAGE) ;

        WebView affichageWeb = findViewById(R.id.navigateur);


        //if (messageRecu.equalsIgnoreCase("slam3"))
        //   monDiffuseur.setText(this.getText(R.string.texte_sio));

        if (messageRecu.equalsIgnoreCase("slam4")) {
            affichageWeb.loadUrl("file:///android_asset/SLAM4.html");
        }
        else
            affichageWeb.loadUrl("file:///android_asset/erreur.html");
    }

}
