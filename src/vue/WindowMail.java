package vue;

import controleur.ControleurBoiteMail;

import javax.swing.*;

public class WindowMail extends JFrame{
    private JPanel JPanelFenetre;
    private JButton nouveauMailButton;
    private JTable table1;
    private JTextField login_textField;
    private JPasswordField passwordPasswordField;
    private JButton connexionButton;

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
}
