package figure.Controler;

import figure.Fractales.*;
import figure.Modele;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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

    @FXML
    private void initialize() {
        Modele mo = Modele.getInstance();
        nbr_taille.setText("10");
        view_fractal.setStyle("-fx-background-color: #f0f0f0;");
        Nbr_iteration.setText(mo.getIteration()+"");
        tfAngle = new TextField();

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
        MenuItem triangle = new MenuItem("Triangle Sierpinski");
        MenuItem pytha = new MenuItem("Pythagoras tree");
        MenuItem dragon = new MenuItem("Dragon curve");
        MenuItem koch = new MenuItem("Koch snowflake");

        menger.setOnAction(e -> {
            this.setActionItemMenu(new Menger(),menger.getText());
        });

        triangle.setOnAction(e -> {
            this.setActionItemVoid(triangle.getText());
        });

        pytha.setOnAction(e -> {
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
            this.setActionItemMenu(new Dragon(),dragon.getText());
        });

        koch.setOnAction(e -> {
            mo.setAngle(60);
            this.setActionItemMenu(new Flocon(),koch.getText());
            if (!PaneButton.getChildren().contains(tfAngle)) {
                PaneButton.getChildren().add(tfAngle);
            }
            if (!PaneButton.getChildren().contains(btnAngle)) {
                PaneButton.getChildren().add(btnAngle);
            }
        });
        Menu_fractale.getItems().addAll( menger, koch,dragon,triangle, pytha);
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
        modele.setTaille(nbr2);

        modele.getRoot().getChildren().clear();

        modele.getFractale().build(modele.getRoot());
    }

    public void setNbr_taille(int Nbr) {
        nbr_taille.setText(Nbr+"");
    }

    public void addRoot(Node node) {
        view_fractal.getChildren().add(node);
    }

    public void setRoot(Node node) {
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

    public void setMenu_fractale(MenuButton menu_fractale) {
        Menu_fractale = menu_fractale;
    }

    public void setActionItemMenu(Fractale fractale, String nom) {
        Modele m = Modele.getInstance();
        m.getRoot().getChildren().clear();
        m.setIteration(1);
        m.setTaille(10);
        m.setFractale(fractale);
        m.getFractale().build(m.getRoot());
        Menu_fractale.setText(nom);
        if (PaneButton.getChildren().contains(tfAngle)) {
            PaneButton.getChildren().remove(tfAngle);
        }
        if (PaneButton.getChildren().contains(btnAngle)) {
            PaneButton.getChildren().remove(btnAngle);
        }
    }

    public void setActionItemVoid(String nom) {
        Modele m = Modele.getInstance();
        m.getRoot().getChildren().clear();
        m.getRoot().getChildren().add(new Label("Indisponible pour le moment"));
        Menu_fractale.setText(nom);
    }

    public void setNbr_Entite(int Nbr) {
        Nbr_Entite.setText("Nombre d'entités : "+Nbr);
    }
}