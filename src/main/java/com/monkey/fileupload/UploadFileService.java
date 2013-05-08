package com.monkey.fileupload;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;


@Path("/file")
public class UploadFileService {

    private static final String FILE_UPLOAD_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "UploadFolderImageEditor" + File.separator;
    private static final String CANDIDATE_NAME = "candidateName";
    private static final String SUCCESS_RESPONSE = "Successful";
    private static final String FAILED_RESPONSE = "Failed";

    /**
     * @param uploadedInputStream
     * @param fileDetail
     * @return
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String path = FILE_UPLOAD_PATH;
        String uploadedFileLocation = path + fileDetail.getFileName();

        writeToFile(uploadedInputStream, uploadedFileLocation);

        String output = "File uploaded to : " + uploadedFileLocation;

        return Response.status(200).entity(output).build();

    }

    @POST
    @Path("multipleFiles")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response uploadFile(@Context HttpServletRequest request,
                               @Context HttpServletResponse res) throws Exception {
        String response = "Unable to attach files";
        FileBean bean = MultipartUtil.parseMultipart(request, getBlobService());
        if (null != bean) {
            response = "{\"name\":\"" + bean.getFilename() + "\",\"type\":\""
                    + bean.getContentType() + "\",\"size\":\"" + bean.getSize()
                    + "\"}";
        }
        return Response.ok(response).build();
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
//    }

    /**
     * @param uploadedInputStream
     * @param uploadedFileLocation
     */

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}