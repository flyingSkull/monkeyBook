/*
 * Copyright (c) 2013.
 */

package com.monkey.image.resize;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.monkey.FilePathConstants;

/**
 * Created with IntelliJ IDEA. User: Ric Date: 13.05.13 Time: 15:26 To change
 * this template use File | Settings | File Templates.
 */
public class ImageFileConverterImpl implements ImageFileConverter {

    private int targetWidth = 100;
    private int targetHeight = 100;

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
	double scalex = (double) targetWidth / sourceWidth;
	double scaley = (double) targetHeight / sourceHeight;
	return Math.min(scalex, scaley);
    }

}
