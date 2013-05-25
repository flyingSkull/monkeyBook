package com.monkeybook.image.resize;

import com.monkeybook.FilePathConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ric
 * Date: 25.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class ImageFileConverterImpl implements ImageFileConverter {

    private int targetWidth = 100;
    private int targetHeight = 100;

    private static BufferedImage scaleImage(BufferedImage sourceImage, double scaledWidth) {
        Image scaledImage = sourceImage.getScaledInstance((int) (sourceImage.getWidth() * scaledWidth),
                (int) (sourceImage.getHeight() * scaledWidth), Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.createGraphics();
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    private static double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
        double scaleX = (double) targetWidth / sourceWidth;
        double scaleY = (double) targetHeight / sourceHeight;
        return Math.min(scaleX, scaleY);
    }

    @Override
    public BufferedImage resize(File file, String itemName) {
        try {

            BufferedImage srcImage = ImageIO.read(file);
            double determineImageScale = determineImageScale(srcImage.getWidth(), srcImage.getHeight(), targetWidth,
                    targetHeight);
            BufferedImage dstImage = scaleImage(srcImage, determineImageScale);
            ImageIO.write(dstImage, "jpg", new File(FilePathConstants.FILE_RESIZE_PATH + itemName));
            return dstImage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
