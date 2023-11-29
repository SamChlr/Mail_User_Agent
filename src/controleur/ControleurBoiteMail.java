package controleur;

import vue.WindowMail;
import vue.WindowNouveauMail;
import modele.*;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

public class ControleurBoiteMail implements ActionListener, WindowListener {
    private final WindowMail fenetre;
    private int logged = 0;;

    private GestionConnexion connexion;



    public ControleurBoiteMail(WindowMail display)
    {
        fenetre = display;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("connexion"))
        {
            //action de vérification du login et MDP
            connexion.getInstance();
            connexion.getInstance().setPassword(fenetre.getPassword());
            connexion.getInstance().setUser(fenetre.getLogin());
            try {
                connexion.getInstance().connexion();
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }

            Surveillance thread = new Surveillance(fenetre, connexion.getInstance());
            thread.start();
            fenetre.isConnected();

        }
        if(e.getActionCommand().equals("nouveau"))
        {
            // Créez une instance de WindowNouveauMail
            WindowNouveauMail nouveauMail = new WindowNouveauMail();

            // Créez une instance de ControleurEnvoie et définissez-la comme contrôleur pour WindowNouveauMail
            ControleurEnvoie controleurNouveauMail = new ControleurEnvoie(nouveauMail);
            nouveauMail.setControleur(controleurNouveauMail);

            // Affichez la fenêtre WindowNouveauMail
            nouveauMail.setVisible(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        fenetre.viderDossierPiecesJointes();
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
