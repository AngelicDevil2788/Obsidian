package obsidian.System.GraphicsManager._3DManager_.Coordinates;

public class Camera {
    private Point3D pos = new Point3D(0, 0, 15);
    private Point3D directionAngles = new Point3D(0, 0, 0);

    public Camera(double x, double y, double z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void translate(double x, double y, double z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public Point3D getPos() {
        return pos;
    }
    public Point3D getDirectionAngle() {
        return directionAngles;
    }

    public void setPos(Point3D pos) {this.pos = pos;};
    public void setDirectionAngle(Point3D direction) {this.directionAngles = direction;};

}
