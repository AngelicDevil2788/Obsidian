package obsidian.System.GraphicsManager._3DManager_.Coordinates;

import obsidian.System.OBISystem;
import obsidian.System.WindowManager.Window;

import java.awt.*;

public class PointConverter {
    private static double scale = 1;
    public static Point convertPoint(Point3D point3d, Window referenceWindow) {
        double x3d = point3d.x * scale;
        double y3d = point3d.y * scale;
        double z3d = (point3d.z - referenceWindow.getCamera().getPos().z) * scale;
        double[] newVal = scale(x3d, y3d, z3d, referenceWindow);
        int x2d = (int)(referenceWindow.getScreenSize().width / 2 + newVal[0]);
        int y2d = (int)(referenceWindow.getScreenSize().height / 2 + newVal[1]);

        return new Point(x2d, y2d);
    }

    public static double[] scale(double x3d, double y3d, double z3d, Window w) {
        double distX = x3d - w.getCamera().getPos().x;
        double distY = y3d - w.getCamera().getPos().y;
        double dist = Math.sqrt(distX*distX + distY*distY);
        double theta = Math.atan2(distY, distX);
        double distZ = z3d;
        double localScale = Math.abs((1400/(w.getCamera().getPos().z - distZ)));
        dist *= localScale;
        double[] newVal = new double[2];
        newVal[0] = w.getCamera().getPos().x + dist * Math.cos(theta);
        newVal[1] = w.getCamera().getPos().y + dist * Math.sin(theta);

        return newVal;
    }

    public static void rotateAxisX(Point3D p, boolean CW, double degrees, Point3D center) {
        double distZ = p.z - center.z;
        double distY = p.y - center.y;
        double theta = Math.atan2(distZ, distY);

        double radius = Math.sqrt(distZ * distZ + distY * distY);
        theta += Math.toRadians(degrees)*(CW?1:-1);
        double x = p.x;
        double y = center.y + (radius * Math.cos(theta));
        double z = center.z + (radius * Math.sin(theta));
        p.x = x;
        p.y = y;
        p.z = z;
    }
    public static void rotateAxisY(Point3D p, boolean CW, double degrees, Point3D center) {
        double distZ = p.z - center.z;
        double distX = p.x - center.x;
        double theta = Math.atan2(distX, distZ);

        double radius = Math.sqrt(distZ * distZ + distX * distX);
        theta += Math.toRadians(degrees)*(CW?1:-1);
        double x = center.x + (radius * Math.sin(theta));
        double y = p.y;
        double z = center.z + (radius * Math.cos(theta));
        p.x = x;
        p.y = y;
        p.z = z;

    }
    public static void rotateAxisZ(Point3D p, boolean CW, double degrees, Point3D center) {



    }
}
