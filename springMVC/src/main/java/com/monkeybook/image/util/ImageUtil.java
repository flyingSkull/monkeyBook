package com.monkeybook.image.util;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ric
 * Date: 25.05.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {

    /**
     *
     */
    public static void createFolderStructure(String path) {

        File file = new File(path);

        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }
}
