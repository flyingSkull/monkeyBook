package com.monkey.fileupload;

import com.monkey.image.ImageSizeConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.Iterator;
import java.util.List;


@Path("/file")
@Component
public class UploadFileService {

    private static final String FILE_UPLOAD_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "UploadedImages" + File.separator;
    private static final String SUCCESS_RESPONSE = "Successful";
    private static final String FAILED_RESPONSE = "Failed";
    @Autowired
    private ImageSizeConverter imageSizeConverter;

    /**
     * @param request
     * @param res
     * @throws Exception
     */
    @POST
    @Path("/multipleFiles")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public void uploadFile(@Context HttpServletRequest request, @Context HttpServletResponse res) throws Exception {

        String responseStatus = SUCCESS_RESPONSE;
        String candidateName = null;

        //checks whether there is a file upload request or not
        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {

                final List items = fileUpload.parseRequest(request);

                if (items != null) {
                    final Iterator iterator = items.iterator();

                    while (iterator.hasNext()) {
                        final FileItem item = (FileItem) iterator.next();
                        final String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();

                        if (item.isFormField()) {
                            candidateName = fieldValue;
                            System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
                            System.out.println("Candidate Name: " + candidateName);
                        } else {
                            final File savedFile = new File(FILE_UPLOAD_PATH + File.separator + itemName);
                            System.out.println("Saving the file: " + savedFile.getName());
                            item.write(savedFile);
                        }
                    }
                }
            } catch (FileUploadException fue) {
                responseStatus = FAILED_RESPONSE;
                fue.printStackTrace();
            } catch (Exception e) {
                responseStatus = FAILED_RESPONSE;
                e.printStackTrace();
            }
        }

    }

    public ImageSizeConverter getImageSizeConverter() {
        return imageSizeConverter;
    }

    public void setImageSizeConverter(ImageSizeConverter imageSizeConverter) {
        this.imageSizeConverter = imageSizeConverter;
    }

}
