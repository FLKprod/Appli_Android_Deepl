package com.maximefalkowski.projetdeepl;

public class Language {
    private String language;
    private String name;

    public Language(String language, String name) {
        this.language = language;
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public String toString() {

        // on définit une constante "flagOffset" qui sera utilisée pour décaler les codepoints ASCII
        // vers les codepoints Unicode correspondants aux drapeaux de pays.
        int flagOffset = 0x1F1E6;

        // définit une constante "asciiOffset" qui sera utilisée pour décaler
        // les codepoints des premiers caractères de la chaîne "language" vers les codepoints ASCII.
        int asciiOffset = 0x41;

        // on corrige certains codes qui ne sont pas similaires aux initiales proposées par DeepL
        switch ( language ) {
            case "CS": language = "CZ";break;   // drapeau tchèque
            case "DA": language = "DK";break;   // drapeau danois
            case "EL": language = "GR";break;   // drapeau grecque
            case "EN": language = "GB";break;   // drapeau anglais
            case "ET" : language = "EE";break;  // drapeau estonien
            case "JA": language = "JP";break;   // drapeau japonais
            case "KO": language= "KR";break;    // drapeau coréen
            case "UK": language = "UA";break;   // drapeau ukrainien
            case "SL" : language = "SI";break;  // drapeau slovenien
            case "SV" : language = "SE";break;  // drapeau suédois
            case "NB" : language="NO";break;    // drapeau norvégien
            case "ZH": language = "CN";break;   // drapeau chinois
            default:break;
        }
        // on calcule le codepoint du premier et deuxieme caractère
        int firstChar = Character.codePointAt(language, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(language, 1) - asciiOffset + flagOffset;

        // crée une nouvelle chaîne de caractères en utilisant la méthode toChars()
        // pour convertir les codepoints en caractères Unicode, en assimilant les deux caractères obtenus pour former le drapeau de pays.
        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));

        // on retourne le drapeau puis le nom de la langue
        return flag + " " +name; }


}
