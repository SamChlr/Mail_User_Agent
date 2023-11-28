package modele;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class MailReceive {
    private Session sess;
    private Store st;


    public MailReceive(Session se, Store s)
    {
        sess = se;
        st = s;
    }

    //getters and setters
    public Session getSess() {
        return sess;
    }

    public void setSess(Session sess) {
        this.sess = sess;
    }

    public Store getSt() {
        return st;
    }

    public void setSt(Store st) {
        this.st = st;
    }

    public ArrayList<Mail> receiveMail()
    {
        ArrayList<Mail> listeMail = new ArrayList<>();


        try
        {

            System.out.println("Obtention d'un objet folder");
            Folder f = st.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            System.out.println("Obtention des messages");
            Message msg[] = f.getMessages();
            System.out.println("Nombre de messages : " + f.getMessageCount());
            System.out.println("Nombre de nouveaux messages : " + f.getNewMessageCount());

            System.out.println("Liste des messages : ");
            for (int i=0; i<msg.length; i++)
            {
                Mail nouvMail = new Mail();
                System.out.println("Message n° " + i);

                System.out.println("Expéditeur : " + msg[i].getFrom() [0]);
                nouvMail.setExpediteur(String.valueOf(msg[i].getFrom() [0]));
                nouvMail.setDestinataire(msg[i].getRecipients(Message.RecipientType.TO).getClass().getName());
                System.out.println("Sujet = " + msg[i].getSubject());
                nouvMail.setSujet(msg[i].getSubject());
                //System.out.println("Date : " + msg[i].getSentDate());

                // récupération du msg +  des éventuelles pièce jointe
                if(msg[i].isMimeType("text/plain")) // si que texte
                {
                    nouvMail.setTexte((String) msg[i].getContent());
                }
                if(msg[i].isMimeType("multipart/*")) // si pièce jointe ou  texte + pièce jointe
                {
                    Multipart multipart = (Multipart) msg[i].getContent();




                    for(i = 0; i < multipart.getCount(); i++)
                    {
                        BodyPart bodyPart = multipart.getBodyPart(i);

                        if(bodyPart.isMimeType("text/plain"))
                        {
                            nouvMail.setTexte(bodyPart.getContent().toString());
                        }
                        if(bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT) )
                        {
                            InputStream is = bodyPart.getInputStream();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            int c;
                            while ((c = is.read()) != -1) baos.write(c);
                            baos.flush();
                            String nf = bodyPart.getFileName();
                            FileOutputStream fos =new FileOutputStream(nf);
                            baos.writeTo(fos);
                            fos.close();

                            System.out.println("Pièce attachée " + nf + " récupérée");
                            nouvMail.ajouterPieceJointe(nf);

                        }

                    }


                }
                listeMail.add(nouvMail);
            }
            System.out.println("Fin des messages");
            return  listeMail;
        }
        catch (NoSuchProviderException e)
        {
            System.out.println("Errreur sur provider : " + e.getMessage());
        }
        catch (MessagingException e)
        {
            System.out.println("Errreur sur message : " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Errreur sur I/O : " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("Errreur indéterminée : " + e.getMessage());
        }
        return null;
    }


}
