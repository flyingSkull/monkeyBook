package com.monkey.image.resize;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 
 * @author Ric
 * 
 */
public interface ImageSizeConverter {
    BufferedImage convert(File file);
}
