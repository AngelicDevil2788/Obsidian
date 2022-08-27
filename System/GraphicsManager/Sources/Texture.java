package obsidian.System.GraphicsManager.Sources;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Texture {
    public BufferedImage bits = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    public Rectangle2D area = new Rectangle2D.Double(0, 0, 0, 0);
    private Graphics2D g;
    public String name = "";
    public void cropFromSheet(TextureSheet sheet, int x, int y, int w, int h) {
        if (x > sheet.sheet.getWidth(null) || x < 0)
            return;
        else if (w > sheet.sheet.getWidth(null) || w < 0)
            return;
        else if (y > sheet.sheet.getHeight(null) || y < 0)
            return;
        else if (h > sheet.sheet.getHeight(null) || h < 0)
            return;
        bits = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        area = new Rectangle2D.Double(0, 0, w, h);

        for (int ph = 0; ph < h; ph++) {
            for (int pw = 0; pw < w; pw++) {
                Color c = new Color(sheet.sheet.getRGB(x + pw, y + ph), true);

                bits.setRGB(pw, ph, c.getRGB());

            }
        }

    }

    public void resizeTexture(int newWidth, int newHeight) {
        Image newImg = bits.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bimage = new BufferedImage(newImg.getWidth(null), newImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(newImg, 0, 0, null);
        bGr.dispose();
        area = new Rectangle2D.Double(0, 0, newWidth, newHeight);

        bits = bimage;

    }

    public void create(int x, int y, String name) {
        this.bits = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        area = new Rectangle2D.Double(0, 0, x, y);
        this.name = name;
        this.g = (Graphics2D)bits.getGraphics();
    }

    public void setName(String name) { this.name = name; };

    public void setPixel(int x, int y, Color color) {
        g.setColor(color);
        g.drawLine(x, y, x, y);
    }

    public void renderShape(Shape shape, Color color) {
        g.setColor(color);
        g.draw(shape);
    }

    public void renderFullShape(Shape shape, Color color) {
        g.setColor(color);
        g.fill(shape);
    }

    public void renderCircle(int x, int y, int r, Color color) {

        g.setColor(color);
        g.drawOval(x, y, r * 2 + 1, r * 2 + 1);
    }

    public void renderFullCircle(int x, int y, int r, Color color) {
        g.setColor(color);
        g.fillOval(x, y, r * 2 + 1, r * 2 + 1);
    }

    public void renderOval(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.drawOval(x, y, w, h);
    }

    public void renderFullOval(int x, int y, int w, int h, Color color) {
        g.setColor(color);
        g.fillOval(x, y, w, h);
    }

    public void renderText(String Text, Font font, Color color, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(Text, x, y);

    }

}
