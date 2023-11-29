package modele;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class SendSimple {

    private Session sess;
    private static String  host = "u2.tech.hepl.local";

    public SendSimple(Session s)
    {
        sess = s;
    }

    public void SendMailSimple(String de, String vers, String titre, String text){
        try
        {
            Properties prop = System.getProperties();
            prop.put("mail.smtp.host", host);
            sess = Session.getDefaultInstance(prop, null);
            System.out.println("Création du message");
            String exp = de;
            String dest = vers;
            String sujet = titre;
            String texte = text;
            MimeMessage msg = new MimeMessage (sess);
            msg.setFrom (new InternetAddress (exp));
            msg.setRecipient (Message.RecipientType.TO, new InternetAddress (dest));
            msg.setSubject(sujet);
            msg.setText (texte);

            System.out.println("Envoi du message");
            Transport.send(msg);
            System.out.println("Message envoyé");
        }
        catch (MessagingException e)
        {
            System.out.println("Errreur sur message : " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Errreur sur message : " + e.getMessage());
        }

    }


}
