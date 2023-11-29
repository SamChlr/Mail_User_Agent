package modele;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.Properties;

public class SendMultiPart {
    private Session session;
    private static String  host = "u2.tech.hepl.local";

    public  SendMultiPart(Session sess)
    {
        session = sess;
    }

    public void SendMailMultiPart(String de, String vers, String titre, String text, ArrayList<File> listFichier) throws MessagingException {
        try
        {
            Properties prop = System.getProperties();
            prop.put("mail.smtp.host", host);
            session = Session.getDefaultInstance(prop, null);
            System.out.println("Création du message");
            String exp = de;
            String dest = vers;
            String sujet = titre;
            String texteAcc = text;
            MimeMessage msg = new MimeMessage (session);
            msg.setFrom (new InternetAddress (exp));
            msg.setRecipient (Message.RecipientType.TO, new InternetAddress (dest));
            msg.setSubject(sujet);
            System.out.println("Début construction du multipart");
            Multipart msgMP = new MimeMultipart();

            // 1ère composante : le texte d'accompagnement
            System.out.println("1ère composante");
            MimeBodyPart msgBP = new MimeBodyPart();
            msgBP.setText(texteAcc);
            msgMP.addBodyPart(msgBP);

            //ajout des eventuelles pièces jointes
            for(File file : listFichier)
            {
                ajouterPieceJointe(msgMP, file);
            }

            //envoie du message
            msg.setContent(msgMP);
            System.out.println("Envoi du message");
            Transport.send(msg);
            System.out.println("Message envoyé");

        }
        catch (AddressException e)
        {
            System.out.println("Errreur sur message : " + e.getMessage());
        }
        catch (MessagingException ex)
        {
            System.out.println("Errreur sur message : " + ex.getMessage());
        }


    }

    private void ajouterPieceJointe(Multipart msg, File file) throws MessagingException {
        BodyPart pieceJointe = new MimeBodyPart();
        DataSource so = new FileDataSource (file.getPath());
        pieceJointe.setDataHandler (new DataHandler (so));
        pieceJointe.setFileName(file.getName());
        msg.addBodyPart(pieceJointe);

    }
}
