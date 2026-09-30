package figure.Fractales;
import figure.Modele;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

public class Flocon implements Fractale {
    private ArrayList<Double> coor;
    private int compteur;

    public Flocon(){
        this.compteur = 0;
        this.coor = new ArrayList<>();
    }

    public void curve(Pane p, int iteration){
        Modele m = Modele.getInstance();

        int angleDegres = m.getAngle();
        double angleRad = Math.toRadians(angleDegres);
        double diviseur = 2 * (1 + Math.cos(angleRad));
        double largeurTotale = m.getTaille() / 2.0;
        double Taille = largeurTotale / Math.pow(diviseur, m.getIteration() - 1);

        if(iteration == 1){
            coor.add(0.0);
            coor.add(0.0);
            coor.add(Taille);
            coor.add(0.0);
        }
        else {
            curve(p, iteration - 1);
            int NbrElements = coor.size();
            ajouterCopieRotation(360 - angleDegres, NbrElements);
            ajouterCopieRotation(angleDegres, NbrElements);
            ajouterCopieRotation(0, NbrElements);
        }
    }

    @Override
    public Pane build(Pane p) {
        compteur = 0;
        coor.clear();
        Modele m = Modele.getInstance();
        int iteration = m.getIteration();
        curve(p, iteration);
        int Nbrpoints = coor.size();
        ajouterCopieRotation(120, Nbrpoints);
        ajouterCopieRotation(240, Nbrpoints);
        Polyline pl = new Polyline();
        for(double i : coor){
            pl.getPoints().add(i);
            compteur++;
        }
        pl.setStrokeWidth(0.5);
        pl.setLayoutX( m.getStage().getWidth() /4);
        pl.setLayoutY( m.getStage().getHeight() /4);
        m.setNbr_Entite(compteur);
        p.getChildren().add(pl);
        coor.clear();
        return p;
    }
    public void ajouterCopieRotation(double angleDegres, int nbrPoints) {
        double xStart = coor.get(0);
        double yStart = coor.get(1);

        int size = coor.size();
        double xPivot = coor.get(size - 2);
        double yPivot = coor.get(size - 1);

        double rad = Math.toRadians(angleDegres);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        for (int i = 0; i < nbrPoints; i += 2) {
            double x = coor.get(i);
            double y = coor.get(i + 1);

            double dx = x - xStart;
            double dy = y - yStart;

            //Formule de rotation vectorielle 2D
            double dxRot = dx * cos - dy * sin;
            double dyRot = dx * sin + dy * cos;

            //On ajoute ce vecteur tourné à la suite du pivot
            coor.add(xPivot + dxRot);
            coor.add(yPivot + dyRot);
        }
    }
}

