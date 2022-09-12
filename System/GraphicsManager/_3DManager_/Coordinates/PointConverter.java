package obsidian.System.GraphicsManager._3DManager_.Coordinates;

import obsidian.System.OBISystem;
import obsidian.System.WindowManager.Window;

import java.awt.*;

public class PointConverter {
    private static double scale = 1;
    private static double angleX = 0;
    private static double angleY = 0;
    public static Point convertPoint(Point3D point3d, Window referenceWindow) {
        double x3d = point3d.x * scale;
        double y3d = point3d.y * scale;
        double depth = (point3d.z - referenceWindow.getCamera().getPos().z) * scale;
        double[] newVal = scale(x3d, y3d, depth, referenceWindow);
        int x2d = (int)(referenceWindow.getScreenSize().width / 2 + (int)referenceWindow.getCamera().getPos().x + newVal[0]);
        int y2d = (int)(referenceWindow.getScreenSize().height / 2 + (int)referenceWindow.getCamera().getPos().y + newVal[1]);


        Point point2D = new Point(x2d, y2d);
        return point2D;
    }

    public static double[] scale(double x3d, double y3d, double depth, Window w) {
        double distX = Math.abs(x3d - w.getCamera().getPos().x);
        double distY = Math.abs(y3d - w.getCamera().getPos().y);
        double dist = Math.sqrt(x3d*x3d + y3d*y3d);
        double theta = Math.atan2(y3d, x3d);
        double depth2 = w.getCamera().getPos().z - depth;
        double localScale = 1400/(depth2 + 1400);
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = w.getCamera().getPos().x + dist * Math.cos(theta);
        newVal[1] = w.getCamera().getPos().y + dist * Math.sin(theta);
        return newVal;
    }

    public static void rotateAxisX(Point3D p, boolean CW, double degrees, Point3D center) {
        if (degrees > 360) {degrees -= 360;};
        double distZ = Math.abs(p.z - center.z);
        double distY = Math.abs(p.y - center.y);
        double theta = Math.atan2(distZ, distY);
        theta += Math.toRadians(degrees)*(CW?1:-1);
        p.z += Math.sin(theta);
        p.y += Math.cos(theta);
    }
    public static void rotateAxisY(Point3D p, boolean CW, double degrees, Point3D center) {
        if (degrees > 360) {degrees -= 360;};
        double radius = Math.sqrt(p.x * p.x + p.z * p.z);
        double theta = Math.atan2(p.x, p.z);
        theta += 2*Math.PI/360*degrees*(CW?-1:1);
        p.x = radius * Math.sin(theta);
        p.z = radius * Math.cos(theta);

    }
    public static void rotateAxisZ(Point3D p, boolean CW, double degrees, Point3D center) {
        if (degrees > 360) {degrees -= 360;};
        double radius = Math.sqrt(p.x * p.x + p.y * p.y);
        double theta = Math.atan2(p.y, p.x);
        theta += 2*Math.PI/360*degrees*(CW?1:-1);
        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);

    }
}
