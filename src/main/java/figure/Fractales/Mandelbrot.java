package figure.Fractales;
import figure.Modele;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Mandelbrot implements Fractale {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private Modele m;

    public Mandelbrot() {
        minX = -2.0;
        maxX = 1.0;
        minY = -1.2;
        maxY = 1.2;
    }

    public Pane build(Pane p) {
        m = Modele.getInstance();
        m.setTaille(10);
        int width = 1100;
        int height = 900;
        Canvas canvas = new Canvas(width, height);
        int maxIterations = m.getPrecision();
        canvas.setOnMouseClicked(event -> {

            double base = 1000.0;
            double input = m.getTaille();
            double zoomFactor;
            double valeurSecurisee = Math.max(base, m.getTaille());
            if (input >= 0) {
                zoomFactor = base / valeurSecurisee;
            } else {
                zoomFactor = (base + Math.abs(input)) / base;
            }

            double rangeX = maxX - minX;
            double rangeY = maxY - minY;

            double newRangeX = rangeX * zoomFactor;
            double newRangeY = rangeY * zoomFactor;

            double centreX = minX + (event.getX() / canvas.getWidth()) * rangeX;
            double centreY = minY + (event.getY() / canvas.getHeight()) * rangeY;

            minX = centreX - (newRangeX / 2.0);
            maxX = centreX + (newRangeX / 2.0);
            minY = centreY - (newRangeY / 2.0);
            maxY = centreY + (newRangeY / 2.0);

            //System.out.println("minX: " + minX + " maxX: " + maxX +  " minY: " + minY + " maxY: " + maxY + " zoomFactor: " + zoomFactor);
            m.build();
        });
        GraphicsContext gc = canvas.getGraphicsContext2D();
        PixelWriter pw = gc.getPixelWriter();

        for (int pixelX = 0; pixelX < width; pixelX++) {
            for (int pixelY = 0; pixelY < height; pixelY++) {

                double cr = minX + (pixelX * (maxX - minX) / width);
                double ci = minY + (pixelY * (maxY - minY) / height);

                // Algorithme de Mandelbrot
                double zr = 0;
                double zi = 0;
                int m = 0;

                // La boucle while (critère de divergence : module carré > 4)
                while (m < maxIterations && (zr * zr + zi * zi) <= 4) {
                    double tempZr = zr * zr - zi * zi + cr;
                    zi = 2 * zr * zi + ci;
                    zr = tempZr;
                    m++;
                }

                // 4. Choix de la couleur et dessin
                if (m == maxIterations) {
                    pw.setColor(pixelX, pixelY, Color.BLACK);
                } else {
                    double ratio = (double) m / maxIterations;
                    double teinte = 270.0 * (1.0 - ratio);
                    double luminosite = 0.2 + 0.8 * Math.pow(ratio, 0.7);
                    double saturation = 1.0;
                    pw.setColor(pixelX, pixelY, Color.hsb(teinte, saturation, luminosite));
                }
            }
        }
        p.getChildren().add(canvas);
        return p;
    }

}
