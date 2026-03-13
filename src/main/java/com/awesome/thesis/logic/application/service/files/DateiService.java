package com.awesome.thesis.logic.application.service.files;

import com.awesome.thesis.logic.application.service.html.HtmlService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.profil.ProfilDateiValue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  ProfilEditor profilEditor;

  @Value("${upload.directory}")
  private String uploadDirectory;

  HtmlService htmlService;

  /**
   * Konstruktor.
   *
   * @param htmlService HtmlService, der benötigt wird, um Markdown zu html zu wandeln.
   */
  public DateiService(HtmlService htmlService) {
    this.htmlService = htmlService;
  }

  /**
   * Eine Methode, die den Inhalt einer Markdown Datei zu Html umwandelt.
   *
   * @param dateiname Name der Datei.
   * @return gibt die umgewandelte Datei zurück.
   */
  public String markdownZuHtml(String dateiname) {
    Resource resource = dateiLaden(dateiname);
    try {
      String inhalt = Files.readString(Paths.get(resource.getURI()));
      return htmlService.markdownToHtml(inhalt);
    } catch (IOException e) {
      throw new RuntimeException("Datei konnte nicht gelesen werden.");
    }
  }

  /**
   * Methode, um die upload-Directory abzufragen.
   *
   * @return gibt den Pfad für den Upload zurück.
   */
  public String getUploadDirectory() {
    return uploadDirectory;
  }

  /**
   * Methode, um die upload-Directory zu setzen.
   *
   * @param uploadDirectory Pfad für den Upload.
   */
  public void setUploadDirectory(String uploadDirectory) {
    this.uploadDirectory = uploadDirectory;
  }

  /**
   * Methode zum lokalen Speichern einer Datei.
   *
   * @param datei        Datei.
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
    Path root = Paths.get(uploadDirectory).toAbsolutePath().normalize();
    Path file = root.resolve(filename).toAbsolutePath().normalize();

    if (!file.startsWith(root)) {
      System.err.println("CRITICAL: Path Traversal for file: " + filename);
      throw new RuntimeException("Datei nicht vorhanden");
    }

    Resource resource;
    try {
      resource = new UrlResource(file.toUri());
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    if (resource.exists()) {
      return resource;
    } else {
      throw new RuntimeException("Datei nicht vorhanden");
    }
  }

  /**
   * Methode zum Speichern von Dateien für Profile.
   *
   * @param multipartFile Datei
   * @param beschreibung Beschreibung
   * @param id Profil ID
   * @return DateiInfos-Objekt der gespeicherten Datei
   */
  public DateiInfos dateiSpeichernProfil(MultipartFile multipartFile, String beschreibung, int id) {
    DateiInfos infos = dateiSpeichern(multipartFile, beschreibung);
    String dateiId = UUID.randomUUID().toString();
    ProfilDateiValue dateiValue = new ProfilDateiValue(dateiId, infos.getTitle(),
            infos.getDescription());
    profilEditor.addDatei(id, dateiValue.id(), dateiValue.name(), beschreibung);
    return infos;
  }

  public void removeDateiProfil(int profilId, String id) {
    profilEditor.removeDatei(profilId, id);
  }
}
