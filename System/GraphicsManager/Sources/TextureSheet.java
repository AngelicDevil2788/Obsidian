package obsidian.System.GraphicsManager.Sources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureSheet {
    public BufferedImage sheet;
    public void loadFromFile(String address) {
        try {
            sheet = ImageIO.read(new File(address));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
