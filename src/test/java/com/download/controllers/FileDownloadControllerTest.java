package com.download.controllers;

import com.download.models.FileInfo;
import com.download.repository.FileRepository;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FileDownloadControllerTest {
        static FileRepository fileRepository = mock (FileRepository.class);
        FileInfo expected=new FileInfo(1,"Test.txt","DATA_TYPE","Toto".getBytes());
        FileInfo expected2=new FileInfo(45,"foo.txt","DATA_TYPE","TETE".getBytes());
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new FileDownloadController(fileRepository))
            .build();
    @Before
   public void setup() {
        when(fileRepository.getFileById(45)).thenReturn(expected2);
        when(fileRepository.getAllFiles()).thenReturn(List.of(expected));
    }

    @Test
    public void testGetListFiles() {
        assertThat (resources.client().target("/download/list/").request().get(FileInfo.class))
                .isEqualTo(expected);
    }
}





