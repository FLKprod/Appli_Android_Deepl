package com.maximefalkowski.projetdeepl;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setContentView(R.layout.activity_settings);
        final LinearLayout linearlayout = findViewById(R.id.infoskey);
        if(getIntent().getExtras()!=null && getIntent().getStringExtra("keyDeepl").toString() != ""){
            loadsettings();
        }
        else{
            linearlayout.setVisibility(View.INVISIBLE);
        }
    }
    // fonction pour retourner l'emoji d'un code unicode
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    // Retour au menu principal en cliquant sur le bouton RETOUR ou en cliquant sur le bouton retour de Android
    public void clickBackbutton(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        final EditText key = findViewById(R.id.saisiekeyDeepl);
        Intent gotoMain = new Intent(this, MainActivity.class);
        if(getIntent().getExtras()!=null && getIntent().getStringExtra("keyDeepl").toString() != "Vous n'etes pas connecté !") {
            gotoMain.putExtra("keyDeepl", getIntent().getStringExtra("keyDeepl").toString());
        }
        else{

        }
        startActivity(gotoMain);
    }


    public void clickvalide(View view) {
        loadsettings();
    }

    // fonction pour verifier si la clé et valide et, si oui, afficher toutes ses informations
    public void loadsettings(){
        Toast cleinvalide = Toast.makeText(this,"Clé Deepl invalide "+getEmojiByUnicode(0x1F97A), Toast.LENGTH_SHORT);
        Toast clevalide = Toast.makeText(this,"Clé Deepl valide "+ getEmojiByUnicode(0x1F603), Toast.LENGTH_SHORT);

        final LinearLayout linearlayout = findViewById(R.id.infoskey);
        final EditText saisiekey = findViewById(R.id.saisiekeyDeepl);
        final TextView showkey = findViewById(R.id.showkey);
        final TextView shownbreutilised = findViewById(R.id.shownbreutilised);
        final TextView shownbremax = findViewById(R.id.shownbremax);
        final TextView shownbredispo = findViewById(R.id.shownbredispo);
        final TextView showpourcentage = findViewById(R.id.showpourcentage);
        final ProgressBar barutilisation = findViewById(R.id.barUtilisation);

        // Pour conserver le contexte de l’activité
        Context that = this;
        if (!TextUtils.isEmpty(saisiekey.getText().toString())) {
            AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization","DeepL-Auth-Key "+saisiekey.getText().toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // on recupere les infos désirées et on les intègre dans des zones de texte bien définies
                            int count = response.getInt("character_count");
                            int limit = response.getInt("character_limit");
                            linearlayout.setVisibility(View.VISIBLE);

                            showkey.setText("Clé Deepl utilisateur : " + saisiekey.getText().toString());
                            shownbremax.setText("Nombre de caractères utilisés : "+count);
                            shownbreutilised.setText("Nombre de caractères maximum : "+limit);
                            shownbredispo.setText("Nombre de caractères encore disponibles : "+(limit-count));

                            // on calcul le pourcentage d'utilisation et on l'affichage
                            float percentage = count * 100 / limit ;
                            showpourcentage.setText("Pourcentage d'utilisation : "+percentage + " %");
                            barutilisation.setProgress((int) percentage);

                            // on compare la valeur du pourcentage pour changer la couleur de la progress bar
                            // blanc pour pourcentage <= 50%, sinon orange pour pourcentage <= 75 %, sinon rouge

                            if(percentage <= 50 ){   // vert
                                barutilisation.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                            }
                            else if (percentage <= 75) { // orange
                                barutilisation.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FF8000")));
                            }
                            else{   //rouge
                                barutilisation.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFB71C1C")));
                            }
                            // on informe l'utilisateur que sa clé est valide par une notification Toast
                            clevalide.show();

                            // on rentre dans un Extra la clé DeepL pour la conserver lorsqu'on changera de vue
                            getIntent().putExtra("keyDeepl", saisiekey.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        linearlayout.setVisibility(View.INVISIBLE);
                        getIntent().putExtra("keyDeepl","Vous n'etes pas connecté !");
                        cleinvalide.show();
                    }
                });
        }
    }
}