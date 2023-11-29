package vue;

import controleur.ControleurBoiteMail;
import modele.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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
    private JScrollPane contenuJScrollPane;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Mise en place des listener
        nouveauMailButton.setActionCommand("nouveau");
        connexionButton.setActionCommand("connexion");

        mailTable.setModel(modelMail);
        mailJScrollPane.setViewportView(mailTable);

        contenuJScrollPane.setViewportView(contenuMailTextArea);


        //Controleur de la sélection du tableau de mail
        mailTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = mailTable.getSelectedRow();
                if (selectedRow >= 0) {
                    afficherDetailsMail(selectedRow);
                }
            }
        });

        listPJ.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int index = listPJ.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String pieceJointeSelectionnee = (String) listPJ.getModel().getElementAt(index);
                        telechargerPieceJointe(pieceJointeSelectionnee);
                    }
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
        updateMail(MailCourant);
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
    private void updateMail(ArrayList<Mail> mailCourant)
    {
        for (Mail email : mailCourant){
            ajoutMailTableau(email.getExpediteur(), email.getSujet());
        }
    }

    //ajouter row table modele.panier
    public void ajoutMailTableau(String expediteur, String objet)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Object[] newRow = {expediteur, objet};
                modelMail.addRow(newRow);
            }
        });
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
            // Affichage des pièces jointes
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String pieceJointe : mail.getPieceJointe()) {
                listModel.addElement(pieceJointe);
                System.out.println("MAAAAA PJ : "+pieceJointe);
            }
            listPJ.setModel(listModel);
        }
    }
    public void isConnected(){

        nouveauMailButton.setEnabled(true);
        connexionButton.setEnabled(false);
    }

    private void telechargerPieceJointe(String nomPieceJointe) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choisissez un emplacement pour enregistrer la copie");
        fileChooser.setSelectedFile(new File(nomPieceJointe)); // Suggère le nom de la pièce jointe

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File destination = fileChooser.getSelectedFile();
            copierFichier(nomPieceJointe, destination);
        }
    }
    private void copierFichier(String sourcePath, File destination) {
        try {
            File source = new File(sourcePath);

            // Vérifier si le fichier source existe
            if (!source.exists()) {
                JOptionPane.showMessageDialog(this, "Le fichier source n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Copier le fichier
            Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(this, "Fichier copié avec succès: " + destination.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la copie du fichier: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void viderDossierPiecesJointes() {
        File dossier = new File("../Mail_User_Agent/PiecesJointes");
        if (dossier.exists() && dossier.isDirectory()) {
            File[] fichiers = dossier.listFiles();
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    fichier.delete(); // Supprime le fichier
                }
                System.out.println("SUPPPRIMEEEEE");
            }
        }
    }

}

