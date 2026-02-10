package com.awesome.thesis.logic.application.service.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.domain.model.files.DateiInfos;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class DateiServiceTest {

  private DateiService dateiService;

  private Path dateiPfad;

  @BeforeEach
  void setUp() throws Exception {
    dateiService = new DateiService();
    Path dateiPfad = Files.createTempDirectory("Test-Pfad für upload");
    dateiService.setUploadDirectory(dateiPfad.toString());
  }

  @Test
  void dateiSpeichernFunktioniert() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "datei",
        "test.pdf",
        "text/plain",
        "Hallo".getBytes()
    );

    DateiInfos infos = dateiService.dateiSpeichern(file, "Beschreibung");

    assertEquals("test.pdf", infos.getTitle());
    assertEquals("Beschreibung", infos.getDescription());
    assertTrue(Files.exists(Paths.get(dateiService.getUploadDirectory(), "test.pdf")));
  }

  @Test
  void wennDateiNameNullDannException() {
    MockMultipartFile file = mock(MockMultipartFile.class);
    when(file.getOriginalFilename()).thenReturn(null);

    assertThrows(RuntimeException.class, () ->
        dateiService.dateiSpeichern(file, "beschreibung")
    );
  }

  @Test
  void dateiLadenFunktioniert() throws Exception {
    Path file = Paths.get(dateiService.getUploadDirectory(), "test.pdf");
    Files.writeString(file, "Inhalt");

    Resource resource = dateiService.dateiLaden("test.pdf");

    assertTrue(resource.exists());
    assertEquals("test.pdf", resource.getFilename());
  }

  @Test
  void nichtVorhandeneDateiLadenWirftException() {
    assertThrows(RuntimeException.class, () ->
        dateiService.dateiLaden("nichtVorhanden.pdf")
    );
  }
}