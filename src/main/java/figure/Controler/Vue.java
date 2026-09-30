package figure.Controler;

import figure.Fractales.*;
import figure.Modele;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Vue {
    @FXML
    public AnchorPane PaneButton;
    @FXML
    public Button plus;
    @FXML
    public Button moins;
    @FXML
    public Label Nbr_iteration;
    @FXML
    public MenuButton Menu_fractale;
    @FXML
    public Label Nbr_Entite;
    @FXML
    public AnchorPane view_fractal;
    @FXML
    public TextField nbr_taille;
    @FXML
    public Button actualiser;

    public TextField tfAngle;
    public Button btnAngle;
    public TextField tfAccurate;
    public Label Accurate;
    public boolean isMathFunction;

    @FXML
    public Button btnExit;


    @FXML
    private void initialize() {
        isMathFunction = false;
        Modele mo = Modele.getInstance();
        nbr_taille.setText("10");
        view_fractal.setStyle("-fx-background-color: #f0f0f0;");
        Nbr_iteration.setText(mo.getIteration()+"");
        tfAngle = new TextField();
        tfAccurate = new TextField();
        Accurate = new Label("Précision du fractale");

        btnAngle = new Button();
        btnAngle.setText("Angle");
        btnAngle.setOnAction(e -> {
            String angle = tfAngle.getText();
            int a = 60;
            try {
                a = Integer.parseInt(angle);
            }catch(NumberFormatException _) {
            }
            mo.setAngle(a);
            ActionButtonActualiser(mo);
        });

        tfAngle.setText(mo.getAngle()+"");
        tfAngle.setMaxWidth(100);
        tfAngle.setLayoutX(54.0);
        tfAngle.setLayoutY(330.0);
        btnAngle.setLayoutX(154.0);
        btnAngle.setLayoutY(330.0);

        tfAccurate.setLayoutX(54.0);
        tfAccurate.setLayoutY(330.0);
        Accurate.setLayoutX(54.0);
        Accurate.setLayoutY(310.0);

        btnExit.setOnAction(e -> {
            Platform.exit();
        });

        moins.setOnAction(event -> {
            Modele modele = Modele.getInstance();
            int nbr;
            int Iteration = modele.getIteration();

            modele.setIteration(Iteration-1);
            nbr = Iteration - 1;

            if (nbr < 1 ){
                nbr = 1;
                modele.setIteration(nbr);
            }

            modele.getNbr_ite().setText(String.valueOf(nbr));
            ActionButtonActualiser(modele);
        });

        plus.setOnAction(event -> {
            Modele modele = Modele.getInstance();
            int nbr;
            int Iteration = modele.getIteration();

            modele.setIteration(Iteration+1);
            nbr = Iteration + 1;

            if (nbr < 1 ){
                nbr = 1;
                modele.setIteration(nbr);
            }

            modele.getNbr_ite().setText(String.valueOf(nbr));
            ActionButtonActualiser(modele);
        });

        actualiser.setOnAction(event -> {
            ActionButtonActualiser(mo);
        });

        MenuItem menger = new MenuItem("Sierpiński carpet");
        MenuItem pytha = new MenuItem("Pythagoras tree");
        MenuItem dragon = new MenuItem("Dragon curve");
        MenuItem koch = new MenuItem("Koch snowflake");
        MenuItem mandelbrot = new MenuItem("Mandelbrot set");

        menger.setOnAction(e -> {
            isMathFunction = false;
            this.setActionItemMenu(new Menger(),menger.getText());
        });


        pytha.setOnAction(e -> {
            isMathFunction = false;
            mo.setAngle(45);
            this.setActionItemMenu(new Pytha(),pytha.getText());
            if (!PaneButton.getChildren().contains(tfAngle)) {
                PaneButton.getChildren().add(tfAngle);
            }
            if (!PaneButton.getChildren().contains(btnAngle)) {
                PaneButton.getChildren().add(btnAngle);
            }
        });

        dragon.setOnAction(e -> {
            isMathFunction = false;
            this.setActionItemMenu(new Dragon(),dragon.getText());
        });

        koch.setOnAction(e -> {
            isMathFunction = false;
            mo.setAngle(60);
            this.setActionItemMenu(new Flocon(),koch.getText());
            if (!PaneButton.getChildren().contains(tfAngle)) {
                PaneButton.getChildren().add(tfAngle);
            }
            if (!PaneButton.getChildren().contains(btnAngle)) {
                PaneButton.getChildren().add(btnAngle);
            }
        });
        mandelbrot.setOnAction(e -> {
            isMathFunction = true;
            mo.setPrecision(200);
            this.setActionItemMenu(new Mandelbrot(),mandelbrot.getText());
            if (!PaneButton.getChildren().contains(tfAccurate)) {
                PaneButton.getChildren().add(tfAccurate);
            }
            if (!PaneButton.getChildren().contains(Accurate)) {
                PaneButton.getChildren().add(Accurate);
            }
        });
        Menu_fractale.getItems().addAll( menger, koch,dragon, pytha, mandelbrot);
    }

    private void ActionButtonActualiser(Modele modele) {
        int nbr2;
        try {
            nbr2 = Integer.parseInt(modele.getNbr_taille().getText().trim());
            if (nbr2 < 1 ){
                nbr2 = 10;
            }
        }catch (NumberFormatException e) {
            nbr2 = 10;
        }
        if (!tfAccurate.getText().isEmpty()){
            modele.setPrecision(Integer.parseInt(tfAccurate.getText().trim()));
        }
        modele.setTaille(nbr2);

        modele.getRoot().getChildren().clear();

        modele.getFractale().build(modele.getRoot());
    }

    public void setNbr_taille(int Nbr) {
        nbr_taille.setText(Nbr+"");
    }

    public void setRoot(Node node) {
        view_fractal.getChildren().clear();
        view_fractal.getChildren().add(node);
    }

    public void setNbr_iteration(int iteration) {
        Nbr_iteration.setText(iteration+"");
    }

    public int getNbr_taille() {
        return Integer.parseInt(nbr_taille.getText());
    }

    public TextField getNbr_taillefull() {
        return nbr_taille;
    }
    public MenuButton getMenu_fractale() {
        return Menu_fractale;
    }

    public void setActionItemMenu(Fractale fractale, String nom) {
        Modele m = Modele.getInstance();

        m.getRoot().getChildren().clear();
        m.setIteration(1);
        m.setTaille(10);
        m.setFractale(fractale);
        m.getFractale().build(m.getRoot());
        Menu_fractale.setText(nom);

        if (isMathFunction){
            if (PaneButton.getChildren().contains(plus)) {
                PaneButton.getChildren().remove(plus);
            }
            if (PaneButton.getChildren().contains(moins)){
                PaneButton.getChildren().remove(moins);
            }
            if (PaneButton.getChildren().contains(Nbr_iteration)){
                PaneButton.getChildren().remove(Nbr_iteration);
            }
        }
        else {
            if (!PaneButton.getChildren().contains(plus)) {
                PaneButton.getChildren().add(plus);
            }
            if (!PaneButton.getChildren().contains(moins)){
                PaneButton.getChildren().add(moins);
            }
            if (!PaneButton.getChildren().contains(Nbr_iteration)){
                PaneButton.getChildren().add(Nbr_iteration);
            }
        }

        if (PaneButton.getChildren().contains(tfAngle)) {
            PaneButton.getChildren().remove(tfAngle);
        }
        if (PaneButton.getChildren().contains(btnAngle)) {
            PaneButton.getChildren().remove(btnAngle);
        }
        if (PaneButton.getChildren().contains(tfAccurate)) {
            PaneButton.getChildren().remove(tfAccurate);
        }
        if (PaneButton.getChildren().contains(Accurate)) {
            PaneButton.getChildren().remove(Accurate);
        }
    }

    public void setNbr_Entite(int Nbr) {
        Nbr_Entite.setText("Nombre d'entités : "+Nbr);
    }
}