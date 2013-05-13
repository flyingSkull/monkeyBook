package com.monkey.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
public class UploadFileService {

    private static final String FILE_UPLOAD_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "UploadedImages" + File.separator;
    private static final String SUCCESS_RESPONSE = "Successful";
    private static final String FAILED_RESPONSE = "Failed";

    /**
     * @param request
     * @param res
     * @throws Exception
     */
    @POST
    @Path("/multipleFiles")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public void uploadFile(@Context HttpServletRequest request,
                           @Context HttpServletResponse res) throws Exception {

        String responseStatus = SUCCESS_RESPONSE;
        String candidateName = null;

        //checks whether there is a file upload request or not
        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
                /*
                 * parseRequest returns a list of FileItem
                 * but in old (pre-java5) style
                 */
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
}


//    /**
//     * @param request
//     * @return
//     */
//    @POST
//    @Path("/multipleFiles")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public void uploadMultipleFiles(@Context HttpServletRequest request, @Context HttpServletResponse response) {
//
//
//
//    }
//
//}