package com.awesome.thesis.logic.application.service.files;

import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DateiService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public DateiInfos infosErstellen(MultipartFile datei, String beschreibung){
        DateiTypPruefer.verify(datei);

        String name = datei.getOriginalFilename();

        return new DateiInfos(name, beschreibung);
    }

    public DateiInfos dateiSpeichern(MultipartFile datei, String beschreibung){
        DateiTypPruefer.verify(datei);

        try {
            Path root = Paths.get(uploadDirectory);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String name = datei.getOriginalFilename();
            if (name == null) {
                throw new RuntimeException("Dateiname fehlerhaft");
            }
            Path zielPfad = root.resolve(name);
            datei.transferTo(zielPfad);

            return new DateiInfos(name, beschreibung);

        } catch (IOException e) {
            throw new RuntimeException("Datei konnte nicht gespeichert werden");
        }

    }

    public Resource dateiLaden(String filename) {
        try {
            Path file = Paths.get(uploadDirectory).resolve(filename).toAbsolutePath();

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Datei nicht vorhanden");
            }
        } catch (Exception e) {
            throw new RuntimeException("Datei konnte nicht geladen werden");
        }
    }
}
