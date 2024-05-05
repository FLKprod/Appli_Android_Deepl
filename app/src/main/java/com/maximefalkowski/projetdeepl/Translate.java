package com.maximefalkowski.projetdeepl;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Translate {
        private final String txtsaisie;   // Texte saisie par l'utilisateur
        private final String languetrad;  // Langue de traduction séléctionée par l'utilisateur
        private final String txttrad;     // Text traduit par DeepL
        private final String date;          // Date à laquelle la traduction a été éfféctuée
        public Translate(String txtsaisie,String languetrad, String txttrad){
            this.txtsaisie = txtsaisie;
            this.languetrad = languetrad;
            this.txttrad = txttrad;

            // Ici, on créé un format de date et on la date à laquelle la traduction a été éfféctuée
            Date date = new Date();
            SimpleDateFormat f = new SimpleDateFormat("dd'/'MM'/'yyyy' à 'HH':'mm':'ss");
            this.date = f.format(date);

        }
        public String getTxtsaisie(){
            return txtsaisie;
        }
        public String getLanguetrad(){
            return languetrad;
        }
        public String getTxttrad(){
            return txttrad;
        }
        public String getDate() { return date;}

    @NonNull
    @Override
    public String toString() {
        return "txtsaisie : "+txtsaisie + "\n languetrad : " + languetrad + "\n txttrad : "+txttrad;
    }
}
