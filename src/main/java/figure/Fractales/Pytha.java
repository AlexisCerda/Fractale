package figure.Fractales;
import figure.Modele;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Pytha implements Fractale {
    private int compteur;
    private Canvas canvas;

    public Pytha() {
        this.compteur = 0;
    }

    public void Tree(GraphicsContext gc, int iteration) {
        Modele m = Modele.getInstance();
        double taille = m.getTaille() / 8.0;

        double sceneWidth = m.getStage().getScene().getWidth();
        double sceneHeight = m.getStage().getScene().getHeight();

        double x1 = sceneWidth / 2.5 - taille;
        double y1 = sceneHeight - 50;
        double x2 = sceneWidth / 2.5;
        double y2 = sceneHeight - 50;

        // IMPORTANT : On efface tout avant de dessiner pour éviter les traces fantômes
        gc.clearRect(0, 0, sceneWidth,sceneHeight);
        dessinerArbre(gc, iteration, x1, y1, x2, y2);
    }

    private void dessinerArbre(GraphicsContext gc, int n, double x1, double y1, double x2, double y2) {
        if (n == 0) return;

        double dx = x2 - x1;
        double dy = y2 - y1;

        double v_x = dy;
        double v_y = -dx;

        double x4 = x1 + v_x;
        double y4 = y1 + v_y;
        double x3 = x2 + v_x;
        double y3 = y2 + v_y;

        double[] xPoints = {x1, x2, x3, x4};
        double[] yPoints = {y1, y2, y3, y4};

        double totalIter = Modele.getInstance().getIteration();
        double ratio = (double)n / totalIter;
        double hue = 20 + (100 * (1.0 - ratio));

        double sceneWidth = Modele.getInstance().getStage().getScene().getWidth();
        double sceneHeight = Modele.getInstance().getStage().getScene().getHeight();

        boolean estHorsEcran =
                (x1 < 0 && x2 < 0 && x3 < 0 && x4 < 0) ||
                        (x1 > sceneWidth && x2 > sceneWidth && x3 > sceneWidth && x4 > sceneWidth) ||
                        (y1 < 0 && y2 < 0 && y3 < 0 && y4 < 0) ||
                        (y1 > sceneHeight && y2 > sceneHeight && y3 > sceneHeight && y4 > sceneHeight);

        if (!estHorsEcran) {
            compteur++;
            gc.setFill(Color.hsb(hue, 1.0, 0.8));
            gc.fillPolygon(xPoints, yPoints, 4);
        }
        double L = Math.sqrt(dx*dx + dy*dy);
        double angleDeg = Modele.getInstance().getAngle();
        double alpha = Math.toRadians(angleDeg);
        double angleBase = Math.atan2(dy, dx);
        double angleGauche = angleBase - alpha;
        double tailleGauche = L * Math.cos(alpha);

        double x5 = x4 + tailleGauche * Math.cos(angleGauche);
        double y5 = y4 + tailleGauche * Math.sin(angleGauche);

        dessinerArbre(gc, n - 1, x4, y4, x5, y5);
        dessinerArbre(gc, n - 1, x5, y5, x3, y3);
    }

    @Override
    public Pane build(Pane p) {
        Modele m = Modele.getInstance();
        compteur = 0;
        double w = m.getStage().getScene().getWidth();
        double h = m.getStage().getScene().getHeight();

        canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Tree(gc, m.getIteration());

        m.setNbr_Entite(compteur);

        p.getChildren().add(canvas);
        return p;
    }
}


