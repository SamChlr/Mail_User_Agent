package modele;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.ArrayList;

public class Mail {
    private String MessageID;
    private String Expediteur;
    private String Destinataire;
    private String Sujet;
    private String texte;
    private ArrayList<String> pieceJointe = new ArrayList<>();

    public Mail()
    {}

    //getters and setters
    public String getMessageID() {
        return MessageID;
    }
    public void setMessageID(String messageID) {
        MessageID = messageID;
    }
    public String getExpediteur() {
        return Expediteur;
    }

    public void setExpediteur(String expediteur) {
        Expediteur = expediteur;
    }

    public String getDestinataire() {
        return Destinataire;
    }

    public void setDestinataire(String destinataire) {
        Destinataire = destinataire;
    }

    public String getSujet() {
        return Sujet;
    }

    public void setSujet(String sujet) {
        Sujet = sujet;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public ArrayList<String> getPieceJointe() { return pieceJointe;}

    public String getUnepieceJointe(int indice){return pieceJointe.get(indice);}

    public void ajouterPieceJointe(String PJ)
    {
        pieceJointe.add(PJ);
    }
}
