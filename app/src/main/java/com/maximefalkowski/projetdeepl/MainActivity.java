package com.maximefalkowski.projetdeepl;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button connexionbtn = findViewById(R.id.settingsbtn);
        final TextView keyvalue = findViewById(R.id.keyvalue);


        // Verification d'un connexion valide ou non
        // si il existe au moins un StringExtra "keyDeepL" ou que le StringExtra contenant la Clé DeepL contient le texte "Vous n'etes pas connecté !"
        if(getIntent().getStringExtra("keyDeepl")!=null && getIntent().getStringExtra("keyDeepl").toString() != "Vous n'etes pas connecté !") {
            //Ca veut dire qu'on est connecté
            keyvalue.setText(getIntent().getStringExtra("keyDeepl").toString());
            connexionbtn.setBackgroundColor(Color.parseColor("#FF4CAF50"));
            loadLanguages();
        }
        // Sinon, cela veut dire qu'on ne s'est pas connécté ou que notre clé DeepL n'est pas valide
        else{
            keyvalue.setText("Vous n'etes pas connecté !");
            connexionbtn.setBackgroundColor(Color.parseColor("#FFB71C1C"));
        }

    }
    // fonction pour retourner l'emoji d'un code unicode
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public void clickHistorybtn(View view){
        // si il existe au moins un StringExtra "keyDeepL" ou que le StringExtra contenant la Clé DeepL contient le texte "Vous n'etes pas connecté !"
        if(getIntent().getExtras() != null && !getIntent().getStringExtra("keyDeepl").equals("Vous n'etes pas connecté !")) {
            // On est connecté donc on peut se rediriger vers l'Activity History
            Intent gotoHistory = new Intent(this, History.class);
            gotoHistory.putExtra("keyDeepl", getIntent().getStringExtra("keyDeepl"));    // On envoie la valeur de la clé DeepL
            startActivity(gotoHistory);
        } else {
            // On redirige l'utilisateur vers l'activity pour se connecter
            redirectionConnexion();
        }

    }

    public void clickParametresbtn(View view){
        Intent gotoParametre = new Intent(this,Settings.class);
        // si il existe au moins un StringExtra "keyDeepL" ou que le StringExtra contenant la Clé DeepL contient le texte "Vous n'etes pas connecté !"
        if(getIntent().getExtras()!=null && getIntent().getStringExtra("keyDeepl").toString() != "") {
            // Cela permet de ne pas avoir à se reconnecter en allant dans l'activité Settings
            gotoParametre.putExtra("keyDeepl", getIntent().getStringExtra("keyDeepl").toString());  // On envoie la valeur de la clé DeepL
        }
        startActivity(gotoParametre);
    }

    // Bouton COPIER pour copier le texte traduit ( équivalent au CTRL-C )
    public void clickcopybtn(View view){
        final TextView txttraduit = findViewById(R.id.txttraduit);
        final TextView keyvalue = findViewById(R.id.keyvalue);

        // On verifie si l'utilisateur est connécté
        if(keyvalue.getText() == "Vous n'etes pas connecté !"){
            // Sinon, on lui demande de se connecter et le mene vers l'activity pour se connecter
            redirectionConnexion();
        }

        // sinon, on copie le texte dans
        else {
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

    public void redirectionConnexion(){
        // On créé une Alert pour prevenir l'utilisateur qu'il n'est pas connécté
        new AlertDialog.Builder(this)
                .setTitle("Hmmmmm.... Vous n'êtes pas connécté " + getEmojiByUnicode(0x1F97A))
                .setMessage("Veuillez vous connecter avec votre clé DeepL")
                .setPositiveButton("Se Connecter", new DialogInterface.OnClickListener() {
                    @Override
                    //Quand il cliquera sur le bouton
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent gotoParametre = new Intent(MainActivity.this,Settings.class);
                        startActivity(gotoParametre);       // On le redirige vers l'acitvity Settings
                    }
                }).show();
    }

    public void clickinversebtn(View view){
        // on inverse le texte saisie avec le texte traduit et on retraduit par dessus
        final TextView txttraduit = findViewById(R.id.txttraduit);
        final EditText editText = findViewById(R.id.saisietxt);

        String savetxt = editText.getText().toString();
        editText.setText(txttraduit.getText().toString());
        txttraduit.setText(savetxt);
        translate();
    }

    public void loadLanguages()
    {
        final Spinner languetrad = findViewById(R.id.languetrad);
        final SharedPreferences alllanguages = getSharedPreferences("Languages",MODE_PRIVATE);
        final TextView keyvalue = findViewById(R.id.keyvalue);

        // Pour conserver le contexte de l’activité
        Context that = this;
        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization","DeepL-Auth-Key " + keyvalue.getText().toString())
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                        // Liste dans laquelle seront stockés les langues
                        ArrayList<Language> languageList = new ArrayList<>();

                        //Stockage interne des languages
                        SharedPreferences.Editor editor = alllanguages.edit();
                        ArrayList<String> languageListgetLanguage = new ArrayList<>();

                        // Pour chaque langue
                        for(int i = 0; i < response.length(); ++i)
                        {
                            // On récupère les données de la langue
                            final JSONObject language = response.getJSONObject(i);
                            // On ajoute les données à la liste des langues
                            languageList.add(new Language(
                                    language.getString("language"),
                                    language.getString("name")
                            ));
                            languageListgetLanguage.add(languageList.get(i).getLanguage());


                        }
                        Gson gson = new Gson();
                        editor.putString("Languages",gson.toJson(languageListgetLanguage));
                        editor.apply();
                        // Création d’un adaptateur permettant d’afficher les Langues dans un Spinner
                        ArrayAdapter<Language> adapter = new ArrayAdapter<>(
                                that,
                                android.R.layout.simple_spinner_dropdown_item,
                                languageList
                        );

                        // On ajoute l'Adapter aux spinner déidé pour les langues
                        Spinner spinnertexttrad = findViewById(R.id.languetrad);
                        spinnertexttrad.setAdapter(adapter);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println("Error Languages");
                    }
                });
    }



    // Quand on clique sur le bouton translate
    public void clicktranslatebtn(View view) {
        translate();
    }

    //fonction de traduction ( qui n'est pas directement reliée au bouton TRADUIRE vu qu'on execute aussi cette fonction en appuyant sur le bouton INVERSER
    public void translate(){
        final EditText editText = findViewById(R.id.saisietxt);
        final TextView txttraduitarea = findViewById(R.id.txttraduit);
        final Spinner languetrad = findViewById(R.id.languetrad);
        final TextView keyvalue = findViewById(R.id.keyvalue);

        // Vérification si l'utilisateur est connecté
        if ("Vous n'etes pas connecté !".equals(keyvalue.getText().toString())) {
            redirectionConnexion(); // Redirection de l'utilisateur pour se connecter
        } else {
            Gson gson = new Gson();
            SharedPreferences allLanguages = getSharedPreferences("Languages", MODE_PRIVATE);
            String languagesJson = allLanguages.getString("Languages", null);
            if (languagesJson == null) {
                // Gérer le cas où la liste des langues n'a pas été correctement récupérée
                return;
            }
            try {
                // Conversion de la chaîne JSON en liste de langues
                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> languagesList = gson.fromJson(languagesJson, listType);
                int selectedLanguagePosition = languetrad.getSelectedItemPosition();
                if (selectedLanguagePosition < 0 || selectedLanguagePosition >= languagesList.size()) {
                    // Gérer le cas où aucune langue n'est sélectionnée
                    return;
                }

                AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                        .addHeaders("Authorization", "DeepL-Auth-Key " + keyvalue.getText().toString())
                        .addBodyParameter("text", editText.getText().toString())
                        .addBodyParameter("target_lang", languagesList.get(selectedLanguagePosition))
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray translations = response.getJSONArray("translations");
                                    JSONObject translation = translations.getJSONObject(0);
                                    String detectedLanguage = translation.getString("detected_source_language");
                                    String translatedText = translation.getString("text");
                                    txttraduitarea.setText(translatedText);
                                    // Ajout de la traduction à l'historique
                                    Translate translate = new Translate(editText.getText().toString(), languetrad.getSelectedItem().toString(), translatedText);
                                    addtoHistory(translate);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // Gérer les erreurs de parsing JSON
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                // Gérer les erreurs de réseau
                                anError.printStackTrace();
                            }
                        });
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                // Gérer les erreurs de conversion JSON
            }
        }
    }



    public void addtoHistory(Translate translate){
        final EditText editText = findViewById(R.id.saisietxt);
        final TextView txttraduitarea=findViewById(R.id.txttraduit);
        final Spinner languetrad = findViewById(R.id.languetrad);

        // on recupere le fichier local appelé "Historique"
        SharedPreferences historique = getSharedPreferences("Historique", MODE_PRIVATE);
        String gethistorique = historique.getString("Historique", null);

        // on créé un Type pour pouvoir recupere le contenu du fichier interne comme etant
        // une ArrayList d'objet "Translate"
        Type listType = new TypeToken<ArrayList<Translate>>() {}.getType();
        Gson gson = new Gson();
        // qu'on integre dans un ArrayList composé d'objets Translate
        ArrayList<Translate> newhistorique = gson.fromJson(gethistorique, listType);


        // Puis on créé un editor pour modifier le fichier
        SharedPreferences.Editor editor = historique.edit();
        // On ajoute l'objet Translate en paramètre au debut du tableau
        newhistorique.add(0,translate);
        gson = new Gson();
        //On integre le tableau modifié dans le fichier local "Historique"
        editor.putString("Historique",gson.toJson(newhistorique));
        // On applique les modifications
        editor.apply();
    }
}