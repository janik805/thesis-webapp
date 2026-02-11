package com.awesome.thesis.logic.application.service.files;

import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 * Klasse, die prüft, ob eine Datei ein erlaubtes Dateiformat besitzt.
 */
public class DateiTypPruefer {
  private static final Set<String> ERLAUBTE_DATEIENDUNGEN
      = Set.of("zip", "pdf", "md");

  /**
   * Methode, die Datei-Endung mit den erlaubten Endungen vergleicht.
   *
   * @param datei Datei.
   */
  public static void verify(MultipartFile datei) {
    String dateiName = datei.getOriginalFilename();
    if (dateiName == null) {
      throw new IllegalArgumentException("Dateiname ungültig");
    }
    boolean erlaubt = ERLAUBTE_DATEIENDUNGEN.stream()
        .anyMatch(dateiName::endsWith);

    if (!erlaubt) {
      throw new IllegalArgumentException(
          "Dateityp nicht erlaubt: " + dateiName);
    }

  }
}
