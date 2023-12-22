package org.example;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import java.awt.*;

public class Stamp {
    private final String filePath = "src/main/resources/photo_2023-12-21_15-06-36.jpg";
    private final ImagePlus image = IJ.openImage(filePath);



    public void insertSignature(String signature) {
        ImageProcessor ip = image.getProcessor();
        Font font = new Font("Arial", Font.BOLD, 60);
        ip.setFont(font);
        ip.setColor(Color.BLACK);
        FontMetrics metrics = ip.getFontMetrics();
        int x = (image.getWidth() - metrics.stringWidth(signature)) / 2;
        int y = (image.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        ip.drawString(signature, x, y);
        FileSaver file =new FileSaver(image);
        file.saveAsPng("src/main/resources/stamp.jpg");

    }
}