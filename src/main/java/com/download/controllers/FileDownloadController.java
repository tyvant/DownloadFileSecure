package com.download.controllers;


import com.download.models.FileInfo;
import com.download.models.ShortFileInfo;
import com.download.repository.FileRepository;
import com.google.common.io.Files;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/download")
@Produces(MediaType.APPLICATION_JSON)
public class FileDownloadController {
    private  final FileRepository fileRepository;

    public FileDownloadController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }



    @Path("/list")
    @GET
    @Produces(APPLICATION_JSON)
    public Response getListFiles() {
        List<ShortFileInfo> filesInfo  = fileRepository.getAllFiles().stream().map(s -> new ShortFileInfo(s.getId(),s.getName()))
                .collect(Collectors.toList());
        return Response.status(OK).entity(filesInfo).build();
    }

    @GET
    @Path("/list/{id}")
    @Produces(APPLICATION_JSON)
    public Response getFileNameFrom(@PathParam("id") Integer id) {
        File downloadDirectory = new File(System.getProperty("user.home") );
        FileInfo fileInfo = fileRepository.getFileById(id);
        try {
            File outputFile = new File(downloadDirectory, fileInfo.getName());
          if (!outputFile.exists()) Files.touch(outputFile);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            outputStream.write(fileInfo.getData());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Response.ok(singletonMap("message", "file " + fileInfo.getName() + "download Successfully in "+ downloadDirectory))
                .build();
    }

}

