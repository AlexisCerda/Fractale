package figure;

import figure.Controler.Vue;
import figure.Fractales.Fractale;
import figure.Fractales.Menger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Modele extends Application {

    private static Modele singleton;
    private Fractale fractale;
    private int Taille;
    private int Iteration = 1;
    private Pane root = new Pane();
    private final Label nbr_ite = new Label(Iteration+"");
    private int angle;

    private Stage stage;
    private Vue vue;

    private Modele () {
        this.fractale = new Menger();
        this.Taille = 1000;
        this.angle = 60;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader MainMenu = new FXMLLoader();
        MainMenu.setLocation(getClass().getResource("MainMenu.fxml"));
        Pane nouveau = MainMenu.load();
        vue =  MainMenu.getController();
        this.stage = stage;
        stage.setTitle("Fractal Land");
        stage.setScene(new Scene(nouveau, 1200, 900));
        stage.show();
        this.root = this.fractale.build(this.root);
        vue.setRoot(this.root);

    }

    public synchronized static Modele getInstance(){
        if (singleton == null){
            singleton = new Modele();
        }
        return singleton;
    }
    public Fractale getFractale() {
        return fractale;
    }
    public void setFractale(Fractale fractale) {
        this.fractale = fractale;
    }
    public void setTaille(int taille) {
        Taille = taille*100;
        vue.setNbr_taille(taille);
    }
    public int getTaille() {
        return vue.getNbr_taille()*100;
    }
    public void setIteration(int iteration) {
        Iteration = iteration;
        vue.setNbr_iteration(iteration);
    }
    public int getIteration() {
        return Iteration;
    }
    public Pane getRoot() {
        return root;
    }
    public  Label getNbr_ite() {
        return nbr_ite;
    }
    public  TextField getNbr_taille() {
        if (vue != null) {
            return vue.getNbr_taillefull();
        }
        return null;
    }
    public Stage getStage() {
        return stage;
    }
    public void setNbr_Entite(int nbr_entite) {
        vue.setNbr_Entite(nbr_entite);
    }
    public void setAngle(int angle) {
        this.angle = angle;
        vue.tfAngle.setText(angle+"");
    }
    public int getAngle() {
        return angle;
    }
}
