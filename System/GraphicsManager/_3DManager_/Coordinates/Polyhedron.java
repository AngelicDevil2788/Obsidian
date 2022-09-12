package obsidian.System.GraphicsManager._3DManager_.Coordinates;

import obsidian.System.WindowManager.Window;

public class Polyhedron {
    private Polygon[] polygons;

    public Polygon[] getPolygons() {
        return polygons;
    }

    public void setPolygons(Polygon[] polygons) {
        this.polygons = polygons;
    }

    public Polyhedron(Polygon... polygons) {
        this.polygons = polygons;
    }

    public void translate(double x, double y, double z) {
        for (Polygon p : polygons) {
            p.translate(x, y, z);
        }
    }
    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees, Window w, Point3D center) {
        for (Polygon p : polygons) {
            p.rotate(CW, xDegrees, yDegrees, zDegrees, center);
        }
        this.sortPolygons(w);
    }

    public void sortPolygons(Window w) {
        Polygon.sortPolygons(this.polygons, w);
    }

}
