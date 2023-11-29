import controleur.ControleurBoiteMail;
import vue.WindowMail;
public class Main {
    public static void main(String[] args)
    {
        WindowMail fenetre = new WindowMail();
        ControleurBoiteMail controleur = new ControleurBoiteMail (fenetre);
        fenetre.setControleur(controleur);
        fenetre.setVisible(true);
    }
}