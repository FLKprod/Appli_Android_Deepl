package com.maximefalkowski.projetdeepl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Création de l'adapter pour afficher une liste d'objets "Translate" dans une liste View.
public class TranslateAdapter extends BaseAdapter {
    private Context context;
    private final List<Translate> translateList;
    private final LayoutInflater inflater;
    public TranslateAdapter(Context context,List<Translate> translateList){
        this.context = context;
        this.translateList = translateList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    // Fonction pour limiter le nombre de traductions anciennes dans l'utilisateur à 10
    public int getCount() {
        // tant que le nombre de traduction n'est pas superieur à 10
        if (translateList.size() <10){
            // on retourne le nombre d'élément
            return translateList.size();
    }
        //Sinon on le fixe à 10
        else {return 10;}

    }

    // on retourne l'objet "Translate" situé à une position précisée
    @Override
    public Translate getItem(int position) {
        return translateList.get(position);
    }

    // on retourne toujours 0 pour un identifiant unique de l'élément de la liste.
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // retourne une vue pour un élément spécifié dans la liste. Ensuite, elle récupère les informations de l'objet "Translate" actuel en appelant la méthode "getItem". Enfin, elle définit le texte pour les vues TextView correspondant à "txtsaisie_historycell", "languetrad_historycell", "txttrad_historycell", et "date_historycell". La date est définie en formatant la date actuelle à l'aide de SimpleDateFormat et en définissant le texte sur "date_historycell". Enfin, la vue convertView est retournée pour être affichée dans la liste.
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // On assimile le fichier de mise en page "historycell" pour chaque élément.
        convertView = inflater.inflate(R.layout.historycell,null);

        // recupération des informations d'un item
        Translate currentItem = getItem(position);

        // On insere les parametres de l'objet dans les zones de texte bien définies
        TextView txtsaisiearea = convertView.findViewById(R.id.txtsaisie_historycell);
        txtsaisiearea.setText(currentItem.getTxtsaisie());

        TextView languetradarea = convertView.findViewById(R.id.languetrad_historycell);
        languetradarea.setText(currentItem.getLanguetrad());

        TextView txttradarea = convertView.findViewById(R.id.txttrad_historycell);
        txttradarea.setText(currentItem.getTxttrad());

        TextView numbertranslatearea = convertView.findViewById(R.id.date_historycell);

        numbertranslatearea.setText(" Effectuée le " + currentItem.getDate() + " ");
        return convertView;
    }
}
