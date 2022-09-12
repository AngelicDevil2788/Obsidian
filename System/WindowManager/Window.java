package obsidian.System.WindowManager;

import obsidian.System.GraphicsManager.Sources.Texture;
import obsidian.System.GraphicsManager._3DManager_.Coordinates.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.function.Consumer;

import static java.lang.Math.abs;

public class  Window {
    private JFrame frame = new JFrame();
    private JPanel canvas = new JPanel();
    private ArrayList<Component> GUI_objects;
    private ArrayList<Consumer<Window>> objectFunctions;
    private ActionInput actionInput = new ActionInput(this);
    private GraphicsConfiguration gc;
    private VolatileImage vImg;
    private Graphics2D g;
    private Color bg_color = new Color(0, 0, 0);
    private boolean is_open = false;
    private String title = "";
    private final Input keyInput = new Input();

    private long lastFPScheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;

    private int canvasWidth;
    private int canvasHeight;
    private Dimension resolution;

    private Camera camera = new Camera(0, 0, 15);
    public Camera getCamera() {
        return camera;
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    private boolean[] pixel_occupied = new boolean[canvas.getWidth() * canvas.getHeight()];
    private boolean debugging = false;

    public boolean is_open() {
        return is_open;
    }

    public boolean debugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }

    public void setBackgroundColor(Color color) {
        bg_color = color;
    }

    public Dimension getScreenSize() {
        return new Dimension(canvas.getWidth() / resolution.width, canvas.getHeight() / resolution.height);
    }
    public String getName() {   return title;    };

    public Input getInput() {
        return keyInput;
    }
    public ActionInput getActionInput() { return actionInput; };
    public int getCurrentFPS() { return currentFPS; };

    public int Render() {
        totalFrames++;
        if (System.nanoTime() > lastFPScheck + 1000000000) {
            lastFPScheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }
        pixel_occupied = new boolean[canvas.getWidth() * canvas.getHeight()];
        if (vImg.validate(gc) == VolatileImage.IMAGE_OK) {
            vImg = gc.createCompatibleVolatileImage(canvas.getWidth() / resolution.width, canvas.getHeight() / resolution.height);
        }
        if (debugging()) {
            frame.setTitle(title + "[FPS: " + currentFPS + "]");
        }
        g = (Graphics2D)vImg.getGraphics();

        g.setColor(bg_color);
        g.fillRect(0, 0, canvas.getWidth() / resolution.width, canvas.getHeight() / resolution.height);
        return currentFPS;
    }

    public void UpdateBuffer() {
        g.dispose();

        g = (Graphics2D)canvas.getGraphics();
        g.drawImage(vImg, 0, 0, canvasWidth, canvasHeight, null);
        g.dispose();
    }

    public void repaintBuffer() {
        canvas.getGraphics().drawImage(vImg, 0, 0, canvasWidth, canvasHeight, null);
    }

    public void init(int width,
                     int height,
                     Dimension resolution,
                     String title,
                     Color bg_color,
                     Consumer<Void> onDestroy,
                     ArrayList<Component> objects,
                     ArrayList<Consumer<Window>> objectFunctions) {
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.resolution = resolution;
        this.is_open = true;
        this.title = title;
        this.bg_color = bg_color;
        this.GUI_objects = objects;
        this.objectFunctions = objectFunctions;
        this.canvas.setPreferredSize(new Dimension(this.canvasWidth - 1, this.canvasHeight - 1));
        this.canvas.setBounds(0, 0, this.canvasWidth - 1, this.canvasHeight - 1);
        this.canvas.setLayout(null);
        this.pixel_occupied = new boolean[canvas.getWidth() * canvas.getHeight()];

        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onDestroy.accept(null);
            }
        });
        this.frame.setLayout(null);
        this.frame.setSize(canvasWidth + 12, canvasHeight + 38);
        this.frame.addKeyListener(keyInput);
        if (objects != null) {
            for (int i = 0; i < objects.size(); i++) {
                Component object = this.GUI_objects.get(i);
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                panel.setBounds(object.getX(), object.getY(), object.getPreferredSize().width, object.getPreferredSize().height);
                panel.setOpaque(false);
                panel.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.f));
                panel.add(object);
                panel.setVisible(true);
                this.actionInput.objects.add(object);

                this.frame.add(panel);
            }
        }

        this.frame.add(canvas);
        this.frame.setResizable(false);
        this.frame.setLocation(-7, 0);
        this.frame.setTitle(title);
        this.frame.setVisible(true);

        gc = canvas.getGraphicsConfiguration();
        vImg = gc.createCompatibleVolatileImage(canvas.getWidth() / resolution.width, canvas.getHeight() / resolution.height);
    }

    public void renderShape(Shape shape, Color color) {
        g.setColor(color);
        g.draw(shape);
    }

    public void renderFullShape(Shape shape, Color color) {
        g.setColor(color);
        g.fill(shape);
    }

    public void renderOval(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.drawOval(x, y, w, h);
    }

    public void renderFullOval(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }

    public void renderCircle(int x, int y, int r, Color color) {
        
        g.setColor(color);
        g.drawOval(x, y, r * 2 + 1, r * 2 + 1);
    }

    public void renderFullCircle(int x, int y, int r, Color color) {
        g.setColor(color);
        g.fillOval(x, y, r * 2 + 1, r * 2 + 1);
    }

    public void renderTexture(int x, int y, Texture texture) {
        g.drawImage(texture.bits, x, y, null);
    }

    public void setPixelState(int x, int y, boolean occupied) {
        pixel_occupied[y * (canvas.getWidth() / resolution.width) + x] = occupied;
    }

    public boolean getPixelState(int x, int y) {
        return pixel_occupied[y * (canvas.getWidth() / resolution.width) + x];
    }

    public void renderText(String Text, Font font, Color color, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(Text, x, y);
    }

}
