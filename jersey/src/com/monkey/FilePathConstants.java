package com.monkey;

import java.io.File;

public class FilePathConstants {

    public static final String MAIN_SERVER_PATH = System.getProperty("user.home") + File.separator + "Documents"
	    + File.separator + "MonkeyBookUpload" + File.separator;

    public static final String FILE_UPLOAD_PATH = MAIN_SERVER_PATH + "originalImages" + File.separator;

    public static final String FILE_RESIZE_PATH = MAIN_SERVER_PATH + "resizedImages" + File.separator;

}
