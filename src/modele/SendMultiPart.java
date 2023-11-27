package modele;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.*;
import java.util.Properties;
public class SendMultiPart {

    private Session session;

    public  SendMultiPart(Session sess)
    {
        session = sess;
    }

    public void SendMailMultiPart(String de, String vers, String titre, String text, ArrayList<File> listFichier) throws MessagingException {
        try
        {
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

            // 2ème composante : le fichier Word
            System.out.println("2ème composante");
            String nf = "d:\\notes-java-2001\\BienvenueAInpres.doc";
            msgBP = new MimeBodyPart();
            DataSource so = new FileDataSource (nf);
            msgBP.setDataHandler (new DataHandler (so));
            msgBP.setFileName(nf);
            msgMP.addBodyPart(msgBP);
            // 3ème composante : l'image
            System.out.println("3ème composante");
            nf = "d:\\notes-java-2001\\logo-INPRES.bmp";
            msgBP = new MimeBodyPart();
            so = new FileDataSource (nf);
            msgBP.setDataHandler (new DataHandler (so));
            msgBP.setFileName(nf);
            msgMP.addBodyPart(msgBP);
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
}
