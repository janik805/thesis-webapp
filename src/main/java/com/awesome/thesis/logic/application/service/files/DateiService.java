package com.awesome.thesis.logic.application.service.files;

import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Klasse, um Informationen zu DateiInfo zu erstellen und zu speichern und zu laden.
 */
@Service
public class DateiService {

  @Value("${upload.directory}")
  private String uploadDirectory;

  /**
   * Methode zum lokalen Speichern einer Datei.
   *
   * @param datei Datei.
   * @param beschreibung Beschreibung.
   * @return Gibt ein DateiInfos-Objekt zurück, nachdem die Datei gespeichert wurde.
   */
  public DateiInfos dateiSpeichern(MultipartFile datei, String beschreibung) {
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

  /**
   * Methode zum Laden einer Datei.
   *
   * @param filename Dateiname.
   * @return Gibt eine Resource zurück, wenn diese existiert.
   */
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
