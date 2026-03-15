package com.awesome.thesis.logic.application.service.files;

import com.awesome.thesis.logic.application.service.html.HtmlService;
import com.awesome.thesis.logic.application.service.profiles.ProfilEditor;
import com.awesome.thesis.logic.application.service.themen.ThemaEditor;
import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import com.awesome.thesis.logic.domain.model.themen.Thema;
import com.awesome.thesis.logic.domain.model.themen.ThemaDateiValue;
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

  @Autowired
  ThemaEditor themaEditor;

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
   * Methode zum lokalen Speichern einer Datei.
   *
   * @param datei        Datei.
   * @return Gibt ein DateiInfos-Objekt zurück, nachdem die Datei gespeichert wurde.
   */
  public String dateiSpeichern(String dateiId, MultipartFile datei) {
    DateiTypPruefer.verify(datei);

    try {
      Path root = Paths.get(uploadDirectory).toAbsolutePath().normalize();

      if (!Files.exists(root)) {
        Files.createDirectories(root);
      }

      String name = datei.getOriginalFilename();

      if (name == null) {
        throw new RuntimeException("Dateiname fehlerhaft");
      }
      Path zielPfad = root.resolve(dateiId + extractEndung(name));
      Files.copy(datei.getInputStream(), zielPfad);
      return name;

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
   * Methode zum Laden einer Datei.
   *
   * @param dateiId Dateiid
   * @return Gibt eine Resource zurück, wenn diese existiert.
   */
  public Resource dateiLadenNew(String dateiId) {
    Path root = Paths.get(uploadDirectory).toAbsolutePath().normalize();
    Path file = dateiPfadFinden(dateiId).normalize();

    if (!file.startsWith(root)) {
      System.err.println("CRITICAL: Path Traversal for file: " + dateiId);
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
   * @param profilId Profil ID
   */
  public void dateiSpeichernProfil(MultipartFile multipartFile, String beschreibung,
                                         int profilId) {
    String dateiId = UUID.randomUUID().toString();
    String name = dateiSpeichern(dateiId, multipartFile);
    profilEditor.addDatei(profilId, dateiId, name, beschreibung);
  }

  /**
   * Methode zum Löschen vom Dateien.
   *
   * @param profilId ProfilId
   * @param dateiId DateiId
   */
  public void removeDateiProfil(int profilId, String dateiId) {
    deleteDatei(dateiId);
    profilEditor.removeDatei(profilId, dateiId);
  }

  /**
   * Methode zum Speichern von Dateien für Themen.
   *
   * @param multipartFile Dateien
   * @param beschreibung Datei Beschreibung
   * @param themaId Thema-Id
   * @param profilId Profil-Id
   */
  public void dateiSpeichernThema(MultipartFile multipartFile,
                                  String beschreibung,
                                  Integer themaId,
                                  int profilId) {
    Thema thema = themaEditor.getThema(themaId);
    if (!themaEditor.allowedEdit(profilId, thema)) {
      throw new IllegalStateException("Profil darf Thema nicht bearbeiten!");
    }
    String dateiId = UUID.randomUUID().toString();
    String titel = dateiSpeichern(dateiId, multipartFile);
    ThemaDateiValue dateiValue = new ThemaDateiValue(
            dateiId,
            titel,
            beschreibung);
    themaEditor.addDatei(themaId, dateiValue);
  }

  /**
   * Methode zum Löschen von Dateien für Themen.
   *
   * @param profilId Profil-Id
   * @param themaId Thema-Id
   * @param dateiId Datei-Id
   */
  public void removeDateiThema(int profilId, Integer themaId, String dateiId) {

    if (!themaEditor.allowedEdit(profilId, themaEditor.getThema(themaId))) {
      throw new IllegalStateException("Profil darf Thema nicht bearbeiten!");
    }
    deleteDatei(dateiId);
    themaEditor.removeDatei(themaId, dateiId);
  }

  private Path dateiPfadFinden(String dateiId) {
    Path root = Paths.get(uploadDirectory).toAbsolutePath().normalize();

    try {
      return Files.list(root)
              .filter(p -> p.getFileName().toString().startsWith(dateiId))
              .findFirst()
              .orElseThrow(() -> new RuntimeException("Datei nicht vorhanden"));
    } catch (IOException e) {
      throw new RuntimeException("Datei nicht vorhanden", e);
    }
  }

  private String extractEndung(String name) {
    String endung = "";
    int punkt = name.lastIndexOf('.');
    if (punkt > 0) {
      endung = name.substring(punkt); // z.B. ".pdf"
    }
    return endung;
  }

  private void deleteDatei(String dateiId) {
    Path root = Paths.get(uploadDirectory).toAbsolutePath().normalize();
    Path file = dateiPfadFinden(dateiId).normalize();

    if (!file.startsWith(root)) {
      System.err.println("CRITICAL: Path Traversal for file: " + dateiId);
      throw new RuntimeException("Datei nicht vorhanden");
    }
    try {
      Files.deleteIfExists(file);
    } catch (IOException e) {
      throw new RuntimeException("Datei konnte nicht gelöscht werden");
    }
  }
}
