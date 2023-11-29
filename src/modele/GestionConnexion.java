package modele;

import java.util.*;
import javax.mail.*;
import javax.activation.*;
public class GestionConnexion {

    private static GestionConnexion instance;
    private Session session;
    private static String  host = "u2.tech.hepl.local";
    private String user;
    private String password;

    private Store st;

    public GestionConnexion()
    {
        Properties prop = System.getProperties();
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
            return true;
        }
        else
            return false;
    }

    //getters and setter
    //session
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
