package com.monkeybook;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: ric
 * Date: 25.05.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class FilePathConstants {

    public static final String MAIN_SERVER_PATH = System.getProperty("user.home") + File.separator + "Documents"
            + File.separator + "MonkeyBookUpload" + File.separator;
    public static final String FILE_UPLOAD_PATH = MAIN_SERVER_PATH + "originalImages" + File.separator;
    public static final String FILE_RESIZE_PATH = MAIN_SERVER_PATH + "resizedImages" + File.separator;

}

