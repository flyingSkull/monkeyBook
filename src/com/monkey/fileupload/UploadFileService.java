package com.monkey.fileupload;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monkey.image.resize.ImageSizeConverter;

/**
 * Created with IntelliJ IDEA. User: Ric Date: 06.05.13 Time: 11:26 To change
 * this template use File | Settings | File Templates.
 */
@Component
@Path("/file")
public class UploadFileService {

    private static final String FILE_UPLOAD_PATH = System.getProperty("user.home") + File.separator + "Documents"
	    + File.separator + "UploadedImages" + File.separator;

    @Autowired
    ImageSizeConverter imageSizeConverter;

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
			// System.out.println("UploadFileService.uploadFile() "
			// + fieldName);
			// System.out.println("UploadFileService.uploadFile() "
			// + fieldValue);
			final File savedFile = new File(FILE_UPLOAD_PATH + File.separator + itemName);
			System.out.println("Saving the file: " + savedFile.getName());
			item.write(savedFile);

			imageSizeConverter.convert(savedFile);
		    }
		}
	    } catch (FileUploadException fue) {
		fue.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

}
