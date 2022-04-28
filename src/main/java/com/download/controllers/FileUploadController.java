package com.download.controllers;


import com.download.repository.FileRepository;
import com.download.models.FileInfo;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

import static java.util.Collections.singletonMap;

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
public class FileUploadController {

    private FileRepository fileRepository;

    public FileUploadController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@FormDataParam("fileData")InputStream inputStream, @FormDataParam("fileData")FormDataContentDisposition contentDisposition
                                          ) throws IOException {

        return handleUpload(contentDisposition, inputStream);
    }

    private Response handleUpload(ContentDisposition contentDisposition,InputStream inputStream) throws IOException {

        try {
            String fileName = contentDisposition.getFileName();
            String type = contentDisposition.getType();
            byte[] data = inputStream.readAllBytes();

            fileRepository.saveIntoDB(new FileInfo(fileName,type,data));
            return Response.ok(singletonMap("message",  fileName +" Uploaded Successfully"))
                    .build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}

