package xyz.someboringnerd.wispy.managers;

import xyz.someboringnerd.wispy.content.Block;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class TextureManager
{
    public static BufferedImage getTexture(String path)
    {
        try {
            System.out.println(path);
            return ImageIO.read(Objects.requireNonNull(TextureManager.class.getResource("/assets/" + path)));
        } catch (IOException e) {
            try {
                return ImageIO.read(Objects.requireNonNull(TextureManager.class.getResource("/assets/missing.png")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
