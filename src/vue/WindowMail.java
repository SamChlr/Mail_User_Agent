package vue;

import controleur.ControleurBoiteMail;
import modele.*;

import javax.mail.Message;
import javax.swing.*;
import java.util.ArrayList;

public class WindowMail extends JFrame{
    private JPanel JPanelFenetre;
    private JButton nouveauMailButton;
    private JTable table1;
    private JTextField login_textField;
    private JPasswordField passwordPasswordField;
    private JButton connexionButton;

    // variable liée au mail
    private ArrayList<Mail> MailCourant = new ArrayList<>();

    public WindowMail()
    {
        setContentPane(JPanelFenetre);
        setTitle("Service de mails");
        pack(); // Dimensionnez la fenêtre en fonction des composants
        setLocationRelativeTo(null); // Centrez la fenêtre
        setVisible(true);

        //Mise en plce des listener
        nouveauMailButton.setActionCommand("nouveau");
        connexionButton.setActionCommand("connexion");
    }
    public void setControleur(ControleurBoiteMail c)
    {
        nouveauMailButton.addActionListener(c);
        connexionButton.addActionListener(c);
    }

    public void setMailCourant(ArrayList<Mail> mail)
    {
        MailCourant = mail;
        updateMail();
    }

    public  ArrayList<Mail> getMailCourant(){ return MailCourant;}

    public String getLogin()
    {
        return login_textField.getText();
    }

    public String getPassword()
    {
        return passwordPasswordField.getText();
    }

    private void updateMail()
    {
        //à faire : mettre à jour la table + notification
    }


}
