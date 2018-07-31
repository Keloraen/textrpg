package textRPGAndor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GameObject implements Cloneable {
    //protected enum type{GameCharacter, Item, MapObject};
    public int posX, posY, lastPosX, lastPosY;
    public Sprite sprite;

    @Override
    public Object clone() throws CloneNotSupportedException // Копирование объектов
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Клонирование невозможно");
            return this;
        }

    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url != null) {
                sourceImage = ImageIO.read(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));
        return sprite;
    }
}