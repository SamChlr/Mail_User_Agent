package vue;

import controleur.ControleurBoiteMail;
import modele.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class WindowMail extends JFrame{
    private JPanel JPanelFenetre;
    private JButton nouveauMailButton;
    private JTable mailTable;
    private JTextField login_textField;
    private JPasswordField passwordPasswordField;
    private JButton connexionButton;
    private JScrollPane mailJScrollPane;
    private JTextField expediteurTextField;
    private JTextField objetMailTextField;
    private JPanel affichageMailJPanel;
    private JList listPJ;
    private JTextArea contenuMailTextArea;

    private DefaultTableModel modelMail;
    String[] ColumnMailNames = {"Expéditeur", "Objet"};

    // variable liée au mail
    private ArrayList<Mail> MailCourant = new ArrayList<>();

    public WindowMail()
    {
        modelMail = new DefaultTableModel(null, ColumnMailNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rend toutes les cellules non modifiables
            }
        };

        setContentPane(JPanelFenetre);
        setTitle("Service de mails");
        pack(); // Dimensionnez la fenêtre en fonction des composants
        setLocationRelativeTo(null); // Centrez la fenêtre
        setVisible(true);

        //Mise en place des listener
        nouveauMailButton.setActionCommand("nouveau");
        connexionButton.setActionCommand("connexion");

        mailTable.setModel(modelMail);
        mailJScrollPane.setViewportView(mailTable);

        //Controleur de la sélection du tableau de mail
        mailTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mailTable.getSelectedRow();
                if (selectedRow >= 0) {
                    afficherDetailsMail(selectedRow);
                }
            }
        });
    }
    public void setControleur(ControleurBoiteMail c)
    {
        nouveauMailButton.addActionListener(c);
        connexionButton.addActionListener(c);
    }

    public void setMailCourant(ArrayList<Mail> mail)
    {
        MailCourant = mail;
        NotifMail();
        viderMail();
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

    public void NotifMail(){
        JOptionPane.showMessageDialog(null, "Vous avez un/des nouveau.x mail.s", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
    private void updateMail()
    {
        for (Mail email : MailCourant){
            ajoutMailTableau(email.getExpediteur(), email.getSujet());
        }
    }

    //ajouter row table modele.panier
    public void ajoutMailTableau(String expediteur, String objet)
    {
        // Créez un tableau d'objets représentant les données de la ligne à ajouter
        Object[] newRow = {expediteur, objet};

        // Ajoutez la nouvelle ligne au modèle
        modelMail.addRow(newRow);
    }

    //vider le panier
    public void viderMail()
    {
        modelMail.setRowCount(0);
    }

    private void afficherDetailsMail(int rowIndex) {
        Mail mail = MailCourant.get(rowIndex);

        affichageMailJPanel.setVisible(true);
        // Mettre à jour les composants UI avec les détails du mail
        expediteurTextField.setText(mail.getExpediteur());
        objetMailTextField.setText(mail.getSujet());
        contenuMailTextArea.setText(mail.getTexte());
        // Mettre à jour la liste des pièces jointes si nécessaire
        if(mail.getPieceJointe().isEmpty()){
            DefaultListModel<String> listModel = new DefaultListModel<>();

            for (String pieceJointe : mail.getPieceJointe()) {
                listModel.addElement(pieceJointe);
            }

            listPJ.setModel(listModel);
        }
    }
    public void isConnected(){

        nouveauMailButton.setEnabled(true);
        connexionButton.setEnabled(false);
    }

    public void test()
    {

    }

}
