package com.monkeybook.image.resize;


import java.io.File;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: ric
 * Date: 25.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public interface ImageFileConverter {

    BufferedImage resize(File file, String itemName);
}
