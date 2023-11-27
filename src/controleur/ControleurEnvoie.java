package controleur;

import modele.*;
import vue.WindowNouveauMail;

import javax.swing.JFileChooser;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurEnvoie implements ActionListener, WindowListener {
    private final WindowNouveauMail fenetre;
    private String cheminPieceJointe = "";

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
                cheminPieceJointe = selectedFile.getAbsolutePath();
                System.out.println("Fichier sélectionné : " + cheminPieceJointe);

                // Mettez à jour le JLabel dans la fenêtre pour afficher le nom du fichier
                fenetre.getNomPJ_JLabel().setText(selectedFile.getName());
            }
        }
        if(e.getActionCommand().equals("envoyer"))
        {

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
