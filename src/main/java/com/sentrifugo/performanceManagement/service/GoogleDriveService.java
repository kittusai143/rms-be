package com.sentrifugo.performanceManagement.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleDriveService {

    private final Drive drive;

    public GoogleDriveService() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream("src/main/resources/GoogleDriveApp.json"))
                .createScoped(Collections.singletonList(DriveScopes.DRIVE));
        this.drive = new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                credential.getJsonFactory(),
                credential)
                .setApplicationName("RMS google driveApp")
                .build();
    }

    public String getFileLink(String fileName) throws IOException {
        FileList result = drive.files().list()
                .setQ("name='" + fileName + "'")
                .setSpaces("drive")
                .execute();

        if (result.getFiles() != null && !result.getFiles().isEmpty()) {
            String fileId = result.getFiles().get(0).getId();
            return "https://drive.google.com/file/d/" + fileId + "/view";
        }

        return null;
    }

}
