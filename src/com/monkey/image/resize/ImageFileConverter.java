package com.monkey.image.resize;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 
 * @author Ric
 * 
 */
public interface ImageFileConverter {

    /**
     * 
     * @param file
     * @param itemName
     * @return
     */
    BufferedImage resize(File file, String itemName);
}
