package modele;

import java.util.*;
import javax.mail.*;
import javax.activation.*;
public class GestionConnexion {

    private static GestionConnexion instance = null;
    private Session session;
    private static String  host = "u2.tech.hepl.local";
    private String user;
    private String password;

    private Folder folder;

    private Store st;

    public GestionConnexion()
    {
        Properties prop = System.getProperties();
        prop.put("mail.pop3.host", host);
        prop.put("mail.disable.top", true);
        System.out.println("Création d'une session mail");
        session = Session.getDefaultInstance(prop, null);

    }

    //instance
    public static GestionConnexion getInstance()
    {
        if(instance == null)
            instance = new GestionConnexion();

        return instance;
    }

    //connexion au store
    public Store getSt()
    {
        return st;
    }

    public boolean connexion() throws MessagingException {
        if(session != null)
        {
            System.out.println("Obtention d'un objet store");
            st = session.getStore("pop3");
            st.connect(host, user, password);
            System.out.println("Obtention d'un objet folder");
            folder = st.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            return true;
        }
        else
            return false;
    }

    //getters and setter
    //session

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    //host
    public static String getHost() {
        return host;
    }
    // user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    //password


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
