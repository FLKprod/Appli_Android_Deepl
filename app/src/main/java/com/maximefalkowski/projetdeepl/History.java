package com.maximefalkowski.projetdeepl;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        // Récupération et affichage de la clé DeepL utilisée
        final TextView keyvalue = findViewById(R.id.keyvalue_history);
        keyvalue.setText(getIntent().getStringExtra("keyDeepl").toString());

        loadhistory();
    }

    // fonction pour retourner l'emoji d'un code unicode
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    // Retour au menu principal en cliquant sur le bouton RETOUR
    public void clickBackbtn(View view){
        onBackPressed();
    }



    // Fonction pour netoyer l'historique
    public void clickDeletehistorybtn(View view){
        // On créé une Alert pour prevenir l'utilisateur qu'il n'est pas connécté
        new AlertDialog.Builder(this)
                .setTitle("Etes vous sur de vouloir supprimer l'historique de traductions ? " + getEmojiByUnicode(0x1F97A))
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    //Quand il cliquera sur le bouton
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences historique = getSharedPreferences("Historique", MODE_PRIVATE);
                        Gson gson = new Gson();
                        // on créé un editor pour modifier le fichier local
                        SharedPreferences.Editor editor = historique.edit();

                        // on y mets une liste d'objets "Translate" vide
                        editor.putString("Historique",gson.toJson(new ArrayList<Translate>()));

                        // on applique les changements sur le fichier local
                        editor.apply();

                        // on rechage l'historique
                    }

                })
                .setNegativeButton("Non", null)
                .show();
    }

    // chargement de l'historique des traductions
    public void loadhistory(){
        // on récupère l'instance de SharedPreferences
        SharedPreferences historique = getSharedPreferences("Historique", MODE_PRIVATE);

        // on récupère une chaîne de caractères qui a été stockée
        String Stringhistorique = historique.getString("Historique", null);

        // on va créer un nouveau type qui représente une liste d'objets Translate
        Type listType = new TypeToken<ArrayList<Translate>>() {}.getType();

        // on créé une instance Gson qui nous permettra de convertir des données Java en chaînes JSON et vice versa.
        Gson gson = new Gson();
        List<Translate> historylist  = gson.fromJson(Stringhistorique, listType);

        // on supprime l'élément le plus ancien si l'historique contient plus de 10 traductions
        if(historylist.size()>10){
            historylist.remove(historylist.size()-1);
        }

        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new TranslateAdapter(this, historylist));
    }

    // fonction pour copier une ancienne traduction lorsqu'on clique sur le text traduit ( celui en dessous de la langue )
    public void copytrad(View view){
        TextView txttraduit = findViewById(R.id.txttrad_historycell);
        // on instancie un objet ClipboardManager pour se fournir un accès au service système de gestionnaire de presse-papiers.
        ClipboardManager ClipboardManager = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // on va créer d'un objet ClipData avec le texte provenant de l'objet txttraduit
        ClipData clipData = ClipData.newPlainText("key", txttraduit.getText().toString());
        // on envoie le clipData à la méthode setPrimaryClip pour le stocker dans le presse-papiers
        ClipboardManager.setPrimaryClip(clipData);
        // on affiche un message Toast avec le message "Copié dans le press-papier" pour prevenir l'utilisateur
        Toast copysuccess = Toast.makeText(this, "Copié dans le press-papier " +getEmojiByUnicode(0x1F603), Toast.LENGTH_SHORT);
        copysuccess.show();
    }
}