package modele;

import vue.WindowMail;

import java.util.ArrayList;

public class Surveillance extends Thread {

    private WindowMail vue;
    private GestionConnexion connexion;

    private ArrayList<Mail> mailCourant = new ArrayList<>();

    public Surveillance(WindowMail v, GestionConnexion c)
    {
        vue= v;
        connexion = c;
        mailCourant = vue.getMailCourant();
    }

    @Override
    public void run()
    {

    }

}
