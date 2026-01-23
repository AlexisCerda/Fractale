package figure.Fractales;

import figure.Modele;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;

public class Dragon implements Fractale{
    private ArrayList<Double> coor;
    private int compteur;

    public Dragon(){
        this.compteur = 0;
        this.coor = new ArrayList<>();
    }

    public void curve(Pane p, int iteration){
        Modele m = Modele.getInstance();
        double Taille = (10000.0/m.getTaille());
        if(iteration==1){
            coor.add(0.0);
            coor.add(0.0);
            coor.add(Taille);
            coor.add(0.0);
        }
        else {
            curve(p, iteration - 1);

            int indexDernierX = coor.size() - 2;
            int indexDernierY = coor.size() - 1;

            double px = coor.get(indexDernierX); // Pivot X
            double py = coor.get(indexDernierY); // Pivot Y

            for (int i = indexDernierX - 2; i >= 0; i -= 2) {
                double x = coor.get(i);
                double y = coor.get(i + 1);

                //Formule
                // xNew = px + (y - py)
                // yNew = py - (x - px)
                double xNew = px + (y - py);
                double yNew = py - (x - px);
                coor.add(xNew);
                coor.add(yNew);
            }
        }
    }

    @Override
    public Pane build(Pane p) {
        compteur = 0;
        Modele m = Modele.getInstance();
        curve(p, m.getIteration());
        Polyline pl = new Polyline();
        for(double i : coor){
            pl.getPoints().add(i);
            compteur++;
        }
        pl.setStrokeWidth(1.0);
        pl.setLayoutX( m.getStage().getWidth() /2);
        pl.setLayoutY( m.getStage().getHeight() /2);
        m.setNbr_Entite(compteur);
        p.getChildren().add(pl);
        coor.clear();
        return p;
    }


}
