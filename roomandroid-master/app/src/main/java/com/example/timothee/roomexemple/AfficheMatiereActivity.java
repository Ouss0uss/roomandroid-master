package com.example.timothee.roomexemple;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class AfficheMatiereActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affiche_matiere_string);

        Intent intent = getIntent();

        String messageRecu = intent.getStringExtra(MainActivity.EXTRA_MESSAGE) ;

        TextView monDiffuseur = findViewById(R.id.diffuseMessage);
        WebView affichageWeb = findViewById(R.id.navigateur);


        //if (messageRecu.equalsIgnoreCase("slam3"))
        //   monDiffuseur.setText(this.getText(R.string.texte_sio));

        if (messageRecu.equalsIgnoreCase("slam3"))
            monDiffuseur.setText(Html.fromHtml(getString(R.string.texte_slam3),1));

        else if (messageRecu.equalsIgnoreCase("slam4")) {
            affichageWeb.loadUrl("file:///android_asset/SLAM4.html");
        }
        else
            monDiffuseur.setText(messageRecu);


    }


}
