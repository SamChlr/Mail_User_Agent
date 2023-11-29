package vue;

import controleur.ControleurEnvoie;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class WindowNouveauMail extends JFrame{
    private JPanel JPanelNouveauMail;
    private JTextField dest_textField;
    private JTextField objet_textField;
    private JButton ajouterPieceJointeButton;
    private JTextArea mail_textArea;
    private JButton envoyerButton;
    private JList listPJ;

    public WindowNouveauMail()
    {
        setContentPane(JPanelNouveauMail);
        setTitle("Service de mails");
        pack(); // Dimensionnez la fenêtre en fonction des composants
        setLocationRelativeTo(null); // Centrez la fenêtre
        setVisible(true);

        //Mise en plce des listener
        ajouterPieceJointeButton.setActionCommand("ajouterPJ");
        envoyerButton.setActionCommand("envoyer");
    }
    public void setControleur(ControleurEnvoie c)
    {
        ajouterPieceJointeButton.addActionListener(c);
        envoyerButton.addActionListener(c);
    }

    //GETTER
    public JTextField getDest_textField() {
        return dest_textField;
    }

    public JTextField getObjet_textField() {
        return objet_textField;
    }

    public JTextArea getMail_textArea() {
        return mail_textArea;
    }

    public JList getListPJ() {
        return listPJ;
    }

    public void setListPJ(ArrayList<File> pieceJointe){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (File nomFichier : pieceJointe) {
            model.addElement(nomFichier.getName());
        }
        listPJ.setModel(model);
    }
}
