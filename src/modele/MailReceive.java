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

    private  Folder folder;


    public MailReceive(Session se, Store s, Folder f)
    {
        sess = se;
        st = s;
        folder = f;
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

            System.out.println("Obtention des messages");
            Message msg[] = folder.getMessages();
            System.out.println("Nombre de messages : " + folder.getMessageCount());
            System.out.println("Nombre de nouveaux messages : " + folder.getNewMessageCount());

            System.out.println("Liste des messages : ");
            int j = 0;

            /*for (int i=0; i<msg.length; i++)
            {
                System.out.println("\n<nHeaders du message n°" + (i+1));
                Enumeration e = msg[i].getAllHeaders();
                Header h = (Header)e.nextElement();
                while (e.hasMoreElements())
                {
                    System.out.println(h.getName() + " -- >" + h.getValue());
                    h = (Header)e.nextElement();
                }
                System.out.println("Texte : " + (String)msg[i].getContent());
            }*/

            for (Message mail : msg)
            {
                Mail nouvMail = new Mail();
                System.out.println("Message n° " + j);
                if(mail == null)
                    System.out.println("ALEDDD C VIDEEEE");
                System.out.println("Expéditeur : " + mail.getFrom()[0]);
                nouvMail.setExpediteur(String.valueOf(mail.getFrom() [0]));
                nouvMail.setDestinataire(mail.getRecipients(Message.RecipientType.TO).getClass().getName());
                System.out.println("Sujet = " + mail.getSubject());
                nouvMail.setSujet(mail.getSubject());
                //System.out.println("Date : " + msg[i].getSentDate());

                // récupération du msg +  des éventuelles pièce jointe
                if(mail.isMimeType("text/plain")) // si que texte
                {
                    nouvMail.setTexte((String) mail.getContent());
                }
                if(mail.isMimeType("multipart/*")) // si pièce jointe ou  texte + pièce jointe
                {
                    Multipart multipart = (Multipart) mail.getContent();




                    for(j = 0; j < multipart.getCount(); j++)
                    {
                        BodyPart bodyPart = multipart.getBodyPart(j);

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
                j++;
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
