package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stamp {


    public void insertSignature(String signature) throws Exception {

        String filePath = "src/main/resources/without_background.png";
        File file = new File(filePath);
        if(!file.exists()){
            throw new Exception("Stamp file don`t exist");
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
            Font font = new Font("Arial", Font.BOLD, 60);
            g2d.setFont(font);
            g2d.setColor(Color.BLACK);
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (bufferedImage.getWidth() - metrics.stringWidth(signature)) / 2;
            int y = (bufferedImage.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g2d.drawString(signature, x ,y);
            ImageIO.write(bufferedImage, "png", new File("src/main/resources/stamp.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
