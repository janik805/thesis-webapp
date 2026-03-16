package com.awesome.thesis.logic.application.service.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.awesome.thesis.logic.application.service.html.HtmlService;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

class DateiServiceTest {

  @Mock
  private HtmlService htmlService;

  private DateiService dateiService;

  @BeforeEach
  void setUp() throws Exception {
    dateiService = new DateiService(htmlService);
    Path dateiPfad = Files.createTempDirectory("Test-Pfad für upload");
    dateiService.setUploadDirectory(dateiPfad.toString());
  }

  @Test
  void dateiSpeichernFunktioniert() {
    MockMultipartFile file = new MockMultipartFile(
        "datei",
        "test.pdf",
        "text/plain",
        "Hallo".getBytes(StandardCharsets.UTF_8)
    );

    String title = dateiService.dateiSpeichern("id", file);

    assertEquals("test.pdf", title);
    assertTrue(Files.exists(Paths.get(dateiService.getUploadDirectory(), "id.pdf")));
  }

  @Test
  void wennDateiNameNullDannException() {
    MockMultipartFile file = mock(MockMultipartFile.class);
    when(file.getOriginalFilename()).thenReturn(null);

    assertThrows(RuntimeException.class, () ->
        dateiService.dateiSpeichern("id", file)
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