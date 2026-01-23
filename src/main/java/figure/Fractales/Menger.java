package figure.Fractales;

import figure.Modele;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Menger implements Fractale {
    public int compteur = 0;

    public Pane square(Pane p, int max, int profondeur, double x, double y){
        Modele m = Modele.getInstance();
        double taille = (m.getTaille() /(Math.pow(3,profondeur)));
        Scene scene = m.getStage().getScene();
        Rectangle rect = Create_Square(taille, profondeur);
        rect.setX(x);
        rect.setY(y);
        if (x < scene.getWidth()&&y < scene.getHeight()) {
            p.getChildren().add(rect);
            compteur++;
        }
        if (profondeur >= max){
            return p;
        }
        else if (profondeur == 1){
            return square(p, max, profondeur+1,x+taille/3,y+taille/3);
        }
        else{
            double tmp = taille;
            taille = taille/3;
            x -= taille*3;
            y -= taille*3;

            if (taille > scene.getWidth() && taille > scene.getHeight()) {
                return square(p, max, profondeur+1,x+ taille,y+ taille );
            }
            else {
                square(p, max, profondeur + 1, x + taille, y + taille);
                square(p, max, profondeur + 1, x + taille + tmp, y + taille);
                square(p, max, profondeur + 1, x + taille + (tmp * 2), y + taille);

                square(p, max, profondeur + 1, x + taille, y + taille + tmp);
                square(p, max, profondeur + 1, x + taille + (tmp * 2), y + taille + tmp);

                square(p, max, profondeur + 1, x + taille, y + taille + (tmp * 2));
                square(p, max, profondeur + 1, x + taille + tmp, y + taille + (tmp * 2));
                square(p, max, profondeur + 1, x + taille + (tmp * 2), y + taille + (tmp * 2));
                return p;
            }

        }
    }

    public Rectangle Create_Square(double taille,int iteration){
        Rectangle rect = new Rectangle(taille, taille);
        //rect.setStroke(Color.BLACK);
        if (iteration == 1){
            rect.setFill(Color.BLACK);
            return rect;
        }
        double val = Math.max(0, Math.min(17, iteration));
        double ratio = (val) / (double)(17);
        double hue = (1.0 - ratio) * 120;
        //Color color = Color.hsb(hue, 1.0, 1.0);
        Color color = Color.WHITE;
        rect.setFill(color);
        return rect;
    }

    public void ruler(Pane p, double taille) {
        Line ln = new Line();
        ln.setStartX(40);
        ln.setStartY(100);
        ln.setEndX(40);
        ln.setEndY(100 + taille);
        p.getChildren().add(ln);
        double fontSize = 12.5;

        int pas = 10;
        for (int i = 0; i <= 100; i += pas) {
            setNumberRuler(p, taille, fontSize, i);
        }
        pas = 1;
        if (taille <= 1000){
            return;
        }
        for (int i = 0; i <= 10; i += pas) {
            if (!(i % 10 == 0)) {
                setNumberRuler(p, taille, fontSize, i);
            }
        }
        if (taille <= 10000){
            return;
        }
        for (int i = 0; i < 100; i+= pas) {
            if (!(i/10.0 % 1 == 0)) {
                setNumberRuler(p, taille, fontSize, i/10.0);
            }
        }
        if (taille <= 100000){
            return;
        }
        for (int i = 0; i < 100; i+= pas) {
            if (!(i/100.0 % 0.1 == 0)) {
                setNumberRuler(p, taille, fontSize, i/100.0);
            }
        }
        if (taille <= 1000000){
            return;
        }
        for (int i = 0; i < 100; i+= pas) {
            if (!(i/1000.0 % 0.01 == 0)) {
                setNumberRuler(p, taille, fontSize, i/1000.0);
            }
        }
        if (taille <= 10000000){
            return;
        }
        for (int i = 0; i < 100; i+= pas) {
            if (!(i/10000.0 % 0.001 == 0)) {
                setNumberRuler(p, taille, fontSize, i/10000.0);
            }
        }

    }

    private void setNumberRuler(Pane p, double taille, double fontSize, int i) {
        Text nbr = new Text(i + "%");
        nbr.setLayoutX(45);
        double posY = 100 + (taille * (i / 100.0)) + (fontSize / 3);
        nbr.setLayoutY(posY);
        nbr.setFont(new Font("Arial", fontSize));
        Line tick = new Line(35, 100 + (taille * (i / 100.0)), 40, 100 + (taille * (i / 100.0)));
        p.getChildren().add(tick);
        p.getChildren().add(nbr);
    }
    private void setNumberRuler(Pane p, double taille, double fontSize, double i) {
        Text nbr = new Text(i + "%");
        nbr.setLayoutX(45);
        double posY = 100 + (taille * (i / 100.0)) + (fontSize / 3);
        nbr.setLayoutY(posY);
        nbr.setFont(new Font("Arial", fontSize));
        Line tick = new Line(35, 100 + (taille * (i / 100.0)), 40, 100 + (taille * (i / 100.0)));
        p.getChildren().add(tick);
        p.getChildren().add(nbr);
    }

    @Override
    public Pane build(Pane p) {
        compteur = 0;
        Modele m = Modele.getInstance();
        square(p, m.getIteration(), 1, 100, 100);
        ruler(p,(m.getTaille() / 3.0));
        m.setNbr_Entite(compteur);
        return p;
    }
}

