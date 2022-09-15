package obsidian.System.GraphicsManager._3DManager_.Coordinates;

import obsidian.System.WindowManager.Window;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon {
    private Point3D[] points;
    private Color c;

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    private boolean full = false;

    public Point3D[] getPoints() {return points;};

    public Polygon(Color c, boolean full, Point3D... points) {
        this.points = new Point3D[points.length];
        this.c = c;
        this.full = full;
        for (int i = 0; i < points.length; i++) {
            Point3D p = points[i];
            this.points[i] = new Point3D(p.x, p.y, p.z);
        }
    }

    public Polygon(Color c, Point3D... points) {
        this.points = new Point3D[points.length];
        this.c = c;
        for (int i = 0; i < points.length; i++) {
            Point3D p = points[i];
            this.points[i] = new Point3D(p.x, p.y, p.z);
        }
    }

    public Color getColor() {
        return c;
    }

    public void setColor(Color c) {
        this.c = c;
    }

    public void translate(double x, double y, double z) {
        for (Point3D p : points) {
            p.x += x;
            p.y += y;
            p.z += z;
        }
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Point3D center) {
        for (Point3D p : points) {
            PointConverter.rotateAxisX(p, CW, xDegrees, new Point3D(center.x, center.y, center.z));
            PointConverter.rotateAxisY(p, CW, yDegrees, new Point3D(center.x, center.y, center.z));
            PointConverter.rotateAxisZ(p, CW, zDegrees, new Point3D(center.x, center.y, center.z));
        }
    }

    public double getAverageX() {
        double sum = 0;
        for (Point3D p : points) {
            sum += p.x;
        }
        return sum / this.points.length;
    }

    public double getAverageY() {
        double sum = 0;
        for (Point3D p : points) {
            sum += p.y;
        }
        return sum / this.points.length;
    }

    public double getAverageZ() {
        double sum = 0;
        for (Point3D p : points) {
            sum += p.z;
        }
        return sum / this.points.length;
    }

    public static Polygon[] sortPolygons(Polygon[] polygons, Window refferenceWindow) {
        List<Polygon> polygonsList = new ArrayList<Polygon>(Arrays.asList(polygons));

        Collections.sort(polygonsList, new Comparator<Polygon>() {

            @Override
            public int compare(Polygon o1, Polygon o2) {
                double diffX1 = refferenceWindow.getCamera().getPos().x - o1.getAverageX();
                double diffY1 = refferenceWindow.getCamera().getPos().y - o1.getAverageY();
                double diffZ1 = refferenceWindow.getCamera().getPos().z - o1.getAverageZ();

                double diffX2 = refferenceWindow.getCamera().getPos().x - o2.getAverageX();
                double diffY2 = refferenceWindow.getCamera().getPos().y - o2.getAverageY();
                double diffZ2 = refferenceWindow.getCamera().getPos().z - o2.getAverageZ();

                double diff1 = Math.sqrt(diffX1*diffX1 + diffZ1*diffZ1);
                double diff2 = Math.sqrt(diffX2*diffX2 + diffZ2*diffZ2);

                double dist1 = Math.sqrt(diff1*diff1 + diffY1*diffY1);
                double dist2 = Math.sqrt(diff2*diff2 + diffY2*diffY2);

                if (Double.compare(dist2, dist1) == 0) {
                    return o2.getAverageZ() - o1.getAverageZ() == 0 ? -1 : 1;
                } else {
                    return Double.compare(dist2, dist1);
                }
            }
        });
        Polygon[] newPolygons = new Polygon[polygons.length];
        for (int i = 0; i < newPolygons.length; i++) {
            polygons[i] = polygonsList.get(i);
        }
        return polygons;
    }

    public Polygon(Point3D... points) {
        this.points = new Point3D[points.length];
        this.c = new Color(255, 255, 255);
        for (int i = 0; i < points.length; i++) {
            Point3D p = points[i];
            this.points[i] = new Point3D(p.x, p.y, p.z);
        }
    }
}
