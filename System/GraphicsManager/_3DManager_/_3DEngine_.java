package obsidian.System.GraphicsManager._3DManager_;

import obsidian.System.GraphicsManager._3DManager_.Coordinates.Point3D;
import obsidian.System.GraphicsManager._3DManager_.Coordinates.PointConverter;
import obsidian.System.GraphicsManager._3DManager_.Coordinates.Polygon;
import obsidian.System.GraphicsManager._3DManager_.Coordinates.Polyhedron;
import obsidian.System.WindowManager.Window;

import java.awt.*;
import java.util.ArrayList;

public class _3DEngine_ {
    public static void renderPolygon(Polygon p, Window w, Color color) {
        Polygon rotatedPolygon = new Polygon(p.getPoints());
        rotatedPolygon.rotate(true, w.getCamera().getDirectionAngle().x,w.getCamera().getDirectionAngle().y, 0, w.getCamera().getPos());
        java.awt.Polygon poly = new java.awt.Polygon();
        for (int i = 0; i < p.getPoints().length; i++) {
            Point p1 = PointConverter.convertPoint(p.getPoints()[i], w);
            poly.addPoint(p1.x, p1.y);
        }
        w.renderShape(poly, color);
    }
    public static void renderFullPolygon(Polygon p, Window w, Color color) {
        java.awt.Polygon poly = new java.awt.Polygon();
        for (int i = 0; i < p.getPoints().length; i++) {
            Point p1 = PointConverter.convertPoint(p.getPoints()[i], w);
            poly.addPoint(p1.x, p1.y);
        }
        w.renderFullShape(poly, color);
    }

    public static void renderPolyhedron(Polyhedron polyhedron, Window w, Color color) {
        for(Polygon p : polyhedron.getPolygons()) {
            java.awt.Polygon poly = new java.awt.Polygon();
            for (int i = 0; i < p.getPoints().length; i++) {
                Point p1 = PointConverter.convertPoint(p.getPoints()[i], w);
                poly.addPoint(p1.x, p1.y);
            }
            w.renderShape(poly, color);
        }
    }
    public static void renderFullPolyhedron(Polyhedron polyhedron, Window w, Color color) {
        for(Polygon p : polyhedron.getPolygons()) {
            java.awt.Polygon poly = new java.awt.Polygon();
            for (int i = 0; i < p.getPoints().length; i++) {
                Point p1 = PointConverter.convertPoint(p.getPoints()[i], w);
                poly.addPoint(p1.x, p1.y);
            }
            w.renderFullShape(poly, color);
        }
    }
}
