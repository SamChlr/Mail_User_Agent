package controleur;

import modele.*;
import vue.WindowNouveauMail;

import javax.mail.MessagingException;
import javax.swing.JFileChooser;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleurEnvoie implements ActionListener, WindowListener {
    private final WindowNouveauMail fenetre;
    private ArrayList<File> pieceJointe = new ArrayList<>();

    public ControleurEnvoie(WindowNouveauMail display)
    {
        fenetre = display;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("ajouterPJ"))
        {
            // Créez une instance de JFileChooser
            JFileChooser fileChooser = new JFileChooser();

            // Définissez le répertoire de démarrage, les filtres de fichier, etc.
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            // Affichez le dialogue de sélection de fichier
            int result = fileChooser.showOpenDialog(fenetre);

            // Vérifiez si l'utilisateur a sélectionné un fichier
            if (result == JFileChooser.APPROVE_OPTION) {
                // Récupérez le fichier sélectionné
                File selectedFile = fileChooser.getSelectedFile();
                pieceJointe.add(selectedFile);
                System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

                // Mettez à jour le JList dans la fenêtre pour afficher les noms du fichier
                fenetre.setListPJ(pieceJointe);
            }
        }
        if(e.getActionCommand().equals("envoyer"))
        {


            if (pieceJointe.size() == 0) {
                SendSimple envoie = new SendSimple(GestionConnexion.getInstance().getSession());
                String exp = GestionConnexion.getInstance().getUser() + "@" + GestionConnexion.getHost();
                String dest = fenetre.getDest_textField().getText();
                String Sujet = fenetre.getObjet_textField().getText();
                String texte = fenetre.getMail_textArea().getText();

                envoie.SendMailSimple(exp, dest, Sujet, texte);
            }
            else
            {
                SendMultiPart envoie = new SendMultiPart(GestionConnexion.getInstance().getSession());
                String exp = GestionConnexion.getInstance().getUser() + "@" + GestionConnexion.getHost();
                String dest = fenetre.getDest_textField().getText();
                String Sujet = fenetre.getObjet_textField().getText();
                String texte = fenetre.getMail_textArea().getText();
                try {
                    envoie.SendMailMultiPart(exp,dest,Sujet,texte,pieceJointe);
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);

                }

            }
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {}

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
