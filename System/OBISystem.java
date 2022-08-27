package obsidian.System;

import obsidian.System.GraphicsManager.Sources.Texture;
import obsidian.System.GraphicsManager.Sources.TextureSheet;
import obsidian.System.WindowManager.Window;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OBISystem {
    private static final ArrayList<Window> systemWindows = new ArrayList<>();
    private static boolean is_open = true;

    public static final int maxWidth = Toolkit.getDefaultToolkit().getScreenSize().width + 2;
    public static final int maxHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 72;

    public static boolean isOpen() { return is_open; }
    public static void close() {
        is_open = false;
        System.exit(0);
    }
    public static class chrono {
        public static class threads {
            private static final Map<String, Thread> threads = new HashMap<String, Thread>();

            public static void addThread(String name, Runnable runnable) {
                threads.put(name, new Thread(runnable));
                threads.get(name).start();
            }

            public static Thread getThread(String name) {
                return threads.get(name);
            }

            public static void deleteThread(String name) {
                threads.remove(name);
            }

        }
    }

    public static class graphics {

        public static class texture {
            private static final Map<String, TextureSheet> textureSheets = new HashMap<String, TextureSheet>();

            public static void createTextureSheet(String name, String file) {
                TextureSheet textureSheet = new TextureSheet();
                textureSheet.loadFromFile(file);
                textureSheets.put(name, textureSheet);
            }

            public static void addTextureSheet(String name, TextureSheet textureSheet) {
                textureSheets.put(name, textureSheet);
            }

            public static TextureSheet getTextureSheet(String name) {
                return textureSheets.get(name);
            }

            private static final Map<String, Texture> textures = new HashMap<String, Texture>();

            public static void createTexture(String name, TextureSheet sheet, int x, int y, int w, int h) {
                Texture texture = new Texture();
                texture.cropFromSheet(sheet, x, y, w, h);

            }

            public static void addTexture(String name, Texture textureSheet) {
                textures.put(name, textureSheet);
            }

            public static Texture getTexture(String name) {
                return textures.get(name);
            }

            public static void removeTexture(String name) {
                textures.remove(name);
            }

        }

        public static class object {
            private static final Map<String, Shape> objects = new HashMap<>();
            public static boolean overlaps(int x, int y, int width, int height, Rectangle2D r) {
                return x < r.getX() + r.getWidth() && x + width > r.getX()
                        && y < r.getY() + r.getHeight() && y + height > r.getY();
            }

            public static boolean overlapsAnyObject(int x, int y, int width, int height) {
                final boolean[] found = {false};
                objects.forEach((String name, Shape s) -> {
                    Rectangle2D r = s.getBounds();
                    if (x < r.getX() + r.getWidth() && x + width > r.getX()
                            && y < r.getY() + r.getHeight() && y + height > r.getY()) {
                        found[0] = true;
                    }
                });
                return found[0];
            }

            public static void createObject(Shape shape, String name) {
                objects.put(name, shape);
            }

            public static Shape getObjectByName(String name) {

                return objects.get(name);
            }

            public static void removeObject(String name) {
                objects.remove(name);
            }

        }

        public static class window {
            public static void createWindow(int width, int height, Dimension resolution, String name, Color backgroundColor, Consumer<Void> destroySequence, ArrayList<Component> GUIObjects, ArrayList<Consumer<Window>> GUIObjectFunctions) {
                Window newWindow = new Window();
                newWindow.init(width, height, resolution, name, backgroundColor, destroySequence, GUIObjects, GUIObjectFunctions);
                systemWindows.add(newWindow);
            }

            public static Window getWindowByName(String name) {
                for (Window win : systemWindows) {
                    if (win.getName().equals(name)) {
                        return win;
                    }
                }
                systemWindows.add(new Window());
                return systemWindows.get(systemWindows.size() - 1);
            }

            public static void removeWindow(String name) {
                systemWindows.removeIf(win -> win.getName().equals(name));
            }
            public static void refreshAllWindows() {
                for (Window win : systemWindows) {
                    win.Render();
                }
            }
            public static void updateAllWindows() {
                for (Window win : systemWindows) {
                    win.UpdateBuffer();
                }
            }

        }
    }

}
