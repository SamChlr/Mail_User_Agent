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
        while (!Thread.currentThread().interrupted())
        {
            MailReceive rcv = new MailReceive(connexion.getSession(),connexion.getSt());
            ArrayList<Mail> newMailList = rcv.receiveMail();
            if(mailCourant != null && newMailList != null && mailCourant.size() != newMailList.size())
            {
                mailCourant = newMailList;
                vue.setMailCourant(mailCourant);
                System.out.println("Surveillance : NOUVEAU MAIL");
            }
            if(mailCourant == null && newMailList != null)
            {
                mailCourant = newMailList;
                vue.setMailCourant(mailCourant);
                System.out.println("Surveillance : NOUVEAU MAIL");
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
