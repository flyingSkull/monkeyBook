package com.monkeybook.spring.mvc.controllers;

import com.monkeybook.FilePathConstants;
import com.monkeybook.image.resize.ImageFileConverter;
import com.monkeybook.image.util.ImageUtil;
import com.monkeybook.spring.mvc.model.FileMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

@Controller
@RequestMapping("/controller")
public class FileController {


    @Autowired
    ImageFileConverter imageFileConverter;

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;

    /**
     * ************************************************
     * URL: /rest/controller/upload
     * upload(): receives files
     *
     * @param request  : MultipartHttpServletRequest auto passed
     * @param response : HttpServletResponse auto passed
     * @return LinkedList<FileMeta> as json format
     *         **************************************************
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {

        //1. build an iterator
        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;

        //2. get each file
        while (iterator.hasNext()) {

            //2.1 get next MultipartFile
            multipartFile = request.getFile(iterator.next());
            System.out.println(multipartFile.getOriginalFilename() + " uploaded! " + files.size());

            // create Folder-Structure
            createUploadFolderStructure();

            final String filePath = FilePathConstants.FILE_UPLOAD_PATH + File.separator + multipartFile.getOriginalFilename();
            final File savedFile = new File(filePath);

            System.out.println("Save the file: " + savedFile.getName());

            try {
                multipartFile.transferTo(savedFile);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            // resize Images
            BufferedImage bufferedImage = imageFileConverter.resize(savedFile, multipartFile.getOriginalFilename());

            //2.2 if files > 10 remove the first from the list
            if (files.size() >= 10)
                files.pop();

            //2.3 create new fileMeta
            fileMeta = new FileMeta();
            fileMeta.setFileName(multipartFile.getOriginalFilename());
            fileMeta.setFileSize(multipartFile.getSize() / 1024 + " Kb");
            fileMeta.setFileType(multipartFile.getContentType());

            System.out.println("fileMeta: "+fileMeta);

//            try {
//                fileMeta.setBytes(multipartFile.getBytes());
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            //2.4 add to files
            files.add(fileMeta);

        }

        // result will be like this
        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
        System.out.println("files: "+files.toString());
        return files;

    }

    private void createUploadFolderStructure() {
        ImageUtil.createFolderStructure(FilePathConstants.FILE_UPLOAD_PATH);
        ImageUtil.createFolderStructure(FilePathConstants.FILE_RESIZE_PATH);
    }


    /**
     * ************************************************
     * URL: /rest/controller/get/{value}
     * get(): get file as an attachment
     *
     * @param response : passed by the server
     * @param value    : value from the URL
     * @return void
     *         **************************************************
     */
    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void get(HttpServletResponse response, @PathVariable String value) {
        FileMeta getFile = files.get(Integer.parseInt(value));
        try {
            response.setContentType(getFile.getFileType());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getFileName() + "\"");
            FileCopyUtils.copy(getFile.getBytes(), response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
